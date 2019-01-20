package citytracksaware.core.trip.control;

import android.content.*;
import android.os.*;
import android.provider.Settings.*;
import android.util.*;

import com.google.gson.*;

import org.json.*;

import java.lang.System;
import java.util.*;

import citytracksaware.core.sensors.control.*;
import citytracksaware.core.trip.model.*;
import citytracksaware.core.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class TripManager{

    private Context context;
    private SensorManager sensorManager;
    private UploadManager uploadManager;
    private Gson gson;
    private Trip currentTrip;

    public TripManager(Context context, String serverURL) {
        this.context = context;
        this.uploadManager = UploadManager.getInstance(context, serverURL);
        this.sensorManager = SensorManager.getInstance(context, uploadManager);
        this.gson = new Gson();
    }

    public void setServerURL(String serverURL){
        this.uploadManager.setServerURL(serverURL);
    }

    public void startTrip(String travelMode, String tripPurpose) {
        currentTrip = new Trip();
        currentTrip.setDeviceId(Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));
        currentTrip.setDeviceModel(Build.MODEL);
        currentTrip.setAndroidVersion(Build.VERSION.RELEASE + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName());
        currentTrip.setTimestampStartTime(System.currentTimeMillis());
        currentTrip.setTripPurpose(tripPurpose);
        List<String> travelModes = new ArrayList<>();
        travelModes.add(travelMode);
        currentTrip.setTravelModes(travelModes);
        List<Double> travelModeChangesTimestamps = new ArrayList<>();
        currentTrip.setTimestampsTravelModeChanges(travelModeChangesTimestamps);
        sensorManager.startSensors();
    }

    public void changeTravelModeTo(String newTravelMode){
        if(currentTrip != null) {
            List<Double> travelModeChangesTimestamps = currentTrip.getTimestampsTravelModeChanges();
            travelModeChangesTimestamps.add(Double.longBitsToDouble(System.currentTimeMillis()));
            List<String> travelModes = currentTrip.getTravelModes();
            travelModes.add(newTravelMode);
            currentTrip.setTimestampsTravelModeChanges(travelModeChangesTimestamps);
            currentTrip.setTravelModes(travelModes);
        }
    }

    public void saveCurrentTripState(){
        if(isTripActive()) {
            Log.i("citytracsk-aware","Saving Trip State...");
            JSONDAO<Trip> tripJSONDAO = new JSONDAO<>(context, "currentTrip");
            tripJSONDAO.deleteAll(Trip.class);
            tripJSONDAO.create(currentTrip);
        }
    }

    public void restoreCurrentTripState(){
        JSONDAO<Trip> tripJSONDAO = new JSONDAO<>(context, "currentTrip");
        try {
            Log.i("citytracsk-aware","Restoring Trip State...");
            List<Trip> trips = tripJSONDAO.retrieveAll(Trip.class);
            if(trips.size() == 1){
                currentTrip = trips.get(0);
                tripJSONDAO.deleteAll(Trip.class);
                if(!sensorManager.isAwareActive()) {
                    sensorManager.startSensors();
                }
            }
        }catch (Exception e){
            Log.i("citytracks-aware", "Erro ao restaurar estado da viagem!");
            currentTrip = null;
        }
    }

    public void finishTrip(){

        //Save trip data
        Log.i("citytracks-aware", "Salvando dados da viagem...");
        currentTrip.setTimestampEndTime(System.currentTimeMillis());
        JSONDAO<Trip> tripJSONDAO = new JSONDAO<>(context, "trips");
        tripJSONDAO.create(currentTrip);
        currentTrip = null;

        //Stop sensors
        Log.i("citytracks-aware", "Parando sensores ...");
        sensorManager.stopSensors();

    }

    public void cancelTrip(){
        //Discard trip data
        Log.i("citytracks-aware", "Cancelando viagem...");
        currentTrip = null;
        //Stop sensors
        Log.i("citytracks-aware", "Parando sensores ...");
        sensorManager.stopSensors();

    }

    public boolean isTripActive(){
        return currentTrip != null;
    }

    public boolean uploadLocalDataToServer(){

        if(uploadManager.isNetworkAvailable()){
            Log.i("citytrack-aware", "Enviando dados das viagens...");

            //Upload trip data
            JSONDAO<Trip> tripJSONDAO = new JSONDAO<>(context, "trips");
            List<Trip> trips = null;
            try {
                trips = tripJSONDAO.retrieveAll(Trip.class);
                for (Trip trip : trips) {
                    JSONObject json = new JSONObject(gson.toJson(trip));
                    uploadManager.uploadToServer(json, "trips");
                }
                tripJSONDAO.deleteAll(Trip.class);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            //Upload sensors data
            Log.i("citytracks-aware", "Enviando dados dos sensores...");
            try {
                sensorManager.uploadSensorsData();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;

        }else{
            return false;
        }
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

}
