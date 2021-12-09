package edu.upc.dsa;

import android.media.Image;
import android.os.Bundle;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insignias);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
        try {
            getAllPlanes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
        //----RECYCLERVIEW-----
        RecyclerView recyclerView;
        RecyclerAdapter adapter;
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this,listPlanes);
        recyclerView.setAdapter(adapter);
        //--------------------


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

         */
    }

    private void getAllPlanes() throws IOException {
        Call<List<Plane>> call = apiInterface.getAllPlanes();
        //Fetch and print the results.
        listPlanes = call.execute().body();
        for (Plane plane : listPlanes)
        {
            System.out.println(plane.getModel());
        }
    }

}
