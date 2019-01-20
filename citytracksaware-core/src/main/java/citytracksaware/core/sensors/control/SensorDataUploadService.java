package citytracksaware.core.sensors.control;

import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.support.v4.content.*;

import com.aware.providers.*;
import com.google.gson.*;

import citytracksaware.core.*;
import citytracksaware.core.sensors.control.datamanagers.*;
import citytracksaware.core.util.*;

public class SensorDataUploadService extends IntentService {

    public static final String BROADCAST_ACTION =
            "citytracksaware.core.sensors.SensorDataUploadService.BROADCAST";

    public static final String EXTENDED_DATA_STATUS =
            "citytracksaware.core.sensors.SensorDataUploadService.STATUS";

    public SensorDataUploadService() {
        super("SensorDataUploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getBaseContext();
        String serverURL = intent.getDataString();
        UploadManager uploadManager = UploadManager.getInstance(context, serverURL);
        Gson gson = new Gson();
        Intent statusIntent = null;



        try {
            createNotification(getString(R.string.upload_dados) + " 0/17");
            new ActivitySensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 1/17");
            new LocationSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 2/17");
            new NetworkSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 3/17");
            new ProcessorSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 4/17");
            new ProximitySensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 5/17");
            new ScreenSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 6/17");
            new TemperatureSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 7/17");
            new TrafficSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 8/17");
            new BarometerSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 9/17");
            new BatterySensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 10/17");
            new LightSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 11/17");
            new GyroscopeSensorDataManager(context, uploadManager, gson).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 12/17");
            new ThreeAxisSensorDataManager(
                    context,
                    uploadManager,
                    gson,
                    Accelerometer_Provider.Accelerometer_Data.CONTENT_URI,
                    "accelerometer-readings"
            ).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 13/17");
            new ThreeAxisSensorDataManager(
                    context,
                    uploadManager,
                    gson,
                    Magnetometer_Provider.Magnetometer_Data.CONTENT_URI,
                    "magnetometer-readings"
            ).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 14/17");
            new ThreeAxisSensorDataManager(
                    context,
                    uploadManager,
                    gson,
                    Gravity_Provider.Gravity_Data.CONTENT_URI,
                    "gravity-readings"
            ).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 15/17");
            new ThreeAxisSensorDataManager(
                    context,
                    uploadManager,
                    gson,
                    Linear_Accelerometer_Provider.Linear_Accelerometer_Data.CONTENT_URI,
                    "linear-accelerometer-readings"
            ).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 16/17");
            new ThreeAxisSensorDataManager(
                    context,
                    uploadManager,
                    gson,
                    Rotation_Provider.Rotation_Data.CONTENT_URI,
                    "rotation-readings"
            ).uploadDataToServer();
            createNotification(getString(R.string.upload_dados) + " 17/17");
//            new BluetoothSensorDataManager(context, uploadManager, gson).uploadDataToServer();
//            new WiFiSensorDataManager(context, uploadManager, gson).uploadDataToServer();
             statusIntent = new Intent(BROADCAST_ACTION).putExtra(EXTENDED_DATA_STATUS, "concluded");
             createNotification(getString(R.string.sucesso_upload_dados));
        }catch (Exception e){
            statusIntent = new Intent(BROADCAST_ACTION).putExtra(EXTENDED_DATA_STATUS, "failed");
            e.printStackTrace();
            createNotification(getString(R.string.erro_upload_dados));
        }finally{
            LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);
        }
    }

    private void createNotification(String text) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(text)
                        .setAutoCancel(true);

        // Sets an ID for the notification
        int mNotificationId = 002;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

}
