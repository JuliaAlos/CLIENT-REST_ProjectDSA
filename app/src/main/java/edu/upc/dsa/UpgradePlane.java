package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.PlanePlayerTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpgradePlane extends AppCompatActivity {

    String userName;
    String model;
    Integer upgradeRobustness, upgradeSpeed, upgradeManeuverability, upgradeFuel, upgradeWeight;
    ApiInterface apiInterface;
    ProgressBar robustness, speed, maneuverability, fuel, weight;
    ProgressBar circularProgressBar;
    ConstraintLayout layout;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    List<Upgrade> listUpgradesPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_plane);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        userName = sharedPref.getString("user","Hola");

        upgradeRobustness = 0;
        upgradeSpeed = 0;
        upgradeManeuverability = 0;
        upgradeFuel = 0;
        upgradeWeight = 0;

        circularProgressBar = findViewById(R.id.circularProgressBarID);
        circularProgressBar.setVisibility(View.INVISIBLE);

        robustness = findViewById(R.id.robustness_progress);
        maneuverability = findViewById(R.id.maneuverability_progress);
        speed = findViewById(R.id.speed_progress);
        fuel = findViewById(R.id.fuel_progress);
        weight = findViewById(R.id.weight_progress);
        layout = (ConstraintLayout) findViewById(R.id.layoutUpgradesID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model = extras.getString("MODEL");
        }
        switch (model) {
            case "Airbus":
                layout.setBackgroundResource(R.drawable.mechanic_a320);
                break;
            case "Cessna":
                layout.setBackgroundResource(R.drawable.mechanic_cessna);
                break;
            case "Fighter":
                layout.setBackgroundResource(R.drawable.mechanic_fighter);
                break;
            case "Helicopter":
                layout.setBackgroundResource(R.drawable.mechanic_helicopter);
                break;
            case "Acrobatic":
                layout.setBackgroundResource(R.drawable.mechanic_acrobatic);
                break;
        }
        getPlaneByModel(model);
        getAllUpgradesFromPlayer(userName, model);


    }
    public void upgradeToMechanic(View view) {
        Intent intent = new Intent(this, Mechanic.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Mechanic.class);
        startActivity(intent);
    }

    public void getPlaneByModel(String planeModelModel) {
        Call<PlaneModel> call = apiInterface.getPlaneByModel(planeModelModel);
        call.enqueue(new Callback<PlaneModel>() {
            @Override
            public void onResponse (Call<PlaneModel> call, Response<PlaneModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                robustness.setProgress(response.body().getEnginesLife());
                maneuverability.setProgress(response.body().getVelY());
                speed.setProgress(response.body().getVelX());
                fuel.setProgress(response.body().getFuel());
                weight.setProgress(response.body().getGravity());

            }
            @Override
            public void onFailure(Call<PlaneModel> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    public void upgradeRobustness(View view) {
        upgradeRobustness++;
        robustness.setProgress(robustness.getProgress() + 10);
    }

    public void upgradeManeuverability(View view) {
        upgradeManeuverability++;
        maneuverability.setProgress(maneuverability.getProgress() + 10);
    }

    public void upgradeSpeed(View view) {
        upgradeSpeed++;
        speed.setProgress(speed.getProgress() + 10);
    }

    public void upgradeFuel(View view) {
        upgradeFuel++;
        fuel.setProgress(fuel.getProgress() - 10);
    }

    public void upgradeWeight(View view) {
        upgradeWeight++;
        weight.setProgress(weight.getProgress() - 10);
    }

    public void upgradeAirplaneClick(View view) {
        circularProgressBar.setVisibility(View.VISIBLE);
        while (this.upgradeRobustness > 0) {
            Upgrade upgrade = new Upgrade("0", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeRobustness--;
        }
        while (this.upgradeManeuverability > 0) {
            Upgrade upgrade = new Upgrade("1", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeManeuverability--;
        }
        while (this.upgradeSpeed > 0) {
            Upgrade upgrade = new Upgrade("2", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeSpeed--;
        }
        while (this.upgradeFuel > 0) {
            Upgrade upgrade = new Upgrade("3", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeFuel--;
        }
        while (this.upgradeWeight > 0) {
            Upgrade upgrade = new Upgrade("4", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeWeight--;
        }
        circularProgressBar.setVisibility(View.INVISIBLE);

    }

    public void addUpgradeToPlayer (Upgrade upgrade){
        Call<Void> call = apiInterface.addUpgradeToPlayer(upgrade);
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
        Toast.makeText(UpgradePlane.this, this.model + "purchased correctly.", Toast.LENGTH_LONG).show();
    }

    private void getAllUpgradesFromPlayer (String playerName, String model){
        Call<List<Upgrade>> call = apiInterface.getAllUpgradesFromPlayer(playerName);
        call.enqueue(new Callback<List<Upgrade>>() {
            @Override
            public void onResponse(Call<List<Upgrade>> call, Response<List<Upgrade>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                listUpgradesPlayer = response.body();
                for (Upgrade upgrade : listUpgradesPlayer) {
                    if (upgrade.getPlaneModelModel().equals(model)) {
                        if (upgrade.getModificationCode().equals("0")) {
                            robustness.setProgress(robustness.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("1")) {
                            maneuverability.setProgress(maneuverability.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("2")) {
                            speed.setProgress(speed.getProgress() + 10);
                        }
                        if (upgrade.getModificationCode().equals("3")) {
                            fuel.setProgress(fuel.getProgress() - 10);
                        }
                        if (upgrade.getModificationCode().equals("4")) {
                            weight.setProgress(weight.getProgress() - 10);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Upgrade>> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }
}
