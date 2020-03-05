package com.example.androiddaggerservice.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.concurrent.TimeUnit;

import dagger.android.DaggerService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ServicePos extends DaggerService {
    private Disposable disposable;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Context appContext = getApplicationContext();
    private final int REQUEST_CODE = 1004;
    private final String REQUEST_ACTION_CONTEXT = "REQUEST_ACTION_CONTEXT";
    private final String REQUEST_ACTION_VALUE = "REQUEST_ACTION_VALUE";
    private final String RESULT_ACTION_CONTEXT = "RESULT_ACTION_CONTEXT";
    private final String RESULT_ACTION_VALUE = "RESULT_ACTION_VALUE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Service", "In onBind");
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent("location_update");
                intent.putExtra(RESULT_ACTION_VALUE, location.getLongitude() + " " + location.getLatitude());
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000, 0, locationListener);
        }

        Notification.Builder builder = new Notification.Builder(this)
            .setSubText("service App");
        Notification notification;
        notification = builder.build();
        Log.e("ServicePos","ok onCreate");
        startForeground(5500, notification);
    }

    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(appContext, "Permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000, 0, locationListener);
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Toast.makeText(appContext, "Please enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        disposable = Observable.interval(500, TimeUnit.MILLISECONDS)
            .subscribe(v -> Log.e("ServicePos","interval " + v), throwable ->
                Log.e("ServicePos","error " + throwable.getMessage()));

        return START_REDELIVER_INTENT;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onDestroy() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        //if (!disposable.isDisposed()) {
            disposable.dispose();
        //}
        stopSelf();
        super.onDestroy();
    }

}
