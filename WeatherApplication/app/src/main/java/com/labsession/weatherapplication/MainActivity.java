package com.labsession.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button search;
    TextView show;
    String url;

    class getWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                ;

                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    result.append(line).append("\n");
                }
                return result.toString();
                }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("main");

//                        "temp":32,
//                        "feels_like":32.3,
//                        "temp_min":32,
//                        "temp_max":32,
//                        "pressure":997,
//                        "humidity":62
                weatherInfo = weatherInfo.replace("temp", " Current Temperature:");
                weatherInfo = weatherInfo.replace(",", "\n");
                weatherInfo = weatherInfo.replace("feels_like", "Feels Like");
                weatherInfo = weatherInfo.replace("\"", "");
                weatherInfo = weatherInfo.replace("{", "");
                weatherInfo = weatherInfo.replace("}", "");

                show.setText(weatherInfo);
                }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityName);
        search = findViewById(R.id.search);
        show = findViewById(R.id.show);

        final String[] temp = {""};

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Button Clicked",Toast.LENGTH_SHORT).show();

                String city = cityName.getText().toString();


                try {

                    if (city != null)
                    {
                        url = "http://openweathermap.org/data/2.5/weather?q=" + city + "&appid=439d4b804bc8187953eb36d2a8c26a02";
                    }
                    else
                        {
                        Toast.makeText(MainActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                        }
                    getWeather task = new getWeather();
                    temp[0] = task.execute(url).get();
                }

                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }


                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if (temp[0] == null)
                {
                    show.setText("Connot find Weather");
                }
            }
        });
    }
}
