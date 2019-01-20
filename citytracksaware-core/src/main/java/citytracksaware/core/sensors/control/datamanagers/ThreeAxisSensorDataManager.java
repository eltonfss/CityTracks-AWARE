package citytracksaware.core.sensors.control.datamanagers;

import android.content.*;
import android.database.*;
import android.net.*;
import android.util.*;

import com.google.gson.*;

import org.json.*;

import citytracksaware.core.sensors.control.datamanagers.*;
import citytracksaware.core.sensors.model.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class ThreeAxisSensorDataManager extends SensorDataManager {

    protected String servicePath;

    public ThreeAxisSensorDataManager(Context context, UploadManager uploadManager, Gson gson, Uri contentURI, String servicePath) {
        super(context, uploadManager, gson);
        this.contentURI = contentURI;
        this.servicePath = servicePath;
    }

    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        ThreeAxisSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new ThreeAxisSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setxAxis(sensor_data.getDouble(sensor_data.getColumnIndex("double_values_0")));
            sensorData.setyAxis(sensor_data.getDouble(sensor_data.getColumnIndex("double_values_1")));
            sensorData.setzAxis(sensor_data.getDouble(sensor_data.getColumnIndex("double_values_2")));
            sensorData.setAccuracy(sensor_data.getInt(sensor_data.getColumnIndex("accuracy")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, servicePath);
        }

        clearLocalDatabase();
    }

}
