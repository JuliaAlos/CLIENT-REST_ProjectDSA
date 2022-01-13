package edu.upc.dsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import edu.upc.dsa.models.ForumEntry;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.ui.main.RecyclerAdapterFleet;
import edu.upc.dsa.ui.main.RecyclerAdapterForum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forum extends AppCompatActivity {

    List<ForumEntry> listEntries;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    String playerName;
    TextView post;

    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerForumID);
        post = findViewById(R.id.postTextID);
        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        playerName = sharedPref.getString("user","Hola");

        getAllEntries();
    }

    public void initializeRecyclerView(List<ForumEntry> listEntries){
        RecyclerAdapterForum myAdapter= new RecyclerAdapterForum(this, listEntries);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    private void getAllEntries() {
        Call<List<ForumEntry>> call = apiInterface.getAllEntries();
        call.enqueue(new Callback<List<ForumEntry>>() {
            @Override
            public void onResponse(Call<List<ForumEntry>> call, Response<List<ForumEntry>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                listEntries = response.body();
                Collections.sort(listEntries);
                initializeRecyclerView(listEntries);
            }
            @Override
            public void onFailure(Call<List<ForumEntry>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }


    public void addEntry (ForumEntry entry){
        Call<Void> call = apiInterface.addEntry(entry);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse (Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                getAllEntries();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    public void postClick(View view) {
        String message = this.post.getText().toString();
        this.post.setText("");
        ForumEntry entry = new ForumEntry(this.playerName, message);
        addEntry(entry);
    }
}
