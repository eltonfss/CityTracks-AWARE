package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

/**
 * Accelerometer/Gyroscope/LinearAccelerometer/Magnetometer
 */
public class ThreeAxisSensorData extends SensorData{

    private Double xAxis;
    private Double yAxis;
    private Double zAxis;
    private Integer accuracy;

    public ThreeAxisSensorData() {
    }

    public ThreeAxisSensorData(
            String deviceId,
            Double timestamp,
            Double xAxis,
            Double yAxis,
            Double zAxis,
            Integer accuracy
    ) {
        super(deviceId, timestamp);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.accuracy = accuracy;
    }

    public Double getxAxis() {
        return xAxis;
    }

    public void setxAxis(Double xAxis) {
        this.xAxis = xAxis;
    }

    public Double getyAxis() {
        return yAxis;
    }

    public void setyAxis(Double yAxis) {
        this.yAxis = yAxis;
    }

    public Double getzAxis() {
        return zAxis;
    }

    public void setzAxis(Double zAxis) {
        this.zAxis = zAxis;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}