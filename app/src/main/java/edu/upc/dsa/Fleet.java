package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.ui.main.RecyclerAdapterFleet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** BASE LIST OF AIRPLANES INCLUDED IN THE GAME:
 * Cessna 172 - OK
 * Edge 540 (Acrobatic) - OK
 * Airbus A320 - OK
 * Helicopter - OK
 * Fighter - OK
 */

public class Fleet extends AppCompatActivity {

    ApiInterface apiInterface;
    List<PlaneModel> listPlanes;

    List <PlaneTO> listPlanesPlayer;

    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    RecyclerView recyclerView;
    String playerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fleet);

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        playerName = sharedPref.getString("user","Hola");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerViewID);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        try {
            getListPlanesPlayer(this.playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeRecyclerView(List<PlaneTO> listPlanesPlayer){
        RecyclerAdapterFleet myAdapter= new RecyclerAdapterFleet(this, listPlanesPlayer);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    /************ API OPERATIONS ********************************/

    private void getAllPlanes() throws IOException {
        Call<List<PlaneModel>> call = apiInterface.getAllPlanes();
        call.enqueue(new Callback<List<PlaneModel>>() {
            @Override
            public void onResponse(Call<List<PlaneModel>> call, Response<List<PlaneModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }

                listPlanes = response.body();
                for (PlaneModel plane : listPlanes) {
                    Log.d("MYAPP", plane.getModel());
                }
            }

            @Override
            public void onFailure(Call<List<PlaneModel>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
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
                for (PlaneTO plane : listPlanesPlayer) {
                    Log.d("MYAPP", plane.getModel());
                }
                initializeRecyclerView(listPlanesPlayer);
            }

            @Override
            public void onFailure(Call<List<PlaneTO>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Hangar.class);
        startActivity(intent);
    }

    public void fleetToHangar(View view) {
        Intent intent = new Intent(this, Hangar.class);
        startActivity(intent);
    }
}
