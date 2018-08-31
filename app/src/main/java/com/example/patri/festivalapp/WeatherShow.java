package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherShow extends Activity {

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_weather);
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
    }

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
                Intent intent = new Intent(WeatherShow.this, MainFestivalActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
        }
    }
}
