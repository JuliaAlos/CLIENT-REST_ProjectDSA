package edu.upc.dsa;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {

    ApiInterface apiInterface;

    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void storeClick(View view){
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }

    public void rankingClick(View view){
        Intent intent = new Intent(this, Ranking.class);
        startActivity(intent);
    }

    public void forumClick(View view){
        Intent intent = new Intent(this, Forum.class);
        startActivity(intent);
    }

    public void statsClick(View view){
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }
}
