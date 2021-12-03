package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.upc.dsa.models.LoginUserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {

    ApiInterface apiInterface;
    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);
        checkSharedPreferences();

    }
    public void checkSharedPreferences(){

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        LoginUserTO user = new LoginUserTO(sharedPref.getString("user",null), sharedPref.getString("password",null));
        Log.d("SplashScreen", "Check if user is already login --> " + user.getUserName());
        if(sharedPref.getString("user",null)==null){
            Intent intent = new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
            return;
        }
        Call<LoginUserTO> call = apiInterface.loginUser(user);
        call.enqueue(new Callback<LoginUserTO>() {
            @Override
            public void onResponse(Call<LoginUserTO> call, Response<LoginUserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("SplashScreen", "Error loginUser");
                    Toast.makeText(SplashScreen.this, "User must do login" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText(SplashScreen.this, "Welcome again " + user.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("SplashScreen", "Successful loginUser "+ user.getUserName());
                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(intent);
                return;
            }

            @Override
            public void onFailure(Call<LoginUserTO> call, Throwable t) {
                Toast.makeText(SplashScreen.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("SplashScreen", "Error in getting response from service: "+t.getMessage());
                checkSharedPreferences();
            }
        });

    }
}