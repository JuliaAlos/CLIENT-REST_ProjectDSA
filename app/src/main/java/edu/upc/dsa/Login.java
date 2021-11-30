package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.dsa.models.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    TextView loginName;
    TextView loginPassword;
    ApiInterface apiInterface;
    public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void loginButtonClick(View view) {
        loginName = (TextView) findViewById(R.id.loginUsernamePlainText);
        loginPassword = (TextView) findViewById(R.id.loginPasswordTextView);

        LoginUserTO user = new LoginUserTO(loginName.getText().toString(), loginPassword.getText().toString());
        Log.d("Login user", "Login user --> " + user.getUserName());
        Call<LoginUserTO> call = apiInterface.loginUser(user);
        call.enqueue(new Callback<LoginUserTO>() {
            @Override
            public void onResponse(Call<LoginUserTO> call, Response<LoginUserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("LoginUser", "Error loginUser"+response.code());
                    Toast.makeText(Login.this, "User name not registered" , Toast.LENGTH_LONG).show();
                    return;
                }
                LoginUserTO loginUserTO =response.body();
                Toast.makeText(Login.this, "Welcome again" + loginUserTO.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("Login user", "Successful loginUser "+ loginUserTO.getUserName());
            }

            @Override
            public void onFailure(Call<LoginUserTO> call, Throwable t) {
                Toast.makeText(Login.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Login user", "Error loginUser in getting response from service using retrofit: "+t.getMessage());
            }
        });
    }
}
