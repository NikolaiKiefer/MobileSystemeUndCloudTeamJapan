package com.example.mobilesystemeundcloudteamjapan.tracking;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class Accelorator {

    private long timestamp;
    private double acceloratorValue;
    private long lastUpdate;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAcceloratorValue() {
        return acceloratorValue;
    }

    public void setAcceloratorValue(double acceloratorValue) {
        this.acceloratorValue = acceloratorValue;
    }

    private void sendAcceloratorToServer() {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String projectId = "570002243450";
        Log.d("Accelorator", "Try to send a Message at Server: "+projectId);
        fm.send(new RemoteMessage.Builder( projectId + "@gcm.googleapis.com")
                .setMessageId("1")
                .addData("action", "ACCELORATOR")
                .addData("accelorator", "" + getAcceloratorValue())
                .addData("timestamp", "" + System.currentTimeMillis())
                .build());
    }

    public final SensorEventListener acceloratorListener
            = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


        @Override
        public final void onSensorChanged(SensorEvent event) {
            double tmpAccelorator = event.values[0];
            setAcceloratorValue(tmpAccelorator);
            if(System.currentTimeMillis() - lastUpdate > 5000) {
                lastUpdate = System.currentTimeMillis();
                sendAcceloratorToServer();
            }
        }
    };
}
