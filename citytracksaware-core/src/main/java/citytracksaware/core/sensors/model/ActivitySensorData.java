package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 11/12/2017.
 */

public class ActivitySensorData extends SensorData {

    private String activityName;
    private Integer activityType;
    private Integer confidence;
    private String activities;

    public ActivitySensorData() {
    }

    public ActivitySensorData(String deviceId, Double timestamp, String activityName, Integer activityType, Integer confidence, String activities) {
        super(deviceId, timestamp);
        this.activityName = activityName;
        this.activityType = activityType;
        this.confidence = confidence;
        this.activities = activities;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }
}
