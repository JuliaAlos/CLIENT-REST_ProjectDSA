package edu.upc.dsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import edu.upc.dsa.ApiInterface;
import edu.upc.dsa.models.GameResults;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.transferObjects.PlaneTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnityComms {
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static ApiInterface apiInterface = retrofit.create(ApiInterface.class);


    static String playerName;

    static PlaneModel plane;

    public static void setPlane(PlaneModel plane) {
        UnityComms.plane = plane;
    }

    public static void setPlayerName(String playerName) {
        UnityComms.playerName = playerName;
    }

    public static String getModel() {
        return plane.getModel();
    }

    public static int getFuel() {
        return plane.getFuel();
    }

    public static int getMinFuel() {
        return plane.getMinFuel();
    }

    public static int getEnginesLife() {
        return plane.getEnginesLife();
    }

    public static int getMaxEnginesLife() {
        return plane.getMaxEnginesLife();
    }

    public static int getVelX() {
        return plane.getVelX();
    }

    public static int getMaxSpeed() {
        return plane.getMaxSpeed();
    }

    public static int getVelY() {
        return plane.getVelY();
    }

    public static int getMaxManoeuvrability() {
        return plane.getMaxManoeuvrability();
    }

    public static int getGravity() {
        return plane.getGravity();
    }

    public static int getMinWeight() {
        return plane.getMinWeight();
    }
  
    public static void endGame(float distance, float time, int collectedCoins){
        GameResults gameResults = new GameResults();
        gameResults.setDistance((int) distance);
        gameResults.setCollectedBitcoins(collectedCoins);
        gameResults.setTimeOfFlight((int) time);




        Call<Void> call = apiInterface.uploadGame(gameResults, playerName);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error on uploadGame Call: " + response.code());
                    return;
                }else {
                    Log.d("MYAPP", "Successfully uploaded game results");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });

    }
}
