package id.co.codelabs.weatherapp.utility;

import android.support.test.espresso.IdlingResource;

/**
 * Created by bayu_ on 9/11/2016.
 */
public class ExpressoIdlingResource {
    private static SimpleIdlingResource mIdlingResource = new SimpleIdlingResource();

    public static void increment() {
        mIdlingResource.increment();
    }

    public static void decrement() {
        mIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
