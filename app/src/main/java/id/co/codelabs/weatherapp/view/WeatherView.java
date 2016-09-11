package id.co.codelabs.weatherapp.view;

import id.co.codelabs.weatherapp.model.entity.Cuaca;

/**
 * Created by bayu_ on 9/10/2016.
 */

public interface WeatherView {
    //void showProgress();
    //void hideProgress();
    void showWeather(Cuaca cuaca);
    void showConnectionError();
}
