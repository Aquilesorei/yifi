import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'yifi_platform_interface.dart';

/// An implementation of [YifiPlatform] that uses method channels.
class MethodChannelYifi extends YifiPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('yifi');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> promptUserToEnableHotspot() async {
    await methodChannel.invokeMethod<void>('promptUserToEnableHotspot');
  }

  @override
  Future<void> promptUserToConnectToWifi() async {
    await methodChannel.invokeMethod<void>('promptUserToConnectToWifi');
  }

  @override
  Future<int?> getHotspotState() async {
    final state = await methodChannel.invokeMethod<int>('getHotspotState');
    return state;
  }

  @override
  Future<String?> getSsid() async {
    final ssid = await methodChannel.invokeMethod<String>('getSsid');
    return ssid;
  }

  @override
  Future<bool?> isEnabling() async {
    final isEnabled = await methodChannel.invokeMethod<bool>('isEnabling');
    return isEnabled;
  }

  @override
  Future<bool?> isEnabled() async {
    final isEnabled = await methodChannel.invokeMethod<bool>('isEnabled');
    return isEnabled;
  }

  @override
  Future<bool?> isDisabling() async {
    final isDisabling = await methodChannel.invokeMethod<bool>('isDisabling');
    return isDisabling;
  }

  @override
  Future<bool?> isDisabled() async {
    final isDisabled = await methodChannel.invokeMethod<bool>('isDisabled');
    return isDisabled;
  }

  @override
  Future<bool?> failed() async {
    final hasFailed = await methodChannel.invokeMethod<bool>('failed');
    return hasFailed;
  }

  @override
  Future<bool?> isNotEnabled() async {
    final isNotEnabled = await methodChannel.invokeMethod<bool>('isNotEnabled');
    return isNotEnabled;
  }

  @override
  Future<bool?> isConnectedToWifi() async {
    final isConnected = await methodChannel.invokeMethod<bool>('isConnectedToWifi');
    return isConnected;
  }

  @override
  Future<bool?> isNotConnectedToWifi() async {
    final isNotConnected = await methodChannel.invokeMethod<bool>('isNotConnectedToWifi');
    return isNotConnected;
  }

  @override
  Future<void> connectToWifi(String ssid, String password) async {
    final args = <String, dynamic>{'ssid': ssid, 'password': password};
    await methodChannel.invokeMethod<void>('connectToWifi', args);
  }

  @override
  Future<String?> getIp() async {
    final ip = await methodChannel.invokeMethod<String>('getIp');
    return ip;
  }

  @override
  Future<bool> isPortAvailable() async {
    final isAvailable = await methodChannel.invokeMethod<bool>('isPortAvailable');
    return isAvailable ?? false;
  }

  @override
  Future<int?> findAvailablePort() async {
    final port = await methodChannel.invokeMethod<int>('findAvailablePort');
    return port;
  }
}
