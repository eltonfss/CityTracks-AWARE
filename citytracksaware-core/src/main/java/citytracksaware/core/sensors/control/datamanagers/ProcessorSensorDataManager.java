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

public class ProcessorSensorDataManager extends SensorDataManager {

    public ProcessorSensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        super(context, uploadManager, gson);
        this.contentURI = Processor_Provider.Processor_Data.CONTENT_URI;
    }

    @Override
    public void uploadDataToServer() throws JSONException {

        Cursor sensor_data = getLocalDatabaseCursor();

        Log.i("citytracks-aware", "Processing and sending to upload queue...");
        ProcessorSensorData sensorData;
        while (sensor_data.moveToNext()) {
            sensorData = new ProcessorSensorData();
            sensorData.setTimestamp(sensor_data.getDouble(sensor_data.getColumnIndex("timestamp")));
            sensorData.setDeviceId(deviceId);
            sensorData.setLastUser(sensor_data.getDouble(sensor_data.getColumnIndex("double_last_user")));
            sensorData.setLastSystem(sensor_data.getDouble(sensor_data.getColumnIndex("double_last_system")));
            sensorData.setLastIdle(sensor_data.getDouble(sensor_data.getColumnIndex("double_last_idle")));
            sensorData.setUserLoad(sensor_data.getDouble(sensor_data.getColumnIndex("double_user_load")));
            sensorData.setSystemLoad(sensor_data.getDouble(sensor_data.getColumnIndex("double_system_load")));
            sensorData.setIdleLoad(sensor_data.getDouble(sensor_data.getColumnIndex("double_idle_load")));
            JSONObject json = new JSONObject(gson.toJson(sensorData));
            uploadManager.uploadToServer(json, "processor-readings");
        }

        clearLocalDatabase();
    }
}
