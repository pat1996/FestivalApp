package com.example.patri.festivalapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

public class CountdownActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView countdownView, timerView, countdownTextOne, countdownTextTwo;
    private Button addCountdown;
    private GestureDetectorCompat gestureObjectCOSTACTIVITY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_countdown);
        gestureObjectCOSTACTIVITY = new GestureDetectorCompat(this, new LearnGesture());
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
        timerView = (TextView) findViewById(R.id.countdownTimer);
        countdownTextOne=(TextView)findViewById(R.id.countdown_viewOne);
        countdownTextTwo=(TextView)findViewById(R.id.countdown_viewTwo);
        addCountdown = (Button) findViewById(R.id.new_countdown_btn);

        addCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCountdown();
            }
        });

        setUpCountdown(); //starts the countdown
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObjectCOSTACTIVITY.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        int minStep = 500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {

            if (e2.getX() < (e1.getX() - minStep)) {
                Intent intent = new Intent(CountdownActivity.this, MainFestivalActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
        }
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

    //gets the left time from the database
    // calculates the left days, hours, minutes and seconds
    // sets up the widget and the TextView to display the left days
    public void setUpCountdown() {
        if (getFestivalDate() != 0) {
            long festivalDate = getFestivalDate();
            Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
            long leftTime = festivalDate - today.getTimeInMillis();

            CountDownTimer countDownTimer = new CountDownTimer(leftTime, 1000) { //1 Sekunde
                @Override
                public void onTick(long millisUntilFinished) {
                    int days = (int) (millisUntilFinished / (1000 * 60 * 60 * 24));
                    int hours = (int) (millisUntilFinished / (1000 * 60 * 60) % 24);
                    int mins = (int) (millisUntilFinished / (1000 * 60) % 60);
                    int secs = (int) (millisUntilFinished / (1000) % 60);

                    countdownView.setText((String.format("%d", days)) + " " + "Tage");
                    timerView.setText(String.format("%02d:%02d:%02d", hours, mins, secs));


                   Intent widget = new Intent(getApplicationContext(), CountdownWidgetActivity.class);
                    widget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
                    widget.putExtra("countdownWidget", "Nur noch " + (String.format("%d", days)) + " Tage bis zum Festival");
                    sendBroadcast(widget);
                }

                @Override
                public void onFinish() {
                    countdownTextOne.setText("Hurra!!");
                    countdownView.setText("Heute");
                    countdownTextTwo.setText("ist das Festival");

                }
            }.start();
        }
    }

    // extracts the festival date
    private long getFestivalDate() {
        long daysLeft = 0;
        Database db = MainFestivalActivity.getDb();
        Cursor res = db.selectAllFromTable("CountdownTable");
        res.moveToFirst();
        if (res.getCount() != 0) {
            daysLeft = res.getLong(res.getColumnIndex("festivalDate"));
        }
        return daysLeft;
    }
        // starts the CalculateCountdownActivity
    public void editCountdown() {
        Intent startCountdownEdit = new Intent(this, CalculateCountdownActivity.class);
        startActivity(startCountdownEdit);
    }


}
