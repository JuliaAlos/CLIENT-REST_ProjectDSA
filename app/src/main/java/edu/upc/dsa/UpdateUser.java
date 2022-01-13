package edu.upc.dsa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import edu.upc.dsa.transferObjects.RegisterUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateUser extends AppCompatActivity {

    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";
    TextView username;
    TextView fullName;
    TextView email;
    TextView password;
    TextView password2;
    ImageView image;
    String image_url;


    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        username = findViewById(R.id.textPlayer);
        fullName = findViewById(R.id.textFullName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        password2 = findViewById(R.id.textPassword2);
        image=findViewById(R.id.image);

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
                username.setText(data.getUserName());
                fullName.setText(data.getFullName());
                email.setText(data.getEmail());
                password.setText("");
                password2.setText("");
                image_url=data.getImage_url();
                SharedPreferences sharedPref = getSharedPreferences("image", Context.MODE_PRIVATE);
                Glide.with(UpdateUser.this).load(sharedPref.getString("image",image_url)).into(image);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("image",sharedPref.getString("image",image_url));
                editor.commit();

            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(UpdateUser.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
            }
        });
    }
    public void done(View view) {

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        String user = username.getText().toString();
        String full = fullName.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();



        if(user.length()>0 && full.length()>0 && mail.length()>0){
            if (!validarEmail(mail)){
                errorMessage("Invalid email");
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                return;
            }
            if (!pass.equals(pass2) && pass.length()>0){
                errorMessage("Error whit the new password");
                password.setText("");
                password2.setText("");
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                return;
            }
        } else {
            errorMessage("Some fields are empty");
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            return;
        }
        confirm();
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void errorMessage(String error){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(error)
                .setTitle("ERROR INPUT DATA UPDATE")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true);

        alerta.show();
    }

    private void update(){
        String user = username.getText().toString();
        String full = fullName.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences sharedPref2 = getSharedPreferences("image", Context.MODE_PRIVATE);

        if (pass.length()==0){
            pass = sharedPref.getString("password", null);
        }
        RegisterUserTO userRegister = new RegisterUserTO(user,pass,full,mail,sharedPref2.getString("image",image_url));


        Call<UserTO> call = apiInterface.updateUser(userRegister,sharedPref.getString("user", null));
        call.enqueue(new Callback<UserTO>() {
            @Override
            public void onResponse(Call<UserTO> call, Response<UserTO> response) {
                if(!response.isSuccessful()){
                    Log.d("updateUser", "Error addUser"+response.code());
                    Toast.makeText(UpdateUser.this, "User name already registered" , Toast.LENGTH_LONG).show();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    return;
                }
                UserTO userTO =response.body();
                Toast.makeText(UpdateUser.this, "SUCCESSFUL", Toast.LENGTH_LONG).show();
                Log.d("updateUser", "Successful addUser "+ userTO.getUserName());

                SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user",userRegister.getUserName());
                editor.putString("password",userRegister.getPassword());
                editor.putString("image",userRegister.getImage_url());
                Log.d("AddUser", "Save user--> " + userRegister.getUserName());
                Log.d("AddUser", "Save password --> " + userRegister.getPassword());
                editor.commit();

                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Intent intent = new Intent(UpdateUser.this, Profile.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserTO> call, Throwable t) {
                Toast.makeText(UpdateUser.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Log.d("updateUser", "Error addUser in getting response from service using retrofit: "+t.getMessage());
            }
        });

    }

    private void confirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                String m_Text = input.getText().toString();
                if(!m_Text.equals(sharedPref.getString("password", null))) {
                    dialog.cancel();
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    Toast.makeText(UpdateUser.this, "Incorrect password, try again", Toast.LENGTH_LONG).show();

                } else{
                    update();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
        builder.setCancelable(false).setTitle("CONFIRM TO COMPLETE");
        builder.setMessage("Introduce old password to confirm changes");

        builder.show();
    }

    public void back(View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Are you sure you want to discard all your change")
                .setTitle("CANCEL UPDATE")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UpdateUser.this, Profile.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alerta.show();
    }
    public void imageGalery(View view) {
        Intent intent = new Intent(this, ImageUser.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}