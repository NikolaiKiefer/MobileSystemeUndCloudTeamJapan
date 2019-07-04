package com.example.mobilesystemeundcloudteamjapan.tracking;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class GPS {

    private double longitude;
    private double latitude;
    private long lastUpdate;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() { return latitude; }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    private void sendGpsToServer() {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String projectId = "570002243450";
        Log.d("GPS", "Try to send a Message at Server: "+projectId);
        fm.send(new RemoteMessage.Builder( projectId + "@gcm.googleapis.com")
                .setMessageId("1")
                .addData("action", "GPS")
                .addData("latitude", "" + getLatitude())
                .addData("longitude", "" + getLongitude())
                .addData("timestamp", "" + System.currentTimeMillis())
                .build());
    }

    final public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLongitude(location.getLongitude());
            setLatitude(location.getLatitude());
            if(System.currentTimeMillis() - lastUpdate > 5000) {
                lastUpdate = System.currentTimeMillis();
                sendGpsToServer();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
