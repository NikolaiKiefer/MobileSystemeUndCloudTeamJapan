package com.example.mobilesystemeundcloudteamjapan.tracking;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Light {
    private double lightValue;
    private long lastUpdate;

    public double getLightValue() {
        return lightValue;
    }

    public void setLightValue(double lightValue) {
        this.lightValue = lightValue;
    }

    public void sendLightToServer(FirebaseMessaging fm) {
        // FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String projectId = "570002243450";
        Log.d("Light", "Try to send a Message at Server: "+projectId);
        fm.send(new RemoteMessage.Builder( projectId + "@gcm.googleapis.com")
                .setMessageId("1")
                .addData("action", "LIGHT")
                .addData("light", "" + getLightValue())
                .addData("timestamp", "" + System.currentTimeMillis())
                .build());
    }

    public final SensorEventListener lightSensorListener
            = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


        @Override
        public final void onSensorChanged(SensorEvent event) {
            double eventLightValue = event.values[0];
            setLightValue(eventLightValue);
            if(System.currentTimeMillis() - lastUpdate > 5000) {
                lastUpdate = System.currentTimeMillis();
                // sendLightToServer();
            }
        }
    };
}
