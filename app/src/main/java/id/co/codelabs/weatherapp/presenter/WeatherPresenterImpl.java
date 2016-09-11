package id.co.codelabs.weatherapp.presenter;

import android.content.Context;

import id.co.codelabs.weatherapp.model.entity.Cuaca;
import id.co.codelabs.weatherapp.model.service.WeatherApi;
import id.co.codelabs.weatherapp.model.service.WeatherApiImplement;
import id.co.codelabs.weatherapp.utility.ExpressoIdlingResource;
import id.co.codelabs.weatherapp.view.WeatherView;

/**
 * Created by bayu_ on 9/10/2016.
 */
public class WeatherPresenterImpl implements WeatherPresenter {

    private final WeatherView mView;
    private final WeatherApiImplement mWeatherApi;

    public WeatherPresenterImpl(WeatherView mView, WeatherApiImplement mWeatherApi) {
        this.mView = mView;
        this.mWeatherApi = mWeatherApi;
    }


    @Override
    public void loadWeatherData() {
        //mView.showProgress();

        ExpressoIdlingResource.increment();

        mWeatherApi.getWeather(new WeatherApi.WeatherServiceCallback() {
            @Override
            public void onSuccess(Cuaca cuaca) {
                ExpressoIdlingResource.decrement();
          //      mView.hideProgress();
                mView.showWeather(cuaca);
            }

            @Override
            public void onFailure() {
                ExpressoIdlingResource.decrement();
                mView.showConnectionError();
            //    mView.hideProgress();
            }
        });

    }
}
