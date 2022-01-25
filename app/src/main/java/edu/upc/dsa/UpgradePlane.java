package edu.upc.dsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.PlanePlayerTO;
import edu.upc.dsa.transferObjects.UserTO;
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
    ProgressBar max_robustness, max_speed, max_maneuverability, min_fuel, min_weight;
    ProgressBar circularProgressBar;
    ConstraintLayout layout;
    Boolean changes;
    ImageView robustness_button, maneuverability_button, speed_button, fuel_button, weight_button;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    List<Upgrade> listUpgradesPlayer;
    Integer bitcoinsAvailable;
    Integer priceUpgrade = 0;
    TextView bitcoins, costUpgrade;
    Button upgradeButton;
    Context thisContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_plane);

        circularProgressBar = findViewById(R.id.circularProgressBarID);
        circularProgressBar.setVisibility(View.VISIBLE);

        thisContext = this;

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
        changes = false;

        robustness_button = findViewById(R.id.robustness_button);
        maneuverability_button = findViewById(R.id.maneuverability_button);
        speed_button = findViewById(R.id.speed_button);
        fuel_button = findViewById(R.id.fuel_button);
        weight_button = findViewById(R.id.weight_button);

        upgradeButton = findViewById(R.id.upgradeButtonID);
        upgradeButton.setBackgroundColor(Color.GRAY);

        robustness = findViewById(R.id.robustness_progress);
        maneuverability = findViewById(R.id.maneuverability_progress);
        speed = findViewById(R.id.speed_progress);
        fuel = findViewById(R.id.fuel_progress);
        weight = findViewById(R.id.weight_progress);

        max_robustness = findViewById(R.id.max_robustness_progress);
        max_maneuverability = findViewById(R.id.max_maneuverability_progress);
        max_speed = findViewById(R.id.max_speed_progress);
        min_fuel = findViewById(R.id.min_fuel_progress);
        min_weight = findViewById(R.id.min_weight_progress);

        bitcoins = findViewById(R.id.bitcoinsID);
        costUpgrade = findViewById(R.id.costUpgradeID);
        costUpgrade.setText("0");

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
        getUserByName();
    }
    public void upgradeToMechanic(View view) {
        if (changes) {
            AlertDialog.Builder confirmation = new AlertDialog.Builder(UpgradePlane.this);
            confirmation.setMessage("Are you sure you want to leave? The upgrade will be discarded!")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(thisContext, Mechanic.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo = confirmation.create();
            titulo.show();
        }
        else{
            Intent intent = new Intent(thisContext, Mechanic.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (changes) {
            AlertDialog.Builder confirmation = new AlertDialog.Builder(UpgradePlane.this);
            confirmation.setMessage("Are you sure you want to leave? The upgrade will be discarded!")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(thisContext, Mechanic.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo = confirmation.create();
            titulo.show();
        }
        else{
            Intent intent = new Intent(thisContext, Mechanic.class);
            startActivity(intent);
        }
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
                max_robustness.setProgress(response.body().getMaxEnginesLife());
                maneuverability.setProgress(response.body().getVelY());
                max_maneuverability.setProgress(response.body().getMaxManoeuvrability());
                speed.setProgress(response.body().getVelX());
                max_speed.setProgress(response.body().getMaxSpeed());
                fuel.setProgress(response.body().getFuel());
                min_fuel.setProgress(response.body().getMinFuel());
                weight.setProgress(response.body().getGravity());
                min_weight.setProgress(response.body().getMinWeight());

                getAllUpgradesFromPlayer(userName, model);

            }
            @Override
            public void onFailure(Call<PlaneModel> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }

    public void getUserByName() {
        Call<UserTO> call = apiInterface.getUser(this.userName);
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse (Call<UserTO> call, Response<UserTO> response) {
                if (!response.isSuccessful()) {
                    Log.d("MYAPP", "Error" + response.code());
                    return;
                }
                assert response.body() != null;
                bitcoins.setText(response.body().getPlayer().getBitcoins().toString());
                bitcoinsAvailable = response.body().getPlayer().getBitcoins();
            }
            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });
    }



    public void upgradeRobustness(View view) {
        if (this.robustness.getProgress() == this.max_robustness.getProgress()){
        }
        else {
            upgradeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            upgradeRobustness++;
            priceUpgrade = priceUpgrade + 10;
            String displayedCost = "-" + priceUpgrade.toString();
            costUpgrade.setText(displayedCost);
            robustness.setProgress(robustness.getProgress() + 10);
            changes = true;
        }
    }

    public void upgradeManeuverability(View view) {
        if (this.maneuverability.getProgress() == this.max_maneuverability.getProgress()){
        }
        else {
            upgradeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            upgradeManeuverability++;
            priceUpgrade = priceUpgrade + 10;
            String displayedCost = "-" + priceUpgrade.toString();
            costUpgrade.setText(displayedCost);
            maneuverability.setProgress(maneuverability.getProgress() + 10);
            changes = true;
        }
    }

    public void upgradeSpeed(View view) {
        if (this.speed.getProgress() == this.max_speed.getProgress()){
        }
        else {
            upgradeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            upgradeSpeed++;
            priceUpgrade = priceUpgrade + 10;
            String displayedCost = "-" + priceUpgrade.toString();
            costUpgrade.setText(displayedCost);
            speed.setProgress(speed.getProgress() + 10);
            changes = true;
        }
    }

    public void upgradeFuel(View view) {
        if (this.fuel.getProgress() == this.min_fuel.getProgress()){
        }
        else {
            upgradeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            upgradeFuel++;
            priceUpgrade = priceUpgrade + 10;
            String displayedCost = "-" + priceUpgrade.toString();
            costUpgrade.setText(displayedCost);
            fuel.setProgress(fuel.getProgress() - 10);
            changes = true;
        }
    }

    public void upgradeWeight(View view) {
        if (this.weight.getProgress() == this.min_weight.getProgress()){
        }
        else {
            upgradeButton.setBackgroundColor(Color.parseColor("#FF03DAC5"));
            upgradeWeight++;
            priceUpgrade = priceUpgrade + 10;
            String displayedCost = "-" + priceUpgrade.toString();
            costUpgrade.setText(displayedCost);
            weight.setProgress(weight.getProgress() - 10);
            changes = true;
        }
    }

    public void upgradeAirplaneClick(View view) {
        confirmUpdate();
    }

    public void confirmUpdate(){
        if(changes) {
            if (this.bitcoinsAvailable - this.priceUpgrade < 0){
                this.priceUpgrade = 0;
                upgradeRobustness = 0;
                upgradeWeight = 0;
                upgradeFuel = 0;
                upgradeSpeed = 0;
                upgradeManeuverability = 0;
                AlertDialog.Builder notification = new AlertDialog.Builder(UpgradePlane.this);
                notification.setMessage("You don't have enough bitcoins for such upgrade.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                costUpgrade.setText("0");
                                upgradeButton.setBackgroundColor(Color.GRAY);
                                changes = false;
                                getPlaneByModel(model);
                                dialog.cancel();
                            }
                        });
                AlertDialog title = notification.create();
                title.setTitle("NO MONEY");
                title.show();
            }
            else {
                AlertDialog.Builder confirmation = new AlertDialog.Builder(UpgradePlane.this);
                confirmation.setMessage("Are you sure you want these upgrades?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder notification = new AlertDialog.Builder(UpgradePlane.this);
                                notification.setMessage("New upgrade acquired!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                priceUpgrade = 0;
                                                costUpgrade.setText("0");
                                                changes = false;
                                                upgradeButton.setBackgroundColor(Color.GRAY);
                                                upgradeAirplane();
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog title = notification.create();
                                title.show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                priceUpgrade = 0;
                                upgradeRobustness = 0;
                                upgradeWeight = 0;
                                upgradeFuel = 0;
                                upgradeSpeed = 0;
                                upgradeManeuverability = 0;
                                costUpgrade.setText("0");
                                changes = false;
                                upgradeButton.setBackgroundColor(Color.GRAY);
                                getPlaneByModel(model);
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = confirmation.create();
                titulo.setTitle("NEW UPGRADE");
                titulo.show();
            }
        }
        else{
            AlertDialog.Builder notification = new AlertDialog.Builder(UpgradePlane.this);
            notification.setMessage("Nothing to upgrade")
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog title = notification.create();
            title.show();
        }
    }

    private void upgradeAirplane(){
        circularProgressBar.setVisibility(View.VISIBLE);
        if (this.upgradeRobustness > 0) {
            Upgrade upgrade = new Upgrade("0", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeRobustness--;
        }
        else if (this.upgradeManeuverability > 0) {
            Upgrade upgrade = new Upgrade("1", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeManeuverability--;
        }
        else if (this.upgradeSpeed > 0) {
            Upgrade upgrade = new Upgrade("2", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeSpeed--;
        }
        else if (this.upgradeFuel > 0) {
            Upgrade upgrade = new Upgrade("3", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeFuel--;
        }
        else if (this.upgradeWeight > 0) {
            Upgrade upgrade = new Upgrade("4", this.userName, this.model);
            this.addUpgradeToPlayer(upgrade);
            this.upgradeWeight--;
        }
        else{
            getPlaneByModel(this.model);
            changes = false;
            circularProgressBar.setVisibility(View.GONE);
            getUserByName();
        }
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
                costUpgrade.setText("0");
                upgradeAirplane();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("MYAPP", "Error:" + t.getMessage());
            }
        });

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
                            if (robustness.getProgress() == max_robustness.getProgress()){
                                robustness.getProgressDrawable().setColorFilter(Color.parseColor("#FBE627"), PorterDuff.Mode.SRC_IN);
                                robustness_button.setImageResource(R.drawable.stars);
                            }
                            else{
                                robustness_button.setImageResource(R.drawable.upgrade);
                            }
                        }
                        if (upgrade.getModificationCode().equals("1")) {
                            maneuverability.setProgress(maneuverability.getProgress() + 10);
                            if (maneuverability.getProgress() == max_maneuverability.getProgress()){
                                maneuverability.getProgressDrawable().setColorFilter(Color.parseColor("#FBE627"), PorterDuff.Mode.SRC_IN);
                                maneuverability_button.setImageResource(R.drawable.stars);
                            }
                            else{
                                maneuverability_button.setImageResource(R.drawable.upgrade);
                            }
                        }
                        if (upgrade.getModificationCode().equals("2")) {
                            speed.setProgress(speed.getProgress() + 10);
                            if (speed.getProgress() == max_speed.getProgress()){
                                speed.getProgressDrawable().setColorFilter(Color.parseColor("#FBE627"), PorterDuff.Mode.SRC_IN);
                                speed_button.setImageResource(R.drawable.stars);
                            }
                        }
                        if (upgrade.getModificationCode().equals("3")) {
                            fuel.setProgress(fuel.getProgress() - 10);
                            if (fuel.getProgress() == min_fuel.getProgress()){
                                fuel.getProgressDrawable().setColorFilter(Color.parseColor("#FBE627"), PorterDuff.Mode.SRC_IN);
                                fuel_button.setImageResource(R.drawable.stars);
                            }
                            else{
                                fuel_button.setImageResource(R.drawable.upgrade);
                            }
                        }
                        if (upgrade.getModificationCode().equals("4")) {
                            weight.setProgress(weight.getProgress() - 10);
                            if (weight.getProgress() == min_weight.getProgress()){
                                weight.getProgressDrawable().setColorFilter(Color.parseColor("#FBE627"), PorterDuff.Mode.SRC_IN);
                                weight_button.setImageResource(R.drawable.stars);
                            }
                            else{
                                weight_button.setImageResource(R.drawable.upgrade);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Upgrade>> call, Throwable t) {
                Log.d("MYAPP_UPGRADES", "Error:" + t.getMessage());
            }
        });
        circularProgressBar.setVisibility(View.GONE);
    }
}
