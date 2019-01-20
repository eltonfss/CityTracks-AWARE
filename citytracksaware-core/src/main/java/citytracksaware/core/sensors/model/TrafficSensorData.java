package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class TrafficSensorData extends SensorData {

    private Integer networkType;
    private Double receivedBytes;
    private Double sentBytes;
    private Double receivedPackets;
    private Double sentPackets;

    public TrafficSensorData() {
    }

    public TrafficSensorData(
            String deviceId,
            Double timestamp,
            Integer networkType,
            Double receivedBytes,
            Double sentBytes,
            Double receivedPackets,
            Double sentPackets
    ) {
        super(deviceId, timestamp);
        this.networkType = networkType;
        this.receivedBytes = receivedBytes;
        this.sentBytes = sentBytes;
        this.receivedPackets = receivedPackets;
        this.sentPackets = sentPackets;
    }

    public Integer getNetworkType() {
        return networkType;
    }

    public void setNetworkType(Integer networkType) {
        this.networkType = networkType;
    }

    public Double getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(Double receivedBytes) {
        this.receivedBytes = receivedBytes;
    }

    public Double getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(Double sentBytes) {
        this.sentBytes = sentBytes;
    }

    public Double getReceivedPackets() {
        return receivedPackets;
    }

    public void setReceivedPackets(Double receivedPackets) {
        this.receivedPackets = receivedPackets;
    }

    public Double getSentPackets() {
        return sentPackets;
    }

    public void setSentPackets(Double sentPackets) {
        this.sentPackets = sentPackets;
    }
}
