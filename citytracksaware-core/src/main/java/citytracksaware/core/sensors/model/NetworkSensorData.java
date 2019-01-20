package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class NetworkSensorData extends SensorData {

    private Integer networkType;
    private String networkSubType;
    private Integer networkState;

    public NetworkSensorData() {
    }

    public NetworkSensorData(
            String deviceId,
            Double timestamp,
            Integer networkType,
            String networkSubType,
            Integer networkState
    ) {
        super(deviceId, timestamp);
        this.networkType = networkType;
        this.networkSubType = networkSubType;
        this.networkState = networkState;
    }

    public Integer getNetworkType() {
        return networkType;
    }

    public void setNetworkType(Integer networkType) {
        this.networkType = networkType;
    }

    public String getNetworkSubType() {
        return networkSubType;
    }

    public void setNetworkSubType(String networkSubType) {
        this.networkSubType = networkSubType;
    }

    public Integer getNetworkState() {
        return networkState;
    }

    public void setNetworkState(Integer networkState) {
        this.networkState = networkState;
    }
}
