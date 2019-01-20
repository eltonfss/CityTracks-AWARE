package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class ScreenSensorData extends SensorData {

    private Integer screenStatus;

    public ScreenSensorData() {
    }

    public ScreenSensorData(String deviceId, Double timestamp, Integer screenStatus) {
        super(deviceId, timestamp);
        this.screenStatus = screenStatus;
    }

    public Integer getScreenStatus() {
        return screenStatus;
    }

    public void setScreenStatus(Integer screenStatus) {
        this.screenStatus = screenStatus;
    }
}
