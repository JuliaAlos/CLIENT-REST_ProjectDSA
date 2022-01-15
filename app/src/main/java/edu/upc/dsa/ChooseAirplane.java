package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.PlaneTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseAirplane extends AppCompatActivity {

    ApiInterface apiInterface;
    List <PlaneTO> listPlanesPlayer;
    String currentlyDisplayed;
    Integer positionDisplayed;
    ImageView planeImage;
    ProgressBar progressBar;

    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    String playerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_airplane);
        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        playerName = sharedPref.getString("user","Hola");

        planeImage = findViewById(R.id.planeImageID);
        progressBar = findViewById(R.id.circularProgressID);
        progressBar.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        try {
            getListPlanesPlayer(this.playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getListPlanesPlayer(String playerName) throws IOException{
        Call<List<PlaneTO>> call = apiInterface.getListPlanesPlayer(playerName);
        call.enqueue(new Callback<List<PlaneTO>>() {
            @Override
            public void onResponse(Call<List<PlaneTO>> call, Response<List<PlaneTO>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                listPlanesPlayer = response.body();
                currentlyDisplayed = listPlanesPlayer.get(0).getModel();
                positionDisplayed = 0;

                displayModel(positionDisplayed);

                for (PlaneTO plane : listPlanesPlayer) {
                    Log.d("MYAPP", plane.getModel());
                }
            }

            @Override
            public void onFailure(Call<List<PlaneTO>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    public void displayModel (Integer positionDisplayed){
        if (positionDisplayed > (listPlanesPlayer.size() - 1)){
            this.positionDisplayed = 0;
            positionDisplayed = 0;
        }
        String model = this.listPlanesPlayer.get(positionDisplayed).getModel();
        this.currentlyDisplayed = model;
        Log.d("Current display", currentlyDisplayed);
        switch (model) {
            case "Cessna":
                planeImage.setImageResource(R.drawable.cessna_select);
                break;
            case "Airbus":
                planeImage.setImageResource(R.drawable.airbus_select);
                break;
            case "Fighter":
                planeImage.setImageResource(R.drawable.fighter_select);
                break;
            case "Helicopter":
                planeImage.setImageResource(R.drawable.helicopter_select);
                break;
            case "Acrobatic":
                planeImage.setImageResource(R.drawable.acrobatic_select);
                break;
        }
    }

    public void getPlaneByModel(String planeModelModel) {
        progressBar.setVisibility(View.VISIBLE);
        Call<PlaneModel> call = apiInterface.getPlaneByModel(planeModelModel);
        call.enqueue(new Callback<PlaneModel>() {
            @Override
            public void onResponse (Call<PlaneModel> call, Response<PlaneModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                PlaneModel planeToUnity = response.body();
                System.out.println("Robustness: " + planeToUnity.getEnginesLife());
                Log.d("INSIDE", "Inside:" + planeToUnity.getEnginesLife());
                getAllUpgradesFromPlayer(playerName, planeToUnity);
            }
            @Override
            public void onFailure(Call<PlaneModel> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    private void getAllUpgradesFromPlayer (String playerName, PlaneModel planeToUnity){
        Call<List<Upgrade>> call = apiInterface.getAllUpgradesFromPlayer(playerName);
        call.enqueue(new Callback<List<Upgrade>>() {
            @Override
            public void onResponse(Call<List<Upgrade>> call, Response<List<Upgrade>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                if (response.code() == 200){
                    for (Upgrade upgrade : response.body()) {
                        if (upgrade.getPlaneModelModel().equals(currentlyDisplayed)) {
                            if (upgrade.getModificationCode().equals("0")) {
                                planeToUnity.setEnginesLife(planeToUnity.getEnginesLife() + 10);
                            }
                            if (upgrade.getModificationCode().equals("1")) {
                                planeToUnity.setVelY(planeToUnity.getVelY() + 10);
                            }
                            if (upgrade.getModificationCode().equals("2")) {
                                planeToUnity.setVelX(planeToUnity.getVelX() + 10);
                            }
                            if (upgrade.getModificationCode().equals("3")) {
                                planeToUnity.setFuel(planeToUnity.getFuel() - 10);
                            }
                            if (upgrade.getModificationCode().equals("4")) {
                                planeToUnity.setGravity(planeToUnity.getGravity() - 10);
                            }
                        }
                    }

                }
                System.out.println("Num upgrades: " + response.body().size());
                System.out.println("Stats mod: " + planeToUnity.getEnginesLife() +" , " +  planeToUnity.getFuel());
                progressBar.setVisibility(View.GONE);


                /** AQUÍ ÉS ON HAS DE PASSAR EL "planeToUnity" ON EL NECESSITIS. */


            }

            @Override
            public void onFailure(Call<List<Upgrade>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void chooseToMap(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void nextClick(View view) {
        this.positionDisplayed++;
        displayModel(positionDisplayed);
    }

    public void previousClick(View view) {
        if (this.positionDisplayed == 0){
            this.positionDisplayed = this.listPlanesPlayer.size() - 1;
        }
        else {
            this.positionDisplayed--;
        }
        displayModel(positionDisplayed);
    }

    public void selectAircraftClick(View view) {
        getPlaneByModel(this.currentlyDisplayed);
    }
}
