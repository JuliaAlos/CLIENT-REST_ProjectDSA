package edu.upc.dsa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    ImageView image;
    TextView user,fullname,email,rol;

    ApiInterface apiInterface;
    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        image=findViewById(R.id.image);
        user=findViewById(R.id.user);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        rol=findViewById(R.id.rol);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);


        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("user",null).toString();

        Call<UserTO> call = apiInterface.getUser(userName);
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse(Call<UserTO> call, Response<UserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("Profile", "Error user do not exist");
                    return;
                }

                Log.d("Profile", "Successful getUser "+ userName);
                UserTO data = response.body();
                user.setText(data.getUserName());
                fullname.setText("Full name     "+data.getFullName());
                email.setText("Full email     "+data.getEmail());
                rol.setText(data.getPlayer().getRol());
                Glide.with(Profile.this).load(data.getImage_url()).into(image);

            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(Profile.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
            }
        });
    }
    public void eliminarUser(View v){
        AlertDialog.Builder alerta = new AlertDialog.Builder(Profile.this);
        alerta.setMessage("Are you sure you want to delete the user permanently?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("DELETE USER");
        titulo.show();
    }

    private void deleteUser(){
        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("user",null).toString();

        Call<Void> call = apiInterface.deleteUser(userName);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Log.d("Profile", "Error user do not exist");
                    return;
                }

                Log.d("Profile", "Successful deleteUser "+ userName);
                sharedPref.edit().clear().commit();
                finish();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Profile.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
            }
        });

    }

    public void updateUser(View view) {
        Intent intent = new Intent(this, UpdateUser.class);
        startActivity(intent);
        finish();
    }
    public void back(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        back(null);
    }
}