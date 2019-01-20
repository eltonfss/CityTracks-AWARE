package citytracksaware.core.trip.model;

import java.io.*;
import java.util.*;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class Trip implements Serializable {

    private Long timestampStartTime;
    private Long timestampEndTime;
    private List<Double> timestampsTravelModeChanges;
    private List<String> travelModes;
    private String tripPurpose;
    private String deviceId;
    private String deviceModel;
    private String androidVersion;

    public Trip() {
    }

    public Trip(Long timestampStartTime, Long timestampEndTime, List<Double> timestampsTravelModeChanges, List<String> travelModes, String tripPurpose, String deviceId, String deviceModel, String androidVersion) {
        this.timestampStartTime = timestampStartTime;
        this.timestampEndTime = timestampEndTime;
        this.timestampsTravelModeChanges = timestampsTravelModeChanges;
        this.travelModes = travelModes;
        this.tripPurpose = tripPurpose;
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.androidVersion = androidVersion;
    }

    public Long getTimestampStartTime() {
        return timestampStartTime;
    }

    public void setTimestampStartTime(Long timestampStartTime) {
        this.timestampStartTime = timestampStartTime;
    }

    public Long getTimestampEndTime() {
        return timestampEndTime;
    }

    public void setTimestampEndTime(Long timestampEndTime) {
        this.timestampEndTime = timestampEndTime;
    }

    public List<Double> getTimestampsTravelModeChanges() {
        return timestampsTravelModeChanges;
    }

    public void setTimestampsTravelModeChanges(List<Double> timestampsTravelModeChanges) {
        this.timestampsTravelModeChanges = timestampsTravelModeChanges;
    }

    public List<String> getTravelModes() {
        return travelModes;
    }

    public void setTravelModes(List<String> travelModes) {
        this.travelModes = travelModes;
    }

    public String getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(String tripPurpose) {
        this.tripPurpose = tripPurpose;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}