package com.example.mobilesystemeundcloudteamjapan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
// import com.google.android.gms.location.LocationListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

// import com.google.android.gms.location.LocationListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private TextView lightSensorView;
    private TextView acceloratorView;
    private Button gpsButton;
    private SignInButton signInButton;
    private Button signOutButton;
    private TextView gpsView;
    private TextView googleName;
    private TextView googlePreName;
    private TextView googleEmail;
    private TextView googleAccountId;
    private ImageView googleImg;
    GoogleSignInClient mGoogleSignInClient;

    private static final long MIN_TIME_TO_REFRSH = 3000L;
    private static final float MIN_DISTANCE_TO_REFRESH = 0F;
    private int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";

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
        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = findViewById(R.id.sign_out_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        googleName = findViewById(R.id.googleName);
        googlePreName = findViewById(R.id.googlePreName);
        googleAccountId = findViewById(R.id.googleAccId);
        googleEmail = findViewById(R.id.googleEmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sensorManager.registerListener(
                lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(
                acceloratorSensorListener,
                acceloratorSensor,
                SensorManager.SENSOR_DELAY_NORMAL);



        gpsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},12121);
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_TO_REFRSH,
                            MIN_DISTANCE_TO_REFRESH,
                            locationListener);
                }
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null){
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
            googleEmail.setVisibility(View.GONE);
            googleName.setVisibility(View.GONE);
            googlePreName.setVisibility(View.GONE);
            googleAccountId.setVisibility(View.GONE);
            googleEmail.setText(null);
            googleName.setText(null);
            googlePreName.setText(null);
            googleAccountId.setText(null);
        } else{
            googleEmail.setText(account.getEmail());
            googleName.setText(account.getFamilyName());
            googlePreName.setText(account.getGivenName());
            googleAccountId.setText(account.getId());
            signInButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
            googleEmail.setVisibility(View.VISIBLE);
            googleName.setVisibility(View.VISIBLE);
            googlePreName.setVisibility(View.VISIBLE);
            googleAccountId.setVisibility(View.VISIBLE);
        }
    }


    private final SensorEventListener acceloratorSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            acceloratorView.setText(String.valueOf(event.values[0]));
            View view = getWindow().getDecorView();
            if(event.values[0] >= 5) {
                // View view = getWindow().getDecorView();
                view.setBackgroundColor(0xfff00000);
            } if (event.values[0] <= -0.1){
                view.setBackgroundColor(0xff00ff00);
            }
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
            // In event.values[0] steht der wert des Licht sensors drin
            lightSensorView.setText(String.valueOf(event.values[0]));
            if (event.values[0] >= 10000) {
                Context context = getApplicationContext();

                CharSequence text = "Mach das Licht aus meine Fische schlafen";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                if (!toast.getView().isShown()) {
                    toast.show();
                }
            }
        }
    };



    final private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            gpsView.setText(String.valueOf(location.getLatitude()));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                Log.v(TAG, "HEIEIEIIEIEIEIEI");
                signOut();
                break;

            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.v(TAG, "HEEEEEEEEEEEEEEEEEELLLLLOOOOOOOOOOOOOOOOOOOOOO");
                        updateUI(null);
                        // ...
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

}

