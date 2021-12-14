package edu.upc.dsa;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.upc.dsa.models.Player;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ranking extends AppCompatActivity {
    ApiInterface apiInterface;
    ArrayList<Player> list = new ArrayList<Player>();
    RecyclerView rec;

    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        AdapRecPlayer adapter = new AdapRecPlayer(this, list);
        rec = findViewById(R.id.recyclerRanking);
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList<Player> data (){
        list.add(new Player(R.drawable.a,"Pau", 2.0, "pau2",500,"Avanzado",9, 2));
        list.add(new Player(R.drawable.b,"Julia", 2.0, "julia3",200,"Principiante",8, 25));
        list.add(new Player(R.drawable.c,"Marc", 1.0, "marc1",300,"Avanzado",3, 500));
        list.add(new Player(R.drawable.f,"Arnau", 4.0, "arnau5",500,"Muy avanzado",9, 2500));
        return list;
    }

    public void rankingBackClick(View view){
        finish();
    }
}
