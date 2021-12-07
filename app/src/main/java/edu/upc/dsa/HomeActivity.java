package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

  private DrawerLayout drawerLayout;
  //TextView headerTitle;
  //SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
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

    MenuItem menuItem = navigationView.getMenu().getItem(0);
    onNavigationItemSelected(menuItem);
    menuItem.setChecked(true);

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(HomeActivity.this, getString(R.string.title_click),
                Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int title;
    switch (menuItem.getItemId()) {
      case R.id.nav_profile:
        title = R.string.menu_profile;
        break;
      case R.id.nav_stats:
        title = R.string.menu_stats;
        Intent intentStats = new Intent(this, Stats.class);
        startActivity(intentStats);
        break;
      case R.id.nav_forum:
        title = R.string.menu_forum;
        Intent intentForum = new Intent(this, Forum.class);
        startActivity(intentForum);
        break;
      case R.id.nav_ranking:
        title = R.string.menu_ranking;
        Intent intentRanking = new Intent(this, Ranking.class);
        startActivity(intentRanking);
        break;
      default:
        throw new IllegalArgumentException("menu option not implemented!!");
    }

    Fragment fragment = HomeContentFragment.newInstance(getString(title));
    getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
            .replace(R.id.home_content, fragment)
            .commit();

    setTitle(getString(title));

    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
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
    SharedPreferences sharedPref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
    headerTitle = (TextView) findViewById(R.id.header_title);
    headerTitle.setText(sharedPref.getString("user","Hola"));
  }


  /** Map related functions */

  public void hangarClick(View view) {
    Intent intent = new Intent(this, VideoTemplate.class);
    startActivity(intent);
  }

  public void insigniaClick(View view) {
    Intent intent = new Intent(this, VideoTemplate.class);
    startActivity(intent);
  }

}