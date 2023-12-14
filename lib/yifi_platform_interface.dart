import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'yifi_method_channel.dart';

abstract class YifiPlatform extends PlatformInterface {
  /// Constructs a YifiPlatform.
  YifiPlatform() : super(token: _token);

  static final Object _token = Object();

  static YifiPlatform _instance = MethodChannelYifi();

  /// The default instance of [YifiPlatform] to use.
  ///
  /// Defaults to [MethodChannelYifi].
  static YifiPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [YifiPlatform] when
  /// they register themselves.
  static set instance(YifiPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<int?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> promptUserToEnableHotspot() {
    throw UnimplementedError('promptUserToEnableHotspot() has not been implemented.');
  }


  Future<void> promptUserToConnectToWifi() {
    throw UnimplementedError('promptUserToConnectToWifi() has not been implemented.');
  }


  Future<int?> getHotspotState() {
    throw UnimplementedError('getHotspotState() has not been implemented.');
  }

  Future<String?> getSsid() {
    throw UnimplementedError('getSsid() has not been implemented.');
  }

  Future<bool?> isEnabling() {
    throw UnimplementedError('isEnabling() has not been implemented.');
  }

  Future<bool?> isEnabled() {
    throw UnimplementedError('isEnabled() has not been implemented.');
  }

  Future<bool?> isDisabling() {
    throw UnimplementedError('isDisabling() has not been implemented.');
  }

  Future<bool?> isDisabled() {
    throw UnimplementedError('isDisabled() has not been implemented.');
  }

  Future<bool?> failed() {
    throw UnimplementedError('failed() has not been implemented.');
  }

  Future<bool?> isNotEnabled() {
    throw UnimplementedError('isNotEnabled() has not been implemented.');
  }

  Future<bool?> isConnectedToWifi() {
    throw UnimplementedError('isConnectedToWifi() has not been implemented.');
  }

  Future<bool?> isNotConnectedToWifi() {
    throw UnimplementedError('isNotConnectedToWifi() has not been implemented.');
  }

  Future<void> connectToWifi(String ssid, String password) {
    throw UnimplementedError('connectToWifi() has not been implemented.');
  }

  Future<String?> getIp(){
    throw UnimplementedError('getIp() has not been implemented');
  }

  Future<bool> isPortAvailable(){
    throw UnimplementedError('isPortAvailable() has not been implemented');

  }

  Future<int?> findAvailablePort() {
    throw UnimplementedError('findAvailablePort() has not been implemented');
  }
}
