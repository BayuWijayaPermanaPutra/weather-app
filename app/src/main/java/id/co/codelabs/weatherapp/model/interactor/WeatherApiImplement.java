package id.co.codelabs.weatherapp.model.interactor;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import id.co.codelabs.weatherapp.R;

/**
 * Created by bayu_wpp on 8/8/2016.
 */
public class WeatherApiImplement implements WeatherApi {
    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    @Override
    public void getWeather(WeatherServiceCallback callback, Context context, String city) {
        new LoadWeatherAsyncTask(callback,context,city);
    }

    protected class LoadWeatherAsyncTask extends AsyncTask<Void, Void, JSONObject> {
        private final WeatherServiceCallback mCallback;
        private final Context mContext;
        private final String mCity;

        public LoadWeatherAsyncTask(WeatherServiceCallback callback, Context context, String city) {
            mCallback = callback;
            mContext = context;
            mCity = city;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            try {
                URL url = new URL(String.format(OPEN_WEATHER_MAP_API,mCity));
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();

                connection.addRequestProperty("x-api-key",
                        mContext.getString(R.string.open_weather_maps_app_id));

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());


                if(data.getInt("cod") != 200){
                    Toast.makeText(mContext, "Data Cuaca berada di luar jangkauan!", Toast.LENGTH_SHORT).show();
                    return null;
                }

                return data;
            }catch(Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                mCallback.onSuccess(jsonObject);
            } else {
                mCallback.onFailure();
            }
        }
    }

}
