package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class WiFiSensorData extends WirelessSensorData {

    private String securityProtocols;
    private Integer bandFrequency;

    public WiFiSensorData() {
    }

    public WiFiSensorData(
            String deviceId,
            Double timestamp,
            String bluetoothAddress,
            String bluetoothName,
            String bluetoothRSSI,
            String securityProtocols,
            Integer bandFrequency
    ) {
        super(deviceId, timestamp, bluetoothAddress, bluetoothName, bluetoothRSSI);
        this.securityProtocols = securityProtocols;
        this.bandFrequency = bandFrequency;
    }

    public String getSecurityProtocols() {
        return securityProtocols;
    }

    public void setSecurityProtocols(String securityProtocols) {
        this.securityProtocols = securityProtocols;
    }

    public Integer getBandFrequency() {
        return bandFrequency;
    }

    public void setBandFrequency(Integer bandFrequency) {
        this.bandFrequency = bandFrequency;
    }
}
