package id.co.codelabs.weatherapp.model.service;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import id.co.codelabs.weatherapp.R;
import id.co.codelabs.weatherapp.model.entity.Cuaca;

/**
 * Created by bayu_wpp on 8/8/2016.
 */
public class WeatherApiImplement implements WeatherApi {
    //private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=Bandung,id&appid=b35ad63f5f5ced9b4bceaf049edf6dfb";

    @Override
    public void getWeather(WeatherServiceCallback callback) {
        new LoadWeatherAsyncTask(callback).execute();
    }


    protected class LoadWeatherAsyncTask extends AsyncTask<Void, Void, Cuaca> {
        private final WeatherServiceCallback mCallback;

        public LoadWeatherAsyncTask(WeatherServiceCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Cuaca doInBackground(Void... params) {
            try {
                /*
                URL url = new URL(String.format(OPEN_WEATHER_MAP_API,mCity));
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();

                connection.addRequestProperty("x-api-key",
                        mContext.getString(R.string.open_weather_maps_app_id));
                */
                URL url = new URL(OPEN_WEATHER_MAP_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());
                DateFormat df = DateFormat.getDateTimeInstance();

                String updatedOn = df.format(new Date(data.getLong("dt")*1000));

                JSONObject details = data.getJSONArray("weather").getJSONObject(0);

                JSONObject main = data.getJSONObject("main");

                Cuaca cuaca = new Cuaca(
                        data.getString("name"),
                        data.getJSONObject("sys").getString("country"),
                        updatedOn,
                        main.getString("humidity"),
                        details.getString("description").toUpperCase(Locale.US),
                        main.getString("pressure"),
                        String.format("%.2f", main.getDouble("temp")),
                        details.getInt("id"),
                        data.getJSONObject("sys").getLong("sunrise") * 1000,
                        data.getJSONObject("sys").getLong("sunset") * 1000
                );

                if(data.getInt("cod") != 200){
                    //Toast.makeText(mContext, "Data Cuaca berada di luar jangkauan!", Toast.LENGTH_LONG).show();
                    return null;
                }
                return cuaca;
            }catch(Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cuaca cuaca) {
            if (cuaca != null) {
                mCallback.onSuccess(cuaca);
            } else {
                mCallback.onFailure();
            }
        }
    }
}
