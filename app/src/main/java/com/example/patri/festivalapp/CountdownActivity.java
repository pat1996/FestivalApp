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

public class CountdownActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView countdownView, timerView;
    Button addCountdown;
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
        addCountdown = (Button) findViewById(R.id.new_countdown_btn);

        addCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCountdown();
            }
        });

        setUpCountdown();

      /*  Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!this.isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String newNumberOfDays = calculateRemainingDays()+" "+"Tage";
                                countdownView.setText(newNumberOfDays);
                                Intent updateWidget = new Intent(getApplicationContext(), CountdownWidgetActivity.class);
                                updateWidget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
                                updateWidget.putExtra("newNumDaysWidget", newNumberOfDays);
                                sendBroadcast(updateWidget);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onTouchEvent( event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObjectCOSTACTIVITY.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        int minStep=500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY){

            if(e2.getX() < (e1.getX() - minStep)){
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

    public void setUpCountdown() {
        Database db = MainFestivalActivity.getDb();
        Cursor res = db.selectAllFromTable("CountdownTable");

        res.moveToFirst();

        String time = "0";
        int index = res.getColumnIndex("festivalDate");

        if (res.getCount() != 0) {
            Calendar today = Calendar.getInstance();
            Calendar festivalDate = Calendar.getInstance();
            festivalDate.setTimeInMillis(res.getLong(index));
            Calendar timeNow = Calendar.getInstance();

            long offset = timeNow.get(Calendar.ZONE_OFFSET) + timeNow.get(Calendar.DST_OFFSET);
            long timeSinceMidnight = (timeNow.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000);
            long leftTime = festivalDate.getTimeInMillis() - (today.getTimeInMillis()+timeSinceMidnight);

            CountDownTimer countDownTimer = new CountDownTimer(leftTime, 1000) { //1 Sekunde
                @Override
                public void onTick(long millisUntilFinished) {
                    int days = (int) (millisUntilFinished / (1000 * 60 * 60 * 24));
                    int hours = (int) (millisUntilFinished / (1000 * 60 * 60) % 24);
                    int mins = (int) (millisUntilFinished / (1000 * 60) % 60);
                    int secs = (int) (millisUntilFinished / (1000) % 60);

                    countdownView.setText((String.format("%d", days))+" "+"Tage");
                    timerView.setText(String.format("%02d:%02d:%02d", hours, mins, secs));



                    Intent widget = new Intent(getApplicationContext(),CountdownWidgetActivity.class);
                    widget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
                    widget.putExtra("countdownWidget", "Nur noch "+ (String.format("%d", days))+" Tage bis zum Festival");
                    sendBroadcast(widget);

                    Intent mainActivity = new Intent()
                }
                @Override
                public void onFinish() {

                }
            }.start();
        }
           /* int days = (int) (leftTime / (1000 * 60 * 60 * 24));
            leftTime = leftTime % (1000 * 60 * 60 * 24);

            int hour = (int) (leftTime / (1000 * 60 * 60));
            leftTime = leftTime % (1000 * 60 * 60);

            int min = (int) (leftTime / (1000 * 60));
            leftTime = leftTime % (1000 * 60);

            int sec = (int) (leftTime / 1000);

            time = String.valueOf(days +":"+ hour +":"+ min +":"+ sec);*/
        }


    public void editCountdown() {
        Intent startCountdownEdit = new Intent(this, CalculateCountdownActivity.class);
        startActivity(startCountdownEdit);
    }

}
