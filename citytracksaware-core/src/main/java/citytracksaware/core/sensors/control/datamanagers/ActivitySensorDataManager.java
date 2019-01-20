package citytracksaware.core.sensors.control.datamanagers;

import android.content.*;
import android.database.*;
import android.provider.*;
import android.provider.Settings;
import android.util.*;

import com.aware.plugin.google.activity_recognition.*;
import com.google.gson.*;

import org.json.*;

import citytracksaware.core.sensors.model.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 11/12/2017.
 */

public class ActivitySensorDataManager extends SensorDataManager {

    public ActivitySensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Google_AR_Provider.Google_Activity_Recognition_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        ActivitySensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new ActivitySensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setActivityName(sensor_data.getString(sensor_data.getColumnIndex("activity_name")));
            sensorData.setActivityType(sensor_data.getInt(sensor_data.getColumnIndex("activity_type")));
            sensorData.setConfidence(sensor_data.getInt(sensor_data.getColumnIndex("confidence")));
            sensorData.setActivities(sensor_data.getString(sensor_data.getColumnIndex("activities")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "activity-readings");
        }

        clearLocalDatabase();
    }

}
