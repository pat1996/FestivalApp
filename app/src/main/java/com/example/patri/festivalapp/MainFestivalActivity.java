package com.example.patri.festivalapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainFestivalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Database db;
    private TextView countdownMain;

    private final static int NOTIFICATION_ID = 0;

    public static Database getDb() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        countdownMain=(TextView)findViewById(R.id.main_countdownView);
        setUpWidgetNew();
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

    // Method to create a notification
    private void createNotification(String title, String text){

        Intent notificationIntent = new Intent(this, MainFestivalActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.baseline_notifications_white_24dp)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setVibrate(new long[]{0, 300, 300, 300})
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_packlist) {
            startActivity(new Intent(this, PacklistActivity.class));
            createNotification("Erinnerung", "Hast du schon alles eingepackt? Gehe zurück zu deiner FestivalApp und checke es!");
        } else if (id == R.id.nav_weather) {
            startActivity(new Intent(this, WeatherActivity.class));
            createNotification("Erinnerung", "Hast du das Wetter im Blick? Gehe zurück zu deiner FestivalApp und checke es!");
        } else if (id == R.id.nav_countdown) {
            startActivity(new Intent(this, CountdownActivity.class));
            createNotification("Erinnerung", "Dein Festival startet bald! Gehe zurück zu deiner FestivalApp und checke es!");
        } else if (id == R.id.nav_cost) {
            startActivity(new Intent(this, CostActivity.class));
            createNotification("Erinnerung", "Hast du schon alle Kosten eingetragen? Gehe zurück zu deiner FestivalApp und checke es!");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Set up of the widget directly in the MainApp so it is always up to date
    // Set up of the main app countdown
    private void setUpWidgetNew () {
        if (getFestivalDate() != 0) {
            long festivalDate = getFestivalDate();
            Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
            long leftTime = festivalDate - today.getTimeInMillis();

            CountDownTimer countDownTimer = new CountDownTimer(leftTime, 1000) { //1 Sekunde
                @Override
                public void onTick(long millisUntilFinished) {
                    int days = (int) (millisUntilFinished / (1000 * 60 * 60 * 24));

                    countdownMain.setText("Nur noch " + (String.format("%d", days)) + " Tage bis zum Festival");

                    Intent widget = new Intent(getApplicationContext(), CountdownWidgetActivity.class);
                    widget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
                    widget.putExtra("countdownWidget", "Nur noch " + (String.format("%d", days)) + " Tage bis zum Festival");
                    sendBroadcast(widget);
                }

                @Override
                public void onFinish() {

                    countdownMain.setText("Das Festival ist heute!!"); //When the countdown is finished
                }
            }.start();
        }
    }

    // Extracts the festival date
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
}
