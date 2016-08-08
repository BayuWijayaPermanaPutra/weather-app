package id.co.codelabs.weatherapp.model.interactor;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by bayu_ on 8/8/2016.
 */
public interface WeatherApi {
    interface WeatherServiceCallback {
        void onSuccess(JSONObject objectJSON);
        void onFailure();
    }
    void getWeather(WeatherServiceCallback callback, Context context, String city);
}
