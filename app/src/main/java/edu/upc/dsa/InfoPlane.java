package edu.upc.dsa;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class InfoPlane extends AppCompatActivity {
    String model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_plane);
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

}

