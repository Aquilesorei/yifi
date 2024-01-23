
import 'dart:convert';


class DeviceItem {

static const String UNKNOWN = "UnKnown";

 String ipAddress = UNKNOWN;
 String macAddress = UNKNOWN;
 String deviceName = UNKNOWN;
 String vendorName = UNKNOWN;


 DeviceItem({required this.ipAddress, required this.macAddress, required this.vendorName, required this.deviceName});

  String getIpAddress() {
    return ipAddress;
  }

  void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  String getMacAddress() {
    return macAddress;
  }

  void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  String getDeviceName() {
    return deviceName;
  }

  void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  String getVendorName() {
    return vendorName;
  }

  void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  bool isIpAddressAndDeviceNameSame() {
    return ipAddress == deviceName;
  }

 factory DeviceItem.fromJson(String jsonString) {
   final Map<String, dynamic> json = jsonDecode(jsonString);
   return DeviceItem.fromJsonMap(json);
 }

 factory DeviceItem.fromJsonMap(Map<String, dynamic> json) {

   return DeviceItem(
     ipAddress: json['ipAddress'] ?? UNKNOWN,
     macAddress: json['macAddress'] ?? UNKNOWN,
     deviceName: json['deviceName'] ?? UNKNOWN,
     vendorName: json['vendorName'] ?? UNKNOWN,
   );
 }

}
