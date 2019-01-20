package citytracksaware.core.sensors.model;

/**
 * Created by Elton Soares on 10/29/2017.
 */

public class WirelessSensorData extends SensorData{

    private String deviceAddress;
    private String deviceName;
    private String deviceRSSI;

    public WirelessSensorData() {
    }

    public WirelessSensorData(
            String deviceId,
            Double timestamp,
            String bluetoothAddress,
            String bluetoothName,
            String bluetoothRSSI
    ) {
        super(deviceId, timestamp);
        this.deviceAddress = bluetoothAddress;
        this.deviceName = bluetoothName;
        this.deviceRSSI = bluetoothRSSI;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceRSSI() {
        return deviceRSSI;
    }

    public void setDeviceRSSI(String deviceRSSI) {
        this.deviceRSSI = deviceRSSI;
    }
}
