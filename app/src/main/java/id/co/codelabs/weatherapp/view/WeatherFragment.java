package id.co.codelabs.weatherapp.view;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import id.co.codelabs.weatherapp.R;
import id.co.codelabs.weatherapp.RemoteWeatherFetch;
import id.co.codelabs.weatherapp.model.entity.Cuaca;
import id.co.codelabs.weatherapp.model.service.WeatherApiImplement;
import id.co.codelabs.weatherapp.presenter.WeatherPresenterImpl;
import id.co.codelabs.weatherapp.utility.CityPreference;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements WeatherView {
    Typeface weatherFont;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    Handler handler;

    private SwipeRefreshLayout mSrl;
    private WeatherPresenterImpl mPresenter;

    public WeatherFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        handler = new Handler();

        mPresenter = new WeatherPresenterImpl(this,new WeatherApiImplement());

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");

        updateWeatherData(new CityPreference(getActivity()).getCity());

        initView(rootView);
        //initSwipeRefreshLayout(rootView);

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    private void initView(View rootView){
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
    }
    /*
    private void initSwipeRefreshLayout(View rootView) {

        mSrl = (SwipeRefreshLayout) rootView.findViewById(R.id.srl);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadWeatherData();
            }
        });
    }
    */

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteWeatherFetch.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃"); // kelvin diubah ke celcius

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder); //petir
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle); //gerimis
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);//berkabut
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);//berawan
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);//salju
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);//hujan
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    public void changeCity(String city){
        updateWeatherData(city);
    }
    /*
    @Override
    public void showProgress() {
        if(!mSrl.isRefreshing()) {

            // make sure setRefreshing() is called after the layout done everything else
            mSrl.post(new Runnable() {
                @Override
                public void run() {
                    mSrl.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if(mSrl.isRefreshing()) {
            mSrl.setRefreshing(false);
        }
    }
    */
    @Override
    public void showWeather(Cuaca cuaca) {
        cityField.setText(cuaca.getNamaKota()+", "+cuaca.getIdNegara());
        updatedField.setText("Last Update on : "+cuaca.getLastUpdate());
        detailsField.setText(cuaca.getDescription()+"\n"+"Humidity : "+cuaca.getHumidity()+" %\n" + "Pressure: " + cuaca.getPressure() + " hPa");
        currentTemperatureField.setText(cuaca.getTemperature()+" ℃");
        setWeatherIcon(cuaca.getActualId(),cuaca.getSunrise(),cuaca.getSunset());
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(getActivity(), "Gagal mengambil data cuaca!", Toast.LENGTH_SHORT).show();
    }
}
