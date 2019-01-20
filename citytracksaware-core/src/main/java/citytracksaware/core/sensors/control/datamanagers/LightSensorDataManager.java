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

public class LightSensorDataManager extends SensorDataManager {

    public LightSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Light_Provider.Light_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        LightSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new LightSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setLuminance(sensor_data.getDouble(sensor_data.getColumnIndex("double_light_lux")));
            sensorData.setAccuracy(sensor_data.getInt(sensor_data.getColumnIndex("accuracy")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "light-readings");
        }

        clearLocalDatabase();
    }
}
