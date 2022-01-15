package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Pattern;

import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.RegisterUserTO;
import edu.upc.dsa.transferObjects.UserTO;
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
    TextView password2;


    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().hide();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        username = findViewById(R.id.usernamePlainText);
        fullName = findViewById(R.id.fullNamePlainText);
        email = findViewById(R.id.emailPlainText);
        password = findViewById(R.id.passwordTextView);
        password2 = findViewById(R.id.passwordTextView2);

    }

    //Returns a TO with the information of the new registered user.
    public void doneClick(View view) throws IOException {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewErrorPass).setVisibility(View.GONE);
        findViewById(R.id.textViewErrorCampos).setVisibility(View.GONE);
        findViewById(R.id.textViewErrorMail).setVisibility(View.GONE);

        String user = username.getText().toString();
        String full = fullName.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();



        if(user.length()>0 && full.length()>0 && mail.length()>0
                && pass.length()>0){
            if (!validarEmail(mail)){
                findViewById(R.id.textViewErrorMail).setVisibility(View.VISIBLE);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                return;
            }
            if (!pass.equals(pass2)){
                findViewById(R.id.textViewErrorPass).setVisibility(View.VISIBLE);
                password.setText("");
                password2.setText("");
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                return;
            }
        } else {
            findViewById(R.id.textViewErrorCampos).setVisibility(View.VISIBLE);
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            return;
        }

        RegisterUserTO userRegister = new RegisterUserTO(user,pass,full,mail,"https://fondosmil.com/fondo/34722.png");

        Call<UserTO> call = apiInterface.addUser(userRegister);
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse(Call<UserTO> call, Response<UserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("AddUser", "Error addUser"+response.code());
                    Toast.makeText(Register.this, "User name already registered" , Toast.LENGTH_LONG).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    return;
                }
                UserTO userTO =response.body();
                Toast.makeText(Register.this, "Welcome " + userTO.getUserName(), Toast.LENGTH_LONG).show();
                Log.d("AddUser", "Successful addUser "+ userTO.getUserName());

                SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user",userRegister.getUserName());
                editor.putString("password",userRegister.getPassword());
                editor.putString("image",userRegister.getImage_url());
                Log.d("AddUser", "Save user--> " + userRegister.getUserName());
                Log.d("AddUser", "Save password --> " + userRegister.getPassword());
                editor.commit();

                Intent intentMainActivity = new Intent(Register.this, HomeActivity.class);
                startActivity(intentMainActivity);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(Register.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Log.d("AddUser", "Error addUser in getting response from service using retrofit: "+t.getMessage());
            }
        });
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        Intent intentLoginActivity = new Intent(Register.this, Login.class);
        startActivity(intentLoginActivity);
        finish();
    }
}