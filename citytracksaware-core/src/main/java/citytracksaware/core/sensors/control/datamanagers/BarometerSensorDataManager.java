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

public class BarometerSensorDataManager extends SensorDataManager {

    public BarometerSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Barometer_Provider.Barometer_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        BarometerSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData =  new BarometerSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setAirPressure(sensor_data.getDouble(sensor_data.getColumnIndex("double_values_0")));
            sensorData.setAccuracy(sensor_data.getInt(sensor_data.getColumnIndex("accuracy")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "barometer-readings");
        }

        clearLocalDatabase();
    }
}
