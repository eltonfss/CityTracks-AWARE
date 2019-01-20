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
 * Created by Elton Soares on 11/12/2017.
 */

public class GyroscopeSensorDataManager extends SensorDataManager {


    public GyroscopeSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Gyroscope_Provider.Gyroscope_Data.CONTENT_URI;
    }

    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        ThreeAxisSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new ThreeAxisSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setxAxis(sensor_data.getDouble(sensor_data.getColumnIndex("axis_x")));
            sensorData.setyAxis(sensor_data.getDouble(sensor_data.getColumnIndex("axis_y")));
            sensorData.setzAxis(sensor_data.getDouble(sensor_data.getColumnIndex("axis_z")));
            sensorData.setAccuracy(sensor_data.getInt(sensor_data.getColumnIndex("accuracy")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "gyroscope-readings");
        }

        clearLocalDatabase();
    }
}
