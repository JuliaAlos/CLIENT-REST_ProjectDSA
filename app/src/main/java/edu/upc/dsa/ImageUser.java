package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageUser extends AppCompatActivity {

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_user);
        SharedPreferences sharedPref = getSharedPreferences("image", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/bart.png").into((ImageView) findViewById(R.id.image1));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/homer.jpg").into((ImageView) findViewById(R.id.image2));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/batman.jpg").into((ImageView) findViewById(R.id.image3));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/superman.jpg").into((ImageView) findViewById(R.id.image4));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/minion.jpg").into((ImageView) findViewById(R.id.image5));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/minion2.png").into((ImageView) findViewById(R.id.image6));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/lisa.png").into((ImageView) findViewById(R.id.image7));
        Glide.with(ImageUser.this).load("http://147.83.7.203:8080/profileImages/maggie.png").into((ImageView) findViewById(R.id.image8));

    }
    private void done(){
        editor.commit();
        Intent intent = new Intent(this, UpdateUser.class);
        startActivity(intent);
        finish();
    }

    public void image1(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/bart.png");
        done();
    }
    public void image2(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/homer.jpg");
        done();
    }
    public void image3(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/batman.jpg");
        done();
    }
    public void image4(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/superman.jpg");
        done();
    }
    public void image5(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/minion.jpg");
        done();
    }
    public void image6(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/minion2.png");
        done();
    }
    public void image7(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/lisa.png");
        done();
    }
    public void image8(View view) {
        editor.putString("image","http://147.83.7.203:8080/profileImages/maggie.png");
        done();
    }

    @Override
    public void onBackPressed() {
        done();
    }
}