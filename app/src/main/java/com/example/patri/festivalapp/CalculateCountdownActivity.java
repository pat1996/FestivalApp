package com.example.patri.festivalapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class CalculateCountdownActivity extends Activity {

    EditText enterDay, enterMonth, enterYear;
    Button startCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_countdown);

        startCountdown = (Button) findViewById(R.id.start_countdown_btn);

        startCountdown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setCountdown();
                setWidget();
            }
        });

    }

    //Getter und Setter

    public int getFestivalDay() {
        enterDay = (EditText) findViewById(R.id.day_edit);
        int day = Integer.parseInt(enterDay.getText().toString());
        return day;
    }

    public int getFestivalMonth() {
        enterMonth = (EditText) findViewById(R.id.month_edit);
        int month = Integer.parseInt(enterMonth.getText().toString());
        return month;
    }

    public int getFestivalYear() {
        enterYear = (EditText) findViewById(R.id.year_edit);
        int year = Integer.parseInt(enterYear.getText().toString());

        return year;
    }

    public String getRemainingDays() {
        saveFestivalDate();
        Calendar today = Calendar.getInstance();
        Calendar festivalDate = readFestivalDate();

        long diff = festivalDate.getTimeInMillis() - today.getTimeInMillis();
        int days = (int) (diff / (1000 * 60 * 60 * 24));

        return Integer.toString(days);
    }

    public void saveFestivalDate() {
        Calendar festival = Calendar.getInstance();

        festival.set(Calendar.YEAR, getFestivalYear());
        festival.set(Calendar.MONTH, getFestivalMonth() - 1);
        festival.set(Calendar.DATE, getFestivalDay());

        Database db = MainFestivalActivity.getDb();
        db.insertIntoTable("CountdownTable", festival);
    }

    public Calendar readFestivalDate() {
        Calendar festival = Calendar.getInstance();
        Database db = MainFestivalActivity.getDb();

        Cursor res = db.selectAllFromTable("CountdownTable");
        res.moveToFirst();

        festival.setTimeInMillis(res.getLong(res.getColumnIndex("festivalDate")));

        return festival;

    }

    public void setCountdown() {
        Intent intentCountdown = new Intent(getApplicationContext(), CountdownActivity.class);
        intentCountdown.putExtra("countingDays", getRemainingDays());
        this.finish();
        startActivity(intentCountdown);
    }

    public void setWidget(){

        Intent widget = new Intent(getApplicationContext(),CountdownWidgetActivity.class);
        widget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
        widget.putExtra("countdownWidget", getRemainingDays());
        sendBroadcast(widget);
    }


}

