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
import java.util.List;

import edu.upc.dsa.models.InsigniaModel;
import edu.upc.dsa.transferObjects.InsigniaTO;
import edu.upc.dsa.ui.main.InsigniaRecycler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Insignia_menu extends AppCompatActivity {
    ApiInterface apiInterface;
    List<InsigniaModel> listInsignias;
    List<InsigniaTO> listInsigniasPlayer;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    RecyclerView recyclerView;
    String playerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insignias);

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        playerName = sharedPref.getString("user", "Hola");

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
            getAllInsignias();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getListInsigniasPlayer(this.playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeRecyclerView(List<InsigniaTO> listInsigniasPlayer){
        InsigniaRecycler myAdapter= new InsigniaRecycler(this, listInsigniasPlayer);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }


    private void getAllInsignias() throws IOException {
        Call<List<InsigniaModel>> call = apiInterface.getAllInsignias();
        call.enqueue(new Callback<List<InsigniaModel>>() {
            @Override
            public void onResponse(Call<List<InsigniaModel>> call, Response<List<InsigniaModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }

                listInsignias = response.body();
                for (InsigniaModel insigniaModel : listInsignias) {
                    Log.d("MYAPP", insigniaModel.getName());
                }
            }


            @Override
            public void onFailure(Call<List<InsigniaModel>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    private void getListInsigniasPlayer(String playerName) throws IOException{
        Call<List<InsigniaTO>> call = apiInterface.getListInsigniasPlayer(playerName);
        call.enqueue(new Callback<List<InsigniaTO>>() {
            @Override
            public void onResponse(Call<List<InsigniaTO>> call, Response<List<InsigniaTO>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error, no insignias " + response.code());
                    return;
                }
                listInsigniasPlayer = response.body();
                for (InsigniaTO insignia : listInsigniasPlayer) {
                    Log.d("MYAPP", insignia.getName());
                }
                initializeRecyclerView(listInsigniasPlayer);
            }

            @Override
            public void onFailure(Call<List<InsigniaTO>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

}
