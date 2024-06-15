package com.s22010334.finalproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class BrightnessManager implements SensorEventListener {

    private static BrightnessManager instance;
    private Sensor lightSensor;
    private SensorManager sensorManager;
    private Context context;

    private BrightnessManager(Context context){
        this.context = context.getApplicationContext();
        sensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public static BrightnessManager getInstance(Context context) {
        if (instance == null) {
            instance = new BrightnessManager(context);
        }
        return instance;
    }

    public void start() {
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float ambientLight = event.values[0];
            setScreenBrightness(ambientLight);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void setScreenBrightness(float ambientLight) {
        int brightnessValue = (int) (255 * (ambientLight / 10000));
        brightnessValue = Math.min(brightnessValue, 255);
        brightnessValue = Math.max(brightnessValue, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(context)) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        }
    }
}
