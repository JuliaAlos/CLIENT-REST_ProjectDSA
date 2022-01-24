package edu.upc.dsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.PlanePlayerTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPlane extends AppCompatActivity {
    String model;
    String purchased;
    ApiInterface apiInterface;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    String userName;
    ProgressBar robustness, speed, maneuverability, fuel, weight;
    ConstraintLayout layout;
    List<Upgrade> listUpgradesPlayer;
    TextView pricePlane, currentBitcoins;
    Button buyAirplaneButton;
    Integer bitcoins;
    Integer price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_plane);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        userName = sharedPref.getString("user","Hola");

        robustness = findViewById(R.id.progressRobustness);
        maneuverability = findViewById(R.id.progressManeuverability);
        speed = findViewById(R.id.progressSpeed);
        fuel = findViewById(R.id.progressFuel);
        weight = findViewById(R.id.progressWeight);
        pricePlane = findViewById(R.id.priceTextID);
        currentBitcoins = findViewById(R.id.currentBitcoinsID);
        buyAirplaneButton = findViewById(R.id.buttonBuyAirplaneID);
        layout = (ConstraintLayout) findViewById(R.id.layoutID);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            model = extras.getString("MODEL");
            purchased = extras.getString("PURCHASED");
        }
        System.out.println(purchased);

        if (purchased.equals("Yes")){
            String messageButton = "Purchased";
            buyAirplaneButton.setText(messageButton);
            buyAirplaneButton.setBackgroundColor(Color.GRAY);
            buyAirplaneButton.setClickable(false);
        }

        switch (model) {
            case "Airbus":
                layout.setBackgroundResource(R.drawable.a320_info);
                break;
            case "Cessna":
                layout.setBackgroundResource(R.drawable.cessna_info);
                break;
            case "Fighter":
                layout.setBackgroundResource(R.drawable.fighter_info);
                break;
            case "Helicopter":
                layout.setBackgroundResource(R.drawable.helicopter_info);
                break;
            case "Acrobatic":
                layout.setBackgroundResource(R.drawable.acrobatic_info);
                break;
        }
        getPlaneByModel(this.model);
        getUserByName();
    }

    public void getUserByName() {
        Call<UserTO> call = apiInterface.getUser(this.userName);
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse (Call<UserTO> call, Response<UserTO> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                assert response.body() != null;
                bitcoins = response.body().getPlayer().getBitcoins();
                currentBitcoins.setText(response.body().getPlayer().getBitcoins().toString());
            }
            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    public void buyAirplaneClick(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(InfoPlane.this);
        alerta.setMessage("Are you sure you want to buy this aircraft?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (bitcoins >= price) {
                            PlanePlayerTO planePlayerTO = new PlanePlayerTO(userName, model);
                            Call<Void> call = apiInterface.addPlaneToUser(planePlayerTO);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (!response.isSuccessful()) {
                                        Log.d("MYAPP", "Error" + response.code());
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d("MYAPP", "Error:" + t.getMessage());
                                }
                            });
                            informPurchaseCompleted();
                            String messageButton = "Purchased";
                            buyAirplaneButton.setText(messageButton);
                            buyAirplaneButton.setBackgroundColor(Color.GRAY);
                            buyAirplaneButton.setClickable(false);
                        }
                        else{
                            informNoMoney();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("BUY AIRCRAFT");
        titulo.show();
    }

    public void informPurchaseCompleted(){
        AlertDialog.Builder confirmation = new AlertDialog.Builder(InfoPlane.this);
        confirmation.setMessage("Aircraft purchased!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getUserByName();
                        dialog.cancel();
                    }
                });
        AlertDialog title = confirmation.create();
        title.show();
    }

    public void informNoMoney() {
        AlertDialog.Builder confirmation = new AlertDialog.Builder(InfoPlane.this);
        confirmation.setMessage("You don't have enough bitcoins to buy this aircraft.")
                .setCancelable(false)
                .setPositiveButton("FUCK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getUserByName();
                        dialog.cancel();
                    }
                });
        AlertDialog title = confirmation.create();
        title.setTitle("NO MONEY");
        title.show();
    }


    public void getPlaneByModel(String planeModelModel) {
        Call<PlaneModel> call = apiInterface.getPlaneByModel(planeModelModel);
        call.enqueue(new Callback<PlaneModel>() {
            @Override
            public void onResponse (Call<PlaneModel> call, Response<PlaneModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                robustness.setProgress(response.body().getEnginesLife());
                maneuverability.setProgress(response.body().getVelY());
                speed.setProgress(response.body().getVelX());
                fuel.setProgress(response.body().getFuel());
                weight.setProgress(response.body().getGravity());
                pricePlane.setText(response.body().getPrice().toString());
                price = response.body().getPrice();

                getAllUpgradesFromPlayer(userName, model);

            }
            @Override
            public void onFailure(Call<PlaneModel> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    private void getAllUpgradesFromPlayer (String playerName, String model){
        Call<List<Upgrade>> call = apiInterface.getAllUpgradesFromPlayer(playerName);
        call.enqueue(new Callback<List<Upgrade>>() {
            @Override
            public void onResponse(Call<List<Upgrade>> call, Response<List<Upgrade>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                listUpgradesPlayer = response.body();
                for (Upgrade upgrade : listUpgradesPlayer) {
                    if (upgrade.getPlaneModelModel().equals(model)) {
                        if (upgrade.getModificationCode().equals("0")) {
                            robustness.setProgress(robustness.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("1")) {
                            maneuverability.setProgress(maneuverability.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("2")) {
                            speed.setProgress(speed.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("3")) {
                            fuel.setProgress(fuel.getProgress() - 10);
                        }
                        if (upgrade.getModificationCode().equals("4")) {
                            weight.setProgress(weight.getProgress() - 10);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Upgrade>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }

    public void backClick(View view) {
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }


}

