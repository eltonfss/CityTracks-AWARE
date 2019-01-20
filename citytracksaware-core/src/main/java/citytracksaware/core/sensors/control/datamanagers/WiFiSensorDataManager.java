package citytracksaware.core.sensors.control.datamanagers;

import android.content.*;
import android.database.*;
import android.util.*;

import com.aware.providers.*;
import com.google.gson.*;

import org.json.*;

import citytracksaware.core.sensors.control.datamanagers.*;
import citytracksaware.core.sensors.model.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class WiFiSensorDataManager extends SensorDataManager {

    public WiFiSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = WiFi_Provider.WiFi_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {
        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        WiFiSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new WiFiSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(sensor_data.getString(sensor_data.getColumnIndex("device_id")));
            sensorData.setDeviceAddress(sensor_data.getString(sensor_data.getColumnIndex("bssid")));
            sensorData.setDeviceName(sensor_data.getString(sensor_data.getColumnIndex("ssid")));
            sensorData.setDeviceRSSI(sensor_data.getString(sensor_data.getColumnIndex("rssi")));
            sensorData.setSecurityProtocols(sensor_data.getString(sensor_data.getColumnIndex("security")));
            sensorData.setBandFrequency(sensor_data.getInt(sensor_data.getColumnIndex("frequency")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "wifi-readings");
        }

        clearLocalDatabase();
    }
}
