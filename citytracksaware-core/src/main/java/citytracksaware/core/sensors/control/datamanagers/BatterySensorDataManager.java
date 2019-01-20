package citytracksaware.core.sensors.control.datamanagers;

import android.content.*;
import android.database.*;
import android.util.*;

import com.aware.providers.*;
import com.google.gson.*;

import org.json.*;

import citytracksaware.core.sensors.model.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class BatterySensorDataManager extends SensorDataManager {

    public BatterySensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Battery_Provider.Battery_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        BatterySensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new BatterySensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setBatteryStatus(sensor_data.getInt(sensor_data.getColumnIndex("battery_status")));
            sensorData.setBatteryScale(sensor_data.getInt(sensor_data.getColumnIndex("battery_scale")));
            sensorData.setBatteryVoltage(sensor_data.getInt(sensor_data.getColumnIndex("battery_voltage")));
            sensorData.setBatteryTemperature(sensor_data.getInt(sensor_data.getColumnIndex("battery_temperature")));
            sensorData.setBatteryAdaptor(sensor_data.getInt(sensor_data.getColumnIndex("battery_adaptor")));
            sensorData.setBatteryHealth(sensor_data.getInt(sensor_data.getColumnIndex("battery_health")));
            sensorData.setBatteryTechnology(sensor_data.getString(sensor_data.getColumnIndex("battery_technology")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "battery-readings");
        }

        clearLocalDatabase();
    }
}
