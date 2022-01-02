package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.upc.dsa.transferObjects.PlanePlayerTO;
import edu.upc.dsa.transferObjects.PlaneTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoPlane extends AppCompatActivity {
    String model;
    ApiInterface apiInterface;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_plane);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        userName = sharedPref.getString("user","Hola");

        ProgressBar Robustness, Speed, Maneuverability, Fuel, Weight;
        Robustness = findViewById(R.id.progressRobustness);
        Maneuverability = findViewById(R.id.progressManeuverability);
        Speed = findViewById(R.id.progressSpeed);
        Fuel = findViewById(R.id.progressFuel);
        Weight = findViewById(R.id.progressWeight);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model = extras.getString("MODEL");
        }
        switch (model) {
            case "Airbus":
                layout.setBackgroundResource(R.drawable.a320_info);
                Robustness.setProgress(60);
                Maneuverability.setProgress(40);
                Speed.setProgress(70);
                Fuel.setProgress(60);
                Weight.setProgress(100);
                break;
            case "Cessna":
                layout.setBackgroundResource(R.drawable.cessna_info);
                Robustness.setProgress(30);
                Maneuverability.setProgress(70);
                Speed.setProgress(40);
                Fuel.setProgress(20);
                Weight.setProgress(20);
                break;
            case "Fighter":
                layout.setBackgroundResource(R.drawable.fighter_info);
                Robustness.setProgress(20);
                Maneuverability.setProgress(60);
                Speed.setProgress(90);
                Fuel.setProgress(80);
                Weight.setProgress(50);
                break;
            case "Helicopter":
                layout.setBackgroundResource(R.drawable.helicopter_info);
                Robustness.setProgress(50);
                Maneuverability.setProgress(70);
                Speed.setProgress(50);
                Fuel.setProgress(50);
                Weight.setProgress(60);
                break;
            case "Acrobatic":
                layout.setBackgroundResource(R.drawable.acrobatic_info);
                Robustness.setProgress(20);
                Maneuverability.setProgress(80);
                Speed.setProgress(60);
                Fuel.setProgress(20);
                Weight.setProgress(20);
                break;
        }
    }

    public void buyAirplaneClick(View view) {
        PlanePlayerTO planePlayerTO = new PlanePlayerTO(userName, model);
        Call<Void> call = apiInterface.addPlaneToUser(planePlayerTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse (Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
        Toast.makeText(InfoPlane.this, this.model + "purchased correctly.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }

    public void backClick(View view) {
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }
}

