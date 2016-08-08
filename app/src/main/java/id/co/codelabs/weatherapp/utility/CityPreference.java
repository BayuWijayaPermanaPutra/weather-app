package id.co.codelabs.weatherapp.utility;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by bayu_wpp on 6/25/2016.
 */
public class CityPreference {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // If the user has not chosen a city yet, return
    // Bandung as the default city
    public String getCity(){
        return prefs.getString("city", "Sydney, AU");
    }

    public void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}
