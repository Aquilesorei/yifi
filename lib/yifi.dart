
import 'yifi_platform_interface.dart';

class Yifi {
  Future<String?> getPlatformVersion() {
    return YifiPlatform.instance.getPlatformVersion();
  }

  Future<void> promptUserToEnableHotspot() async {
    await YifiPlatform.instance.promptUserToEnableHotspot();
  }

  Future<void> promptUserToConnectToWifi() async {
    await YifiPlatform.instance.promptUserToConnectToWifi();
  }

  Future<int?> getHotspotState() {
    return YifiPlatform.instance.getHotspotState();
  }

  Future<String?> getSsid() {
    return YifiPlatform.instance.getSsid();
  }

  Future<bool?> isEnabling() {
    return YifiPlatform.instance.isEnabling();
  }

  Future<bool?> isEnabled() {
    return YifiPlatform.instance.isEnabled();
  }

  Future<bool?> isDisabling() {
    return YifiPlatform.instance.isDisabling();
  }

  Future<bool?> isDisabled() {
    return YifiPlatform.instance.isDisabled();
  }

  Future<bool?> failed() {
    return YifiPlatform.instance.failed();
  }

  Future<bool?> isNotEnabled() {
    return YifiPlatform.instance.isNotEnabled();
  }

  Future<bool?> isConnectedToWifi() {
    return YifiPlatform.instance.isConnectedToWifi();
  }

  Future<bool?> isNotConnectedToWifi() {
    return YifiPlatform.instance.isNotConnectedToWifi();
  }

  Future<void> connectToWifi(String ssid, String password) async {
    await YifiPlatform.instance.connectToWifi(ssid, password);
  }

  Future<String?> getIp() {
    return YifiPlatform.instance.getIp();
  }

  Future<bool> isPortAvailable() {
    return YifiPlatform.instance.isPortAvailable();
  }

  Future<int?> findAvailablePort() {
    return YifiPlatform.instance.findAvailablePort();
  }
}
