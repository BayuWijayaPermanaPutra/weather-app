package id.co.codelabs.weatherapp.model.service;

import android.content.Context;

import id.co.codelabs.weatherapp.model.entity.Cuaca;

/**
 * Created by bayu_ on 8/8/2016.
 */
public interface WeatherApi {
    interface WeatherServiceCallback {
        void onSuccess(Cuaca cuaca);
        void onFailure();
    }
    void getWeather(WeatherServiceCallback callback);
}
