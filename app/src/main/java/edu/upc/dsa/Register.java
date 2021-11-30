package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    TextView username;
    TextView fullName;
    TextView email;
    TextView password;


    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    //Returns a TO with the information of the new registered user.
    public void doneClick(View view) throws IOException {
        this.username = (TextView) findViewById(R.id.usernamePlainText);
        this.fullName = (TextView) findViewById(R.id.fullNamePlainText);
        this.email = (TextView) findViewById(R.id.emailPlainText);
        this.password = (TextView) findViewById(R.id.passwordTextView);

        RegisterUserTO user = new RegisterUserTO(username.getText().toString(),
                password.getText().toString(),fullName.getText().toString(),email.getText().toString());

        Log.d("AddUser", "Add register new user -> " + user.getUserName());
        Call<UserTO> call = apiInterface.addUser(user);
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse(Call<UserTO> call, Response<UserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("AddUser", "Error addUser"+response.code());
                    Toast.makeText(Register.this, "User name already registered" , Toast.LENGTH_LONG).show();
                    return;
                }
                UserTO userTO =response.body();
                Toast.makeText(Register.this, "Welcome " + userTO.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("AddUser", "Successful addUser "+ userTO.getUserName());
            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(Register.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("AddUser", "Error addUser in getting response from service using retrofit: "+t.getMessage());
            }
        });

    }
}