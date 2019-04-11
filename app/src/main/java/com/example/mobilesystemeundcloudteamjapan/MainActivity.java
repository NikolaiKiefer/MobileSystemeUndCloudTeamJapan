package com.example.mobilesystemeundcloudteamjapan;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
// import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private TextView lightSensorView;
    private TextView acceloratorView;
    private Button gpsButton;
    private TextView gpsView;

    private static final long MIN_TIME_TO_REFRSH = 3000L;
    private static final float MIN_DISTANCE_TO_REFRESH = 0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor acceloratorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightSensorView = findViewById(R.id.lightSensorView);
        acceloratorView = findViewById(R.id.acceloratorText);
        gpsButton = findViewById(R.id.gpsButton);
        gpsView = findViewById(R.id.gpsWertView);


        sensorManager.registerListener(
                lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(
                acceloratorSensorListener,
                acceloratorSensor,
                SensorManager.SENSOR_DELAY_NORMAL);





/*
        gpsButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_TO_REFRSH,
                        MIN_DISTANCE_TO_REFRESH,
                        locationListener);

                locationManager.requestLocationUpdates();

            }
        });

*/
    }

    private final SensorEventListener acceloratorSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            acceloratorView.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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

   /* final private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }
    };*/
}

