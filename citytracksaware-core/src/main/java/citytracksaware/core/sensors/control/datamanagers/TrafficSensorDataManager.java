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

public class TrafficSensorDataManager extends SensorDataManager {

    public TrafficSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Traffic_Provider.Traffic_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        TrafficSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new TrafficSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setNetworkType(sensor_data.getInt(sensor_data.getColumnIndex("network_type")));
            sensorData.setReceivedBytes(sensor_data.getDouble(sensor_data.getColumnIndex("double_received_bytes")));
            sensorData.setSentBytes(sensor_data.getDouble(sensor_data.getColumnIndex("double_sent_bytes")));
            sensorData.setReceivedPackets(sensor_data.getDouble(sensor_data.getColumnIndex("double_received_packets")));
            sensorData.setSentPackets(sensor_data.getDouble(sensor_data.getColumnIndex("double_sent_packets")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "traffic-readings");
        }

        clearLocalDatabase();

    }
}
