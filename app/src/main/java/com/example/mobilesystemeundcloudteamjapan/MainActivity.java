package com.example.mobilesystemeundcloudteamjapan;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private TextView lightSensorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightSensorView = findViewById(R.id.lightSensorView);

        sensorManager.registerListener(
                lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }
        private final SensorEventListener lightSensorListener
                = new SensorEventListener() {

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }


            @Override
            public final void onSensorChanged(SensorEvent event) {
                    lightSensorView.setText(String.valueOf(event.values[0]));
                // Do something with this sensor data.
            }
        };
    }

