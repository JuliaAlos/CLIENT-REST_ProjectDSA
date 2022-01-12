package edu.upc.dsa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
//import com.unity3d.player.UnityPlayerActivity;

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
  ProgressBar progressBar;

  private DrawerLayout drawerLayout;

  public void storeClick(View view) {
    Intent intent = new Intent(this, Stats.class);
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);


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
    SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
    userName = sharedPref.getString("user", null);
    if (userName == null) {
      finish();
    }
    progressBar = findViewById(R.id.progressBar);

  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

    switch (menuItem.getItemId()) {
      case R.id.nav_profile:
        Intent intentProfile = new Intent(this, Profile.class);
        startActivity(intentProfile);
        break;
      case R.id.nav_stats:
        Intent intentStats = new Intent(this, Stats.class);
        startActivity(intentStats);
        break;
      case R.id.nav_forum:
        Intent intentForum = new Intent(this, Forum.class);
        startActivity(intentForum);
        break;
      case R.id.nav_ranking:
        Intent intentRanking = new Intent(this, Ranking.class);
        startActivity(intentRanking);
        break;
      case R.id.nav_hangar:
        Intent intentHangar = new Intent(this, Hangar.class);
        startActivity(intentHangar);
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

  public void logout() {
    progressBar.setVisibility(View.VISIBLE);
    Call<Void> call = apiInterface.logoutUser(userName);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {

        if (!response.isSuccessful()) {
          Log.d("LogoutUser", "Error logoutUser" + response.code());
          Toast.makeText(HomeActivity.this, "User not found", Toast.LENGTH_LONG).show();
          progressBar.setVisibility(View.GONE);
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


  /**
   * Map related functions
   */

  public void hangarClick(View view) {
    Intent intent = new Intent(this, Hangar.class);
    startActivity(intent);
  }

  /*
  public void gameLaunchClick(View view){
    Intent intent = new Intent(this, UnityPlayerActivity.class);
    startActivity(intent);
  }
  */


  public void socialClick(View view) {
    Intent intent = new Intent(this, Forum.class);
    startActivity(intent);
  }

  public void insigniaClick(View view) {
    Intent intent = new Intent(this, Insignia_menu.class);
    startActivity(intent);
  }

  /**
   * Nav menu configuration
   */
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

