package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class ProcessorSensorData extends SensorData {

    private Double lastUser;
    private Double lastSystem;
    private Double lastIdle;
    private Double userLoad;
    private Double systemLoad;
    private Double idleLoad;

    public ProcessorSensorData() {
    }

    public ProcessorSensorData(
            String deviceId,
            Double timestamp,
            Double lastUser,
            Double lastSystem,
            Double lastIdle,
            Double userLoad,
            Double systemLoad,
            Double idleLoad
    ) {
        super(deviceId, timestamp);
        this.lastUser = lastUser;
        this.lastSystem = lastSystem;
        this.lastIdle = lastIdle;
        this.userLoad = userLoad;
        this.systemLoad = systemLoad;
        this.idleLoad = idleLoad;
    }

    public Double getLastUser() {
        return lastUser;
    }

    public void setLastUser(Double lastUser) {
        this.lastUser = lastUser;
    }

    public Double getLastSystem() {
        return lastSystem;
    }

    public void setLastSystem(Double lastSystem) {
        this.lastSystem = lastSystem;
    }

    public Double getLastIdle() {
        return lastIdle;
    }

    public void setLastIdle(Double lastIdle) {
        this.lastIdle = lastIdle;
    }

    public Double getUserLoad() {
        return userLoad;
    }

    public void setUserLoad(Double userLoad) {
        this.userLoad = userLoad;
    }

    public Double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(Double systemLoad) {
        this.systemLoad = systemLoad;
    }

    public Double getIdleLoad() {
        return idleLoad;
    }

    public void setIdleLoad(Double idleLoad) {
        this.idleLoad = idleLoad;
    }
}
