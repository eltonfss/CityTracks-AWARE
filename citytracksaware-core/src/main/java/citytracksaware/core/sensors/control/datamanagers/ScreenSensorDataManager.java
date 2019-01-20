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

public class ScreenSensorDataManager extends SensorDataManager {

    public ScreenSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Screen_Provider.Screen_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        ScreenSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new ScreenSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setScreenStatus(sensor_data.getInt(sensor_data.getColumnIndex("screen_status")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "screen-readings");
        }

        clearLocalDatabase();
    }
}
