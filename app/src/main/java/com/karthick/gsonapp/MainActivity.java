package com.karthick.gsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "weather";

    class Weather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... address) {
            try {

                //ESTABLISH CONNECTION WITH ADDRESS
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                //RETRIEVE DATA FROM URL
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                //RETRIEVE DATA AND RETURN IT AS STRING
                int data = inputStreamReader.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = inputStreamReader.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private TextView textView;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();
        textView = findViewById(R.id.text);
        listView = findViewById(R.id.listview);

        Gson gson = new Gson();

        String content;
        Weather w = new Weather();
        try {
            content = w.execute("https://api.openweathermap.org/data/2.5/weather?q=London&appid=ebfcac32bda131ed5a160f2757938396").get();
            Log.i(TAG, "content : " + content);

            //DESERIALIZATION
            WeatherData weatherData = gson.fromJson(content, WeatherData.class);
            Log.i(TAG, "weatherData : " + weatherData);

            //EXTRACT DATA FROM JAVA OBJECTS
            Wind wind = weatherData.getWind();
            Coord coord = weatherData.getCoord();
            Sys sys = weatherData.getSys();
            com.karthick.gsonapp.Weather[] weather = weatherData.getWeather();
            Main main = weatherData.getMain();

            //APPEND FIELD NAMES TO THE VALUE
            String lon = "lon : " + coord.getLon();
            String lat = "lat : " + coord.getLat();
            String country = "country : " + sys.getCountry();
            String description = "description : " + weather[0].getDescription();
            String temp = "temp : " + main.getTemp() + "";
            String humidity = "humidity : " + main.getHumidity();
            String temp_min = "temp_min : " + main.getTemp_min();
            String temp_max = "temp_max : " + main.getTemp_max();
            String speed = "speed : " + wind.getSpeed() + "";
            String deg = "deg : " + wind.getDeg() + "";
            String dt = "dt : " + weatherData.getDt() + "";
            String name = "name : " + weatherData.getName();

            //PREPARE LIST FOR THE LISTVIEW
            arrayList.add(lon);
            arrayList.add(lat);
            arrayList.add(country);
            arrayList.add(description);
            arrayList.add(temp);
            arrayList.add(humidity);
            arrayList.add(temp_min);
            arrayList.add(temp_max);
            arrayList.add(speed);
            arrayList.add(deg);
            arrayList.add(dt);
            arrayList.add(name);

            //PREPARE ADAPTER
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
