package app.test;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by guillaume on 06/04/18.
 */

public class MyLocationListener implements LocationListener {

    private View view;

    MyLocationListener(View v)
    {
        view = v;
    }

    @Override
    public void onLocationChanged(Location location) {
        Snackbar.make(view, "Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        System.out.println("Latitude : " + location.getLatitude() + " Longitude : " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
