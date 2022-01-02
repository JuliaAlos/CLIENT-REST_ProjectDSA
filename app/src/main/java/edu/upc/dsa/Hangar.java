package edu.upc.dsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.upc.dsa.HomeActivity;
import edu.upc.dsa.R;

public class Hangar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangar);
    }

    public void fleetClick(View view) {
        Intent intent = new Intent(this, Fleet.class);
        startActivity(intent);
    }

    public void mechanicClick(View view) {
    }

    public void returnClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
