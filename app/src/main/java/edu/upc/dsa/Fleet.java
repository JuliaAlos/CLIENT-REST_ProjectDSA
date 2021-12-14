package edu.upc.dsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.transferObjects.PlaneUserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** BASE LIST OF AIRPLANES INCLUDED IN THE GAME:
 * Cessna 172
 * Diamond DA42
 * Airbus A320
 * Airbus A380
 * F-16
 * Antonov An-225
 * Space Shuttle? xd
 */

public class Fleet extends AppCompatActivity {
    ApiInterface apiInterface;
    List<PlaneModel> allPlanes;
    List<PlaneTO> listPlanesUser;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    RecyclerView recyclerView;
    String username = "mirarnau";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        //LoginUserTO user = new LoginUserTO(sharedPref.getString("user",null), sharedPref.getString("password",null));
        //username = user.getUserName();
        setContentView(R.layout.fleet);


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

        //So that the list is created.
        try {
            getListPlanesUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Here, the allPlanes list is created and the recyclerview is initialized.
        try {
            getAllPlanes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initializeRecyclerView(List<PlaneModel> planes){
        RecyclerAdapter myAdapter= new RecyclerAdapter(this, planes, this.listPlanesUser);
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

                allPlanes = response.body();
                for (PlaneModel plane : allPlanes) {
                    Log.d("MYAPP", plane.getModel());
                }
                initializeRecyclerView(allPlanes);
            }

            @Override
            public void onFailure(Call<List<PlaneModel>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    //Only gets the list of planes of the user, it doesn't initialize the recyclerview.
    private void getListPlanesUser() throws IOException {
        Call<List<PlaneTO>> call = apiInterface.getListPlanesPlayer();
        call.enqueue(new Callback<List<PlaneTO>>() {
            @Override
            public void onResponse(Call<List<PlaneTO>> call, Response<List<PlaneTO>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }

                listPlanesUser = response.body();
                for (PlaneTO plane : listPlanesUser) {
                    Log.d("MYAPP", plane.getModel());
                }
            }

            @Override
            public void onFailure(Call<List<PlaneTO>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }




}
