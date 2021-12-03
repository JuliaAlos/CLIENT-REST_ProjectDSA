package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import edu.upc.dsa.models.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    TextView loginName;
    TextView loginPassword;
    CardView progressBar;

    ApiInterface apiInterface;
    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        checkSharedPreferences();
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        setTheme(R.style.Theme_ProjectDSA);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    public void loginButtonClick(View view) {
        loginName = (TextView) findViewById(R.id.loginUsernamePlainText);
        loginPassword = (TextView) findViewById(R.id.loginPasswordTextView);

       // progressBar= (CardView) findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);


        LoginUserTO user = new LoginUserTO(loginName.getText().toString(), loginPassword.getText().toString());
        Log.d("LoginUser", "Login user --> " + user.getUserName());
        Call<LoginUserTO> call = apiInterface.loginUser(user);
        call.enqueue(new Callback<LoginUserTO>() {
            @Override
            public void onResponse(Call<LoginUserTO> call, Response<LoginUserTO> response) {

                if(!response.isSuccessful()){
                    Log.d("LoginUser", "Error loginUser"+response.code());
                    Toast.makeText(Login.this, "User name not registered" , Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.GONE);
                    return;
                }
                LoginUserTO loginUserTO =response.body();
                Toast.makeText(Login.this, "Welcome " + loginUserTO.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("LoginUser", "Successful loginUser "+ loginUserTO.getUserName());
                saveSharedPreferences(loginUserTO);
                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<LoginUserTO> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("LoginUser", "Error loginUser in getting response from service using retrofit: "+t.getMessage());
            }
        });
    }

    public void loginToRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void saveSharedPreferences(LoginUserTO loginUserTO){
        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user",loginUserTO.getUserName());
        editor.putString("password",loginUserTO.getPassword());
        Log.d("LoginUser", "Save user--> " + loginUserTO.getUserName());
        Log.d("LoginUser", "Save password --> " + loginUserTO.getPassword());
        editor.commit();
    }

    public void checkSharedPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        LoginUserTO user = new LoginUserTO(sharedPref.getString("user",null), sharedPref.getString("password",null));
        Log.d("LoginUser", "Check if user is already login --> " + user.getUserName());
        if(sharedPref.getString("user",null)==null)
            return;
        Call<LoginUserTO> call = apiInterface.loginUser(user);
        call.enqueue(new Callback<LoginUserTO>() {
            @Override
            public void onResponse(Call<LoginUserTO> call, Response<LoginUserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("LoginUser", "Error loginUser");
                    Toast.makeText(Login.this, "User must do login" , Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Login.this, "Welcome again " + user.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("LoginUser", "Successful loginUser "+ user.getUserName());
                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<LoginUserTO> call, Throwable t) {
                Toast.makeText(Login.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("LoginUser", "Error in getting response from service: "+t.getMessage());
            }
        });

    }
}
