package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ImagePlanes extends AppCompatActivity {

    ImageView ImagePlanesView;
    int planephoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_planes);

        ImagePlanesView = findViewById(R.id.ImagePlanesView);
        getPlaneData();
        setPlaneData();

    }

    private void getPlaneData (){
        if (getIntent().hasExtra("planeData")){
            planephoto = getIntent().getIntExtra("planeData", 0);
        }
        else{
            Toast.makeText(this, "No data of the plane", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPlaneData(){
        ImagePlanesView.setImageResource(planephoto);

    }

}