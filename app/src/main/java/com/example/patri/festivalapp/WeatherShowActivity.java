package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.text.InputFilter;
import android.util.Log;
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

        String url = getUrl();

        new DownloadWeather(weatherDate, weatherCity, weatherDegree, weatherDescription).execute(url);

    }

    class DownloadWeather extends AsyncTask<String, Void, String[]> {

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
        protected String[] doInBackground(String... strings) {
            String weather;
            String[] data = new String[4];
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
                double celsius = Double.parseDouble(weather) - 273.15;
                celsius = Math.round(celsius);

                JSONObject jsonObjectCity = new JSONObject(builder.toString());
                String city = jsonObjectCity.getString("name");

                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray array = jsonObject.getJSONArray("weather");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject weather1 = array.getJSONObject(i);
                    String description = weather1.getString("description");
                    data[0] = String.valueOf(celsius);
                    data[1] = city;
                    data[2] = description;
                }

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                String formatted_date = String.valueOf(day + "." + month + "." + year);
                data[3] = formatted_date;

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String[] data) {
            weatherDegree.setText(data[0] + "°");
            weatherCity.setText(data[1]);
            weatherDescription.setText(data[2]);
            weatherDate.setText(data[3]);
        }
    }

    private String getUrl() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=%CITY%&appid=a054669249150807aa88528372b0e6b1";

        Database db = MainFestivalActivity.getDb();
        Cursor res = db.selectAllFromTable("WeatherTable");
        res.moveToFirst();

        String city = res.getString(res.getColumnIndex("City"));
        url = url.replace("%CITY%", city);

        return url;
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

