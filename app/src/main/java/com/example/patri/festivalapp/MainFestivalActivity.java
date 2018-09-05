package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFestivalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView countdownMain;

    private static Database db;

    public static Database getDb() {
        return db;
    }

    // This method calculates the total cost
    public static double getTotalCost() {
        ArrayList<Cost> values;
        values = db.readCostData();
        double totalCost = 0.0;
        for (int i = 0; i < values.size(); i++) {
            totalCost = totalCost + values.get(i).getPrice();
        }
        totalCost = Math.round(totalCost * 100.0) / 100.0;
        return totalCost;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Beispiel für die Verwendung der Datenbank
        //Database db = new Database(this); darf nur in der MainActivity stehen, um auf die Datenbank in anderen
        //zugreifen zu können wird die Getter-Methode verwendet

        db = new Database(this);

        setContentView(R.layout.activity_main_festival);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView totalCostMain = (TextView) findViewById(R.id.total_cost_price_main);
        totalCostMain.setText(String.valueOf(getTotalCost()) + "€");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_festival, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_packlist) {
            startActivity(new Intent(this, PacklistActivity.class));
        } else if (id == R.id.nav_weather) {
            startActivity(new Intent(this, WeatherActivity.class));
        } else if (id == R.id.nav_countdown) {
            startActivity(new Intent(this, CountdownActivity.class));

        } else if (id == R.id.nav_cost) {
            startActivity(new Intent(this, CostActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
