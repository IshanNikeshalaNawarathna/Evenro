package lk.evenro.even.model;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.firebase.auth.FirebaseAuth;

import lk.evenro.even.SignInActivity;

public class SensorHelper implements SensorEventListener {
    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final Context context;

    private static final float FLIP_THRESHOLD = 5f;

    public SensorHelper(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;
    }

    public void registerSensor() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[2] < FLIP_THRESHOLD) { // Detect flip
            FirebaseAuth.getInstance().signOut();
            context.startActivity(new Intent(context, SignInActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}