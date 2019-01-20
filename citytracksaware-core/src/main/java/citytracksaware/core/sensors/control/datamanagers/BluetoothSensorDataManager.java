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

public class BluetoothSensorDataManager extends SensorDataManager {

    public BluetoothSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Bluetooth_Provider.Bluetooth_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        WirelessSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new WirelessSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(sensor_data.getString(sensor_data.getColumnIndex("device_id")));
            sensorData.setDeviceAddress(sensor_data.getString(sensor_data.getColumnIndex("bt_address")));
            sensorData.setDeviceName(sensor_data.getString(sensor_data.getColumnIndex("bt_name")));
            sensorData.setDeviceRSSI(sensor_data.getString(sensor_data.getColumnIndex("bt_rssi")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "bluetooth-readings");
        }

        clearLocalDatabase();
    }
}
