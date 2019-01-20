package citytracksaware.core.sensors.control;

import android.content.*;
import android.hardware.*;
import android.net.*;
import android.support.v4.content.*;
import android.util.*;
import android.widget.*;

import com.aware.*;
import com.aware.plugin.google.activity_recognition.*;
import com.aware.providers.*;
import com.google.gson.*;

import org.json.*;

import java.io.*;

import citytracksaware.core.sensors.control.datamanagers.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 11/5/2017.
 */

public class SensorManager{

    private Context context;
    private Intent aware;
    private UploadManager uploadManager;
    private Intent sensorDataUploadServiceIntent;
    private static SensorManager instance;

    public static SensorManager getInstance(Context context,  UploadManager uploadManager){
        if(instance == null){
            instance = new SensorManager(context, uploadManager);
        }
        return instance;
    }

    protected SensorManager(Context context, UploadManager uploadManager){
        this.context = context;
        this.uploadManager = uploadManager;
    }

    public boolean isAwareActive(){
        if(aware != null){
            return true;
        }
        return false;
    }

    public void startSensors() {
        //Initialize AWARE
        aware = new Intent(context, Aware.class);
        context.startService(aware);
        //set standard frequencies
        int standardFrequencyMicroseconds = 200000;
        int standardFrequencySeconds = 60;
        //Activate Accelerometer
        Aware.setSetting(context, Aware_Preferences.STATUS_ACCELEROMETER, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_ACCELEROMETER, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_ACCELEROMETER);
        //Activate Barometer
        Aware.setSetting(context, Aware_Preferences.STATUS_BAROMETER, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_BAROMETER, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_BAROMETER);
        //Activate Battery
        Aware.setSetting(context, Aware_Preferences.STATUS_BATTERY, true);
        Aware.startSensor(context, Aware_Preferences.STATUS_BATTERY);
        //Activate Bluetooth
        Aware.setSetting(context, Aware_Preferences.STATUS_BLUETOOTH, false);
//        Aware.setSetting(context, Aware_Preferences.FREQUENCY_BLUETOOTH, standardFrequencySeconds);
//        Aware.startSensor(context, Aware_Preferences.STATUS_BLUETOOTH);
        //Ativate Gravity
        Aware.setSetting(context, Aware_Preferences.STATUS_GRAVITY, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_GRAVITY, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_GRAVITY);
        //Activate Gyroscope
        Aware.setSetting(context, Aware_Preferences.STATUS_GYROSCOPE, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_GYROSCOPE, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_GYROSCOPE);
        //Activate Light
        Aware.setSetting(context, Aware_Preferences.STATUS_LIGHT, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_LIGHT, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_LIGHT);
        //Activate Linear Accelerometer
        Aware.setSetting(context, Aware_Preferences.STATUS_LINEAR_ACCELEROMETER, true);
        Aware.setSetting(
                context, Aware_Preferences.FREQUENCY_LINEAR_ACCELEROMETER, standardFrequencyMicroseconds
        );
        Aware.startSensor(context, Aware_Preferences.STATUS_LINEAR_ACCELEROMETER);
        //Activate Location
        Aware.setSetting(context, Aware_Preferences.STATUS_LOCATION_GPS, true);
        Aware.setSetting(context, Aware_Preferences.STATUS_LOCATION_NETWORK, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_LOCATION_GPS, 180);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_LOCATION_NETWORK, 300);
        Aware.setSetting(context, Aware_Preferences.MIN_LOCATION_GPS_ACCURACY, 150);
        Aware.setSetting(context, Aware_Preferences.MIN_LOCATION_NETWORK_ACCURACY, 150);
        Aware.setSetting(context, Aware_Preferences.LOCATION_EXPIRATION_TIME, 300);
        Aware.startSensor(context, Aware_Preferences.STATUS_LOCATION_GPS);
        Aware.startSensor(context, Aware_Preferences.STATUS_LOCATION_NETWORK);
        //Activate Magnetometer
        Aware.setSetting(context, Aware_Preferences.STATUS_MAGNETOMETER, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_MAGNETOMETER, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_MAGNETOMETER);
        //Activate Network
        Aware.setSetting(context, Aware_Preferences.STATUS_NETWORK_EVENTS, true);
        Aware.setSetting(context, Aware_Preferences.STATUS_NETWORK_TRAFFIC, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_NETWORK_TRAFFIC, standardFrequencySeconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_NETWORK_EVENTS);
        Aware.startSensor(context, Aware_Preferences.STATUS_NETWORK_TRAFFIC);
        //Activate Processor
        Aware.setSetting(context, Aware_Preferences.STATUS_PROCESSOR, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_PROCESSOR, 10);
        Aware.startSensor(context, Aware_Preferences.STATUS_PROCESSOR);
        //Activate Proxmity
        Aware.setSetting(context, Aware_Preferences.STATUS_PROXIMITY, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_PROXIMITY, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_PROXIMITY);
        //Activate Rotation
        Aware.setSetting(context, Aware_Preferences.STATUS_ROTATION, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_ROTATION, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_ROTATION);
        //Activate Screen
        Aware.setSetting(context, Aware_Preferences.STATUS_SCREEN, true);
        Aware.startSensor(context, Aware_Preferences.STATUS_SCREEN);
        //Activate Temperature
        Aware.setSetting(context, Aware_Preferences.STATUS_TEMPERATURE, true);
        Aware.setSetting(context, Aware_Preferences.FREQUENCY_TEMPERATURE, standardFrequencyMicroseconds);
        Aware.startSensor(context, Aware_Preferences.STATUS_TEMPERATURE);
        //Activate WiFi
        Aware.setSetting(context, Aware_Preferences.STATUS_WIFI, false);
//        Aware.setSetting(context, Aware_Preferences.FREQUENCY_WIFI, standardFrequencySeconds);
//        Aware.startSensor(context, Aware_Preferences.STATUS_WIFI);
        //Activate Activity Recognition
        Aware.startPlugin(context, Plugin.PACKAGE_NAME);
        Log.i("citytracks-aware", "Sensors started!");
    }

    public void stopSensors() {
        //Deactivate Accelerometer
//        Aware.setSetting(context, Aware_Preferences.STATUS_ACCELEROMETER, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_ACCELEROMETER);
        //Deactivate Barometer
//        Aware.setSetting(context, Aware_Preferences.STATUS_BAROMETER, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_BAROMETER);
        //Deactivate Battery
//        Aware.setSetting(context, Aware_Preferences.STATUS_BATTERY, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_BATTERY);
        //Deactivate Bluetooth
//        Aware.setSetting(context, Aware_Preferences.STATUS_BLUETOOTH, false);
//        Aware.stopSensor(context, Aware_Preferences.STATUS_BLUETOOTH);
        //Deativate Gravity
//        Aware.setSetting(context, Aware_Preferences.STATUS_GRAVITY, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_GRAVITY);
        //Deactivate Gyroscope
//        Aware.setSetting(context, Aware_Preferences.STATUS_GYROSCOPE, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_GYROSCOPE);
        //Deactivate Light
//        Aware.setSetting(context, Aware_Preferences.STATUS_LIGHT, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_LIGHT);
        //Deactivate Linear Accelerometer
//        Aware.setSetting(context, Aware_Preferences.STATUS_LINEAR_ACCELEROMETER, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_LINEAR_ACCELEROMETER);
        //Deactivate Location
//        Aware.setSetting(context, Aware_Preferences.STATUS_LOCATION_GPS, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_LOCATION_GPS);
//        Aware.setSetting(context, Aware_Preferences.STATUS_LOCATION_NETWORK, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_LOCATION_NETWORK);
        //Deactivate Magnetometer
//        Aware.setSetting(context, Aware_Preferences.STATUS_MAGNETOMETER, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_MAGNETOMETER);
        //DeActivate Network
//        Aware.setSetting(context, Aware_Preferences.STATUS_NETWORK_EVENTS, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_NETWORK_EVENTS);
//        Aware.setSetting(context, Aware_Preferences.STATUS_NETWORK_TRAFFIC, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_NETWORK_TRAFFIC);
        //DeActivate Processor
//        Aware.setSetting(context, Aware_Preferences.STATUS_PROCESSOR, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_PROCESSOR);
        //DeActivate Proxmity
//        Aware.setSetting(context, Aware_Preferences.STATUS_PROXIMITY, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_PROXIMITY);
        //DeActivate Rotation
//        Aware.setSetting(context, Aware_Preferences.STATUS_ROTATION, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_ROTATION);
        //DeActivate Screen
//        Aware.setSetting(context, Aware_Preferences.STATUS_SCREEN, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_SCREEN);
        //DeActivate Temperature
//        Aware.setSetting(context, Aware_Preferences.STATUS_TEMPERATURE, false);
        Aware.stopSensor(context, Aware_Preferences.STATUS_TEMPERATURE);
        //DeActivate WiFi
//        Aware.setSetting(context, Aware_Preferences.STATUS_WIFI, false);
//        Aware.stopSensor(context, Aware_Preferences.STATUS_WIFI);
        //DeActivate ActivityRecognition
        Aware.stopPlugin(context, Settings.STATUS_PLUGIN_GOOGLE_ACTIVITY_RECOGNITION);
        //Deactivate aware
        //FIXME launching Exception context.stopService(aware);
        Log.i("citytracks-aware", "Sensors stoped!");
    }
    
    public void uploadSensorsData() {
        if(sensorDataUploadServiceIntent == null) {
            Log.i("citytracks-aware", "Stating SensorDataUploadService!");
            sensorDataUploadServiceIntent = new Intent(context, SensorDataUploadService.class);
            sensorDataUploadServiceIntent.setData(Uri.parse(uploadManager.getServerURL()));
            context.startService(sensorDataUploadServiceIntent);

            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String status = intent.getStringExtra(SensorDataUploadService.EXTENDED_DATA_STATUS);
                    if(status.equals("concluded")){
                        Log.i("citytracks-aware", "Sensor data uploaded sucessfully!");
                        //TODO notify user
                    }else if(status.equals("failed")){
                        Log.i("citytracks-aware", "Sensor data upload failed!");
                        //TODO notify user
                    }
                    sensorDataUploadServiceIntent = null;
                }
            };

            LocalBroadcastManager.getInstance(context).registerReceiver((receiver),
                    new IntentFilter(SensorDataUploadService.BROADCAST_ACTION)
            );
        }else{
            Log.i("citytracks-aware", "SensorDataUploadService already running!");
        }
    }

}