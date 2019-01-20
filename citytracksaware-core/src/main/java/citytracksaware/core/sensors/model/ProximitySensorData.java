package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class ProximitySensorData extends SensorData {

    private Double proximity;
    private Integer accuracy;

    public ProximitySensorData() {
    }

    public ProximitySensorData(String deviceId, Double timestamp, Double proximity, Integer accuracy) {
        super(deviceId, timestamp);
        this.proximity = proximity;
        this.accuracy = accuracy;
    }

    public Double getProximity() {
        return proximity;
    }

    public void setProximity(Double proximity) {
        this.proximity = proximity;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
