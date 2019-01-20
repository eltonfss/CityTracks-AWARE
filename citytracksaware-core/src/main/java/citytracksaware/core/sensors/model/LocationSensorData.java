package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class LocationSensorData extends SensorData{

    private Double latitude;
    private Double longitude;
    private Double bearing;
    private Double speed;
    private Double altitude;
    private String provider;
    private Integer accuracy;

    public LocationSensorData() {
    }

    public LocationSensorData(
            String deviceId,
            Double timestamp,
            Double latitude,
            Double longitude,
            Double bearing,
            Double speed,
            Double altitude,
            String provider,
            Integer accuracy
    ) {
        super(deviceId, timestamp);
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
        this.speed = speed;
        this.altitude = altitude;
        this.provider = provider;
        this.accuracy = accuracy;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
