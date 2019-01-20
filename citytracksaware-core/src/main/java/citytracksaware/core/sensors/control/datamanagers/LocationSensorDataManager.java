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

public class LocationSensorDataManager extends SensorDataManager {

    public LocationSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Locations_Provider.Locations_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        LocationSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData  = new LocationSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setLatitude(sensor_data.getDouble(sensor_data.getColumnIndex("double_latitude")));
            sensorData.setLongitude(sensor_data.getDouble(sensor_data.getColumnIndex("double_longitude")));
            sensorData.setBearing(sensor_data.getDouble(sensor_data.getColumnIndex("double_bearing")));
            sensorData.setSpeed(sensor_data.getDouble(sensor_data.getColumnIndex("double_speed")));
            sensorData.setAltitude(sensor_data.getDouble(sensor_data.getColumnIndex("double_altitude")));
            sensorData.setProvider(sensor_data.getString(sensor_data.getColumnIndex("provider")));
            sensorData.setAccuracy(sensor_data.getInt(sensor_data.getColumnIndex("accuracy")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "location-readings");
        }

        clearLocalDatabase();
    }
}
