package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
            enterMonth = (EditText) findViewById(R.id.moth_edit);
            int month = Integer.parseInt(enterMonth.getText().toString());
            return month;
        }

        public int getFestivalYear() {
            enterYear = (EditText) findViewById(R.id.year_edit);
            int year = Integer.parseInt(enterYear.getText().toString());

            return year;
        }

        public String getRemainingDays() {
            Calendar today = Calendar.getInstance();
            Calendar festival = Calendar.getInstance();

            festival.set(Calendar.YEAR, getFestivalYear());
            festival.set(Calendar.MONTH, getFestivalMonth()-1);
            festival.set(Calendar.DATE, getFestivalDay());

            long diff = festival.getTimeInMillis() - today.getTimeInMillis();
            int days = (int) (diff / (1000 * 60 * 60 * 24));

            return Integer.toString(days);
        }

        public void setCountdown (){

            Intent intentCountdown= new Intent (getApplicationContext(),CountdownActiviy.class);
            intentCountdown.putExtra("countingDays",getRemainingDays());
            this.finish();
            startActivity(intentCountdown);
        }

    }

