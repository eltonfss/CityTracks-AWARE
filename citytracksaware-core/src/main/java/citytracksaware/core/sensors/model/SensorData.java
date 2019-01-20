package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public abstract class SensorData {
    private String deviceId;
    private Double timestamp;

    protected SensorData() {

    }

    protected SensorData(String deviceId, Double timestamp) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }
}
