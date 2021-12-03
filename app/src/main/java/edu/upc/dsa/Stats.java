package edu.upc.dsa;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Stats extends AppCompatActivity {

    ApiInterface apiInterface;

    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }
    public void statsBackClick(View view){
        finish();
    }
}
