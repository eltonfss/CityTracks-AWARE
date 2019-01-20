package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class BarometerSensorData extends SensorData {

    private Double airPressure;
    private Integer accuracy;

    public BarometerSensorData() {
    }

    public BarometerSensorData(String deviceId, Double timestamp, Double airPressure, Integer accuracy) {
        super(deviceId, timestamp);
        this.airPressure = airPressure;
        this.accuracy = accuracy;
    }

    public Double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(Double airPressure) {
        this.airPressure = airPressure;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
