package edu.upc.dsa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.models.AdapRecPlane;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.ViewModel;
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

    ArrayList<Plane> list = new ArrayList<Plane>();
    RecyclerView rec;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fleet);

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

/*
        //----RECYCLERVIEW-----
        RecyclerView recyclerView;
        RecyclerAdapter adapter;
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, listPlanes);
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

        AdapRecPlane adapter = new AdapRecPlane(this, list);
        rec = findViewById(R.id.recyclerViewID);
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(this));

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


    public ArrayList<Plane> data (){
        list.add(new Plane(R.drawable.a,"Motores europeos", 2.0, "Airbuss",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.b,"Motores estadunidenses", 2.0, "Boeing",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.c,"Vuelo básico", 1.0, "Cessna",500,200,9.81, 2,500));
        list.add(new Plane(R.drawable.f,"Vuelo profesional", 4.0, "Caza",500,200,9.81, 2,500));
        return list;
    }

}
