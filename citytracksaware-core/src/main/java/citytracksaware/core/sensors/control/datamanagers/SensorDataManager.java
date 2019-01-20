package citytracksaware.core.sensors.control.datamanagers;

import android.content.*;
import android.database.*;
import android.net.*;
import android.provider.*;
import android.util.*;

import com.google.gson.*;

import org.json.*;

import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public abstract class SensorDataManager {

    protected Context context;
    protected UploadManager uploadManager;
    protected Gson gson;
    protected Uri contentURI;
    protected String deviceId;

    public SensorDataManager(Context context, UploadManager uploadManager, Gson gson) {
        this.context = context;
        this.uploadManager = uploadManager;
        this.gson = gson;
        this.deviceId =  Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }

    protected Cursor getLocalDatabaseCursor() {
        Log.i("citytracks-aware", "Reading " + contentURI.toString() + " Data from SQLITE...");
        return context.getContentResolver().query(
                this.contentURI,
                null,
                null,
                null,
                null
        );
    }

    protected void clearLocalDatabase() {
        Log.i("citytracks-aware", "Cleaning SQLITE database...");
        context.getContentResolver().delete(
                contentURI,
                null,
                null
        );
    }

    public abstract void uploadDataToServer() throws JSONException;
}
