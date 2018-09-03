package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.provider.UserDictionary.Words.APP_ID;

public class WeatherShowActivity extends Activity {

    private GestureDetectorCompat gestureObject;
    private TextView weatherDate, weatherCity, weatherDegree, weatherDescription;

    private static final String APP_ID = "a054669249150807aa88528372b0e6b1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_weather);
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        weatherDate = (TextView) findViewById(R.id.weather_date);
        weatherCity = (TextView) findViewById(R.id.weather_city);
        weatherDegree = (TextView) findViewById(R.id.weather_degree);
        weatherDescription = (TextView) findViewById(R.id.weather_description);

        String url = "https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=a054669249150807aa88528372b0e6b1";



        new DownloadWeather(weatherDate, weatherCity, weatherDegree, weatherDescription).execute(url);

    }

    class DownloadWeather extends AsyncTask<String, Void, String> {

        private TextView weatherDate, weatherCity, weatherDegree, weatherDescription;

        public DownloadWeather(TextView weatherDate, TextView weatherCity, TextView weatherDegree, TextView weatherDescription) {
            this.weatherDate = weatherDate;
            this.weatherCity = weatherCity;
            this.weatherDegree = weatherDegree;
            this.weatherDescription = weatherDescription;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String weather = "UNDEFINED";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(builder.toString());
                JSONObject main = topLevel.getJSONObject("main");
                weather = String.valueOf(main.getDouble("temp"));

                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray array = jsonObject.getJSONArray("weather");

                String temp = String.valueOf(jsonObject.getDouble("temp"));
                String city = jsonObject.getString("name");
                String description = jsonObject.getString("description");

                weatherDegree.setText(temp);
                weatherCity.setText(city);
                weatherDescription.setText(description);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-TT");
                String formatted_date = sdf.format(calendar.getTime());
                weatherDate.setText(formatted_date);

                /*double temp_int = Double.parseDouble(temp);
                double conti = (temp_int - 32) /1.8000;
                conti = Math.round(conti);
                int i = (int)conti;
                weatherDegree.setText(String.valueOf(i));*/

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(String temp) {
            weatherDegree.setText(temp);
        }

    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        int minStep = 30;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {

            if (e2.getX() < (e1.getX() - minStep)) {
                Intent intent = new Intent(WeatherShowActivity.this, MainFestivalActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
        }
    }
}

