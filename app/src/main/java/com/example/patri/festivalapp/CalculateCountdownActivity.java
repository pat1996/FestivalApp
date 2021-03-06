package com.example.patri.festivalapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class CalculateCountdownActivity extends Activity {

    private GestureDetectorCompat gestureObject;
    private EditText enterDay, enterMonth, enterYear;
    private Button startCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_countdown);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        startCountdown = (Button) findViewById(R.id.start_countdown_btn);
        enterDay = (EditText) findViewById(R.id.day_edit);
        enterMonth = (EditText) findViewById(R.id.month_edit);
        enterYear = (EditText) findViewById(R.id.year_edit);

        startCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Makes sure everything is filled in correctly
                if (enterYear.getText().toString().isEmpty() || enterMonth.getText().toString().isEmpty() || enterDay.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Du hast nicht alles korrekt ausgefüllt",Toast.LENGTH_LONG).show();
                    return;
                }else if (getFestivalYear()<2017){
                    Toast.makeText(getApplicationContext(),"Du musst das Jahr korrekt und vollständig ausfüllen",Toast.LENGTH_LONG).show();
                    return;
                } else if (getFestivalDay()>31){
                    Toast.makeText(getApplicationContext(),"Du musst den Tag korrekt ausfüllen",Toast.LENGTH_LONG).show();
                    return;
                }else if (getFestivalMonth()>12){
                    Toast.makeText(getApplicationContext(),"Du musst den Monat korrekt ausfüllen",Toast.LENGTH_LONG).show();
                    return;
                } else{
                    Database db = MainFestivalActivity.getDb();
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.YEAR, getFestivalYear());
                    date.set(Calendar.MONTH, getFestivalMonth()-1);
                    date.set(Calendar.DAY_OF_MONTH, getFestivalDay());
                    date.set(Calendar.HOUR, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);

                    db.insertIntoTable("CountdownTable", date);
                }
                 setCountdown();
            }
        });
    }

    // Getter und Setter
    public int getFestivalDay() {
        int day = Integer.parseInt(enterDay.getText().toString());
        return day;
    }

    public int getFestivalMonth() {
        int month = Integer.parseInt(enterMonth.getText().toString());
        return month;
    }

    public int getFestivalYear() {
        int year = Integer.parseInt(enterYear.getText().toString());
        return year;
    }

    // Build up a date-object out of the informations of the editTexts
    private Calendar getFestivalDate (){
        Calendar festival = Calendar.getInstance();
        festival.set(Calendar.YEAR, getFestivalYear());
        festival.set(Calendar.MONTH, getFestivalMonth()-1);
        festival.set(Calendar.DATE, getFestivalDay()+1); //+1 so the countdown does not show 0 on the last day before the festival
        return festival;
    }

    // Calculates the remaining days / the remaining time in milliseconds
   private long getRemainingDays() {
       Calendar today= Calendar.getInstance();
       Calendar festivalDate = getFestivalDate();
       Calendar timeNow = Calendar.getInstance();

       long offset = timeNow.get(Calendar.ZONE_OFFSET) + timeNow.get(Calendar.DST_OFFSET);

       long timeSinceMidnight = (timeNow.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000);

       long remainingDays = festivalDate.getTimeInMillis() - (today.getTimeInMillis()+timeSinceMidnight);
       return remainingDays;
   }

    // Starts the countdownActivity
    public void setCountdown() {
        Intent intentCountdown = new Intent(getApplicationContext(), CountdownActivity.class);
        this.finish();
        startActivity(intentCountdown);
    }

    // The following override method and the class LearnGesture are needed for the swipe functionality
    // So the user can go back to the MainFestivalActivity only with a swipe to the left
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        int minStep=30;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY){

            if(e2.getX() < (e1.getX() - minStep)){
                Intent intent = new Intent(CalculateCountdownActivity.this, CountdownActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
        }
    }
}

