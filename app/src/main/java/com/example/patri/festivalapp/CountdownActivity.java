package com.example.patri.festivalapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class CountdownActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView countdownView;
    Button addCountdown;
    private static final String noFestival = "0 Days";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_countdown);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        countdownView = (TextView) findViewById(R.id.countdown_view);
        addCountdown = (Button) findViewById(R.id.new_countdown_btn);

        addCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCountdown();
            }
        });
//        Intent getDays = getIntent();
//        String remainingDays = getDays.getStringExtra("countingDays");
//        countdownView.setText(remainingDays+" "+"Days");

        calculateRemainingDays();
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
            Intent i = new Intent(this, PacklistActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_weather) {
            Intent i = new Intent(this, WeatherActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_countdown) {


        } else if (id == R.id.nav_cost) {
            Intent i = new Intent(this, CostActivity.class);
            this.finish();
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateRemainingDays() {
        Database db = MainFestivalActivity.getDb();
        Cursor res = db.selectAllFromTable("CountdownTable");

        res.moveToFirst();
        Log.e("Anzahl.", String.valueOf(res.getCount()));

        int days = 0;
        int index = res.getColumnIndex("festivalDate");

        if (res.getCount() != 0) {
            Calendar today = Calendar.getInstance();
            Calendar festivalDate = Calendar.getInstance();
            festivalDate.setTimeInMillis(res.getLong(index));

            long diff = festivalDate.getTimeInMillis() - today.getTimeInMillis();
            days = (int) (diff / (1000 * 60 * 60 * 24));
        }
        countdownView.setText(String.valueOf(days)+" "+"Tage");
    }

    public void editCountdown() {
        Intent startCountdownEdit = new Intent(this, CalculateCountdownActivity.class);
        startActivity(startCountdownEdit);
    }

}
