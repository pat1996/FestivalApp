package com.example.patri.festivalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WeatherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GestureDetectorCompat gestureObjectCOSTACTIVITY;

    EditText citysearch;
    Button weather_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_weather);
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

        citysearch = (EditText)findViewById(R.id.citysearch);
        weather_button = (Button)findViewById(R.id.weather_button);

        weather_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWeather();

            }
        });
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
                Intent intent = new Intent(WeatherActivity.this, MainFestivalActivity.class);
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
            Intent i = new Intent(this,PacklistActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_weather) {

        } else if (id == R.id.nav_countdown) {
            Intent i = new Intent(this,CountdownActivity.class);
            this.finish();
            startActivity(i);

        } else if (id == R.id.nav_cost) {
            Intent i = new Intent(this,CostActivity.class);
            this.finish();
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void editWeather(){
        String cityString = citysearch.getText().toString();
        CityDB city = new CityDB();
        city.setCity(cityString);

        Database db = MainFestivalActivity.getDb();
        db.insertIntoTable("WeatherTable", city);

        if (cityString.isEmpty()) {
            Toast.makeText(this, "Gib bitte eine Stadt ein!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent startWeatherEdit = new Intent(this,WeatherShowActivity.class);
        startActivity(startWeatherEdit);
    }
}
