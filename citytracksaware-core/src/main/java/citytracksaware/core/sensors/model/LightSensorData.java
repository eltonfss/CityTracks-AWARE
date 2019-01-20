package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class LightSensorData extends SensorData{

    private Double luminance;
    private Integer accuracy;

    public LightSensorData() {
    }

    public LightSensorData(String deviceId, Double timestamp, Double luminance, Integer accuracy) {
        super(deviceId, timestamp);
        this.luminance = luminance;
        this.accuracy = accuracy;
    }

    public Double getLuminance() {
        return luminance;
    }

    public void setLuminance(Double luminance) {
        this.luminance = luminance;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
