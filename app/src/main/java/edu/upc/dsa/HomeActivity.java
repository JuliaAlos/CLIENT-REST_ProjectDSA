package edu.upc.dsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
//import com.unity3d.player.UnityPlayerActivity;

import edu.upc.dsa.transferObjects.RankingTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

  ApiInterface apiInterface;
  public static final String API_URL = "http://147.83.7.203:8080/dsaApp/";

  private String userName;
  private String imageProfile;
  ProgressBar progressBar;
  String playerName;
  String rol;
  String distance;
  String time;
  String coins;

  private DrawerLayout drawerLayout;

  public void storeClick(View view) {
    Intent intent = new Intent(this, Stats.class);
    startActivity(intent);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
    playerName = sharedPref.getString("user","Hola");

    Toolbar toolbar = findViewById(R.id.toolbar);

    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(HomeActivity.this, getString(R.string.title_click),
                Toast.LENGTH_SHORT).show();
      }
    });
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    apiInterface = retrofit.create(ApiInterface.class);
    sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
    userName = sharedPref.getString("user", null);
    imageProfile = sharedPref.getString("image", "https://s03.s3c.es/imag/_v0/770x420/5/9/6/avion-vuelo.jpg");
    if (userName == null) {
      finish();
    }

    progressBar = findViewById(R.id.progressBar);

    MenuItem item = navigationView.getMenu().getItem(0);
    getRol(this.playerName, item);

    item = navigationView.getMenu().getItem(1);
    getDistance(this.playerName, item);

    item = navigationView.getMenu().getItem(2);
    getTime(this.playerName, item);

    item = navigationView.getMenu().getItem(3);
    getMoney(this.playerName, item);

  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

    switch (menuItem.getItemId()) {
      case R.id.nav_profile:
        Intent intentProfile = new Intent(this, Profile.class);
        startActivity(intentProfile);
        finish();
        break;
      case R.id.nav_level:

        break;
      case R.id.nav_distance:
        break;
      case R.id.nav_time:

        break;
      case R.id.nav_logout:
        logout();
        break;
      default:
        throw new IllegalArgumentException("menu option not implemented!!");
    }

    /*Fragment fragment = HomeContentFragment.newInstance(getString(title));
    getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
            .replace(R.id.home_content, fragment)
            .commit();

    setTitle(getString(title));*/

    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }

  public String getRol (String playerName , MenuItem item) {
    Call<RankingTO> call = apiInterface.getRol(playerName);
    call.enqueue(new Callback<RankingTO>() {
      @Override
      public void onResponse(Call<RankingTO> call, Response<RankingTO> response) {
        if (!response.isSuccessful()) {
          Log.d("Menu", "Error" + response.code());
          return;
        }
         rol = response.body().getScore();
         Log.d("Menu", "Rol: " + response.body().getScore());
         item.setTitle("Rol: " + rol);
         SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString("image",response.body().getImage_url());
         editor.commit();
      }

      @Override
      public void onFailure(Call<RankingTO> call, Throwable t) {
        Log.d("Menu", "Error:" + t.getMessage());
      }
    });
    return rol;
  }

  public String getDistance (String playerName , MenuItem item) {
    Call<RankingTO> call = apiInterface.getDistance(playerName);
    call.enqueue(new Callback<RankingTO>() {
      @Override
      public void onResponse(Call<RankingTO> call, Response<RankingTO> response) {
        if (!response.isSuccessful()) {
          Log.d("Menu", "Error" + response.code());
          return;
        }
        distance = response.body().getScore();
        Log.d("Menu", "Rol: " + response.body().getScore());
        item.setTitle("Distance: "+ distance + " m");
      }

      @Override
      public void onFailure(Call<RankingTO> call, Throwable t) {
        Log.d("Menu", "Error:" + t.getMessage());
      }
    });
    return distance;
  }

  public String getTime (String playerName , MenuItem item) {
    Call<RankingTO> call = apiInterface.getTime(playerName);
    call.enqueue(new Callback<RankingTO>() {
      @Override
      public void onResponse(Call<RankingTO> call, Response<RankingTO> response) {
        if (!response.isSuccessful()) {
          Log.d("Menu", "Error" + response.code());
          return;
        }
        time = response.body().getScore();
        Log.d("Menu", "Rol: " + response.body().getScore());
        item.setTitle("Time: "+ time + " h");
      }

      @Override
      public void onFailure(Call<RankingTO> call, Throwable t) {
        Log.d("Menu", "Error:" + t.getMessage());
      }
    });
    return time;
  }

  public String getMoney (String playerName , MenuItem item) {
    Call<RankingTO> call = apiInterface.getMoney(playerName);
    call.enqueue(new Callback<RankingTO>() {
      @Override
      public void onResponse(Call<RankingTO> call, Response<RankingTO> response) {
        if (!response.isSuccessful()) {
          Log.d("Menu", "Error" + response.code());
          return;
        }
        coins = response.body().getScore();
        Log.d("Menu", "Rol: " + response.body().getScore());
        item.setTitle("Bitcoins: "+ coins);
      }

      @Override
      public void onFailure(Call<RankingTO> call, Throwable t) {
        Log.d("Menu", "Error:" + t.getMessage());
      }
    });
    return coins;
  }

  public void logout() {
    Call<Void> call = apiInterface.logoutUser(userName);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {

        if (!response.isSuccessful()) {
          Log.d("LogoutUser", "Error logoutUser" + response.code());
          Toast.makeText(HomeActivity.this, "User not found", Toast.LENGTH_LONG).show();
          return;
        } else {
          Intent intentLogin = new Intent(HomeActivity.this, Login.class);
          startActivity(intentLogin);
        }

      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(HomeActivity.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
        Log.d("LoginUser", "Error logoutUser in getting response from service using retrofit: " + t.getMessage());
      }
    });
  }

  public void hangarClick(View view) {
    Intent intent = new Intent(this, Hangar.class);
    startActivity(intent);
  }

  public void gameLaunchClick(View view){
    Intent intent = new Intent(this, ChooseAirplane.class);
    startActivity(intent);
  }

  public void rankingClick(View view) {
    Intent intentRanking = new Intent(this, Ranking.class);
    startActivity(intentRanking);
  }


  public void socialClick(View view) {
    Intent intent = new Intent(this, Forum.class);
    startActivity(intent);
  }

  public void insigniaClick(View view) {
    Intent intent = new Intent(this, Insignia_menu.class);
    startActivity(intent);
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
    }
  }

  @Override
  public void onDrawerSlide(@NonNull View view, float v) {
    //cambio en la posición del drawer
  }

  @Override
  public void onDrawerOpened(@NonNull View view) {
    //el drawer se ha abierto completamente

  }

  @Override
  public void onDrawerClosed(@NonNull View view) {
    //el drawer se ha cerrado completamente
  }

  @Override
  public void onDrawerStateChanged(int i) {
    //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    TextView headerTitle;
    headerTitle = (TextView) findViewById(R.id.header_title);
    headerTitle.setText(userName);
    Glide.with(this).load(imageProfile).into((ImageView) findViewById(R.id.imageNav));
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == event.KEYCODE_BACK) {
      AlertDialog.Builder alerta = new AlertDialog.Builder(this);
      alerta.setMessage("Are you sure you want to exit Insignia")
              .setTitle("EXIT INSIGNIA")
              .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  logout();
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
    return super.onKeyDown(keyCode, event);
  }
}