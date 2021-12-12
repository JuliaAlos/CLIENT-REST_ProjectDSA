package edu.upc.dsa;

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
    List<Plane> listPlanes;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        try {
            getAllPlanes();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initializeRecyclerView(List<Plane> planes){
        RecyclerAdapter myAdapter= new RecyclerAdapter(this, planes);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    /************ API OPERATIONS ********************************/

    private void getAllPlanes() throws IOException {
        Call<List<Plane>> call = apiInterface.getAllPlanes();
        call.enqueue(new Callback<List<Plane>>() {
            @Override
            public void onResponse(Call<List<Plane>> call, Response<List<Plane>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }

                listPlanes = response.body();
                for (Plane plane : listPlanes) {
                    Log.d("MYAPP", plane.getModel());
                }
                initializeRecyclerView(listPlanes);
            }

            @Override
            public void onFailure(Call<List<Plane>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

/**
    public ArrayList<Plane> data (){
        list.add(new Plane(R.drawable.a,"Motores europeos", 2.0, "Airbuss",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.b,"Motores estadunidenses", 2.0, "Boeing",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.c,"Vuelo básico", 1.0, "Cessna",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.f,"Vuelo profesional", 4.0, "Caza",500,200,9.81, 2,500));
        return list;
    }
 */


}
