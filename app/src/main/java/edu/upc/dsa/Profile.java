package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    ImageView image;
    TextView user,fullname,email;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        getSupportActionBar().hide();/** what is this, i don't know, let's see */


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
                user.setText("USER: "+data.getUserName());
                fullname.setText("NAME: "+data.getFullName());
                email.setText("EMAIL: "+data.getEmail());

            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(Profile.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
            }
        });
    }
}