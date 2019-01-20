package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class TemperatureSensorData extends SensorData {

    private Double temperature;
    private Integer accuracy;

    public TemperatureSensorData() {
    }

    public TemperatureSensorData(
            String deviceId,
            Double timestamp,
            Double temperature,
            Integer accuracy
    ) {
        super(deviceId, timestamp);
        this.temperature = temperature;
        this.accuracy = accuracy;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
