
import 'yifi_platform_interface.dart';

class Yifi {
static  Future<int?> getPlatformVersion() {
    return YifiPlatform.instance.getPlatformVersion();
  }

 static  Future<void> promptUserToEnableHotspot() async {
    await YifiPlatform.instance.promptUserToEnableHotspot();
  }

 static  Future<void> promptUserToConnectToWifi() async {
    await YifiPlatform.instance.promptUserToConnectToWifi();
  }

 static  Future<int?> getHotspotState() {
    return YifiPlatform.instance.getHotspotState();
  }

 static  Future<String?> getSsid() {
    return YifiPlatform.instance.getSsid();
  }

 static  Future<bool?> isEnabling() {
    return YifiPlatform.instance.isEnabling();
  }

 static  Future<bool?> isEnabled() {
    return YifiPlatform.instance.isEnabled();
  }

 static  Future<bool?> isDisabling() {
    return YifiPlatform.instance.isDisabling();
  }

 static  Future<bool?> isDisabled() {
    return YifiPlatform.instance.isDisabled();
  }

 static  Future<bool?> failed() {
    return YifiPlatform.instance.failed();
  }

 static  Future<bool?> isNotEnabled() {
    return YifiPlatform.instance.isNotEnabled();
  }

 static  Future<bool?> isConnectedToWifi() {
    return YifiPlatform.instance.isConnectedToWifi();
  }

 static  Future<bool?> isNotConnectedToWifi() {
    return YifiPlatform.instance.isNotConnectedToWifi();
  }

 static  Future<void> connectToWifi(String ssid, String password) async {
    await YifiPlatform.instance.connectToWifi(ssid, password);
  }

 static  Future<String?> getIp() {
    return YifiPlatform.instance.getIp();
  }

 static  Future<bool> isPortAvailable() {
    return YifiPlatform.instance.isPortAvailable();
  }

 static  Future<int?> findAvailablePort() {
    return YifiPlatform.instance.findAvailablePort();
  }

static Future<String?> getConnectedDevices() async {
return YifiPlatform.instance.getConnectedDevices();

}
}
