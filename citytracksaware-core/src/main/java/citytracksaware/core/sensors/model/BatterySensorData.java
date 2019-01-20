package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class BatterySensorData extends SensorData {

    private Integer batteryStatus;
    private Integer batteryLevel;
    private Integer batteryScale;
    private Integer batteryVoltage;
    private Integer batteryTemperature;
    private Integer batteryAdaptor;
    private Integer batteryHealth;
    private String batteryTechnology;

    public BatterySensorData() {
    }

    public BatterySensorData(
            String deviceId,
            Double timestamp,
            Integer batteryStatus,
            Integer batteryLevel,
            Integer batteryScale,
            Integer batteryVoltage,
            Integer batteryTemperature,
            Integer batteryAdaptor,
            Integer batteryHealth,
            String batteryTechnology
    ) {
        super(deviceId, timestamp);
        this.batteryStatus = batteryStatus;
        this.batteryLevel = batteryLevel;
        this.batteryScale = batteryScale;
        this.batteryVoltage = batteryVoltage;
        this.batteryTemperature = batteryTemperature;
        this.batteryAdaptor = batteryAdaptor;
        this.batteryHealth = batteryHealth;
        this.batteryTechnology = batteryTechnology;
    }

    public Integer getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(Integer batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getBatteryScale() {
        return batteryScale;
    }

    public void setBatteryScale(Integer batteryScale) {
        this.batteryScale = batteryScale;
    }

    public Integer getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Integer batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Integer getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(Integer batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public Integer getBatteryAdaptor() {
        return batteryAdaptor;
    }

    public void setBatteryAdaptor(Integer batteryAdaptor) {
        this.batteryAdaptor = batteryAdaptor;
    }

    public Integer getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(Integer batteryHealth) {
        this.batteryHealth = batteryHealth;
    }

    public String getBatteryTechnology() {
        return batteryTechnology;
    }

    public void setBatteryTechnology(String batteryTechnology) {
        this.batteryTechnology = batteryTechnology;
    }
}