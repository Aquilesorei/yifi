import 'package:flutter_test/flutter_test.dart';
import 'package:yifi/yifi.dart';
import 'package:yifi/yifi_platform_interface.dart';
import 'package:yifi/yifi_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockYifiPlatform
    with MockPlatformInterfaceMixin
    implements YifiPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final YifiPlatform initialPlatform = YifiPlatform.instance;

  test('$MethodChannelYifi is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelYifi>());
  });

  test('getPlatformVersion', () async {
    Yifi yifiPlugin = Yifi();
    MockYifiPlatform fakePlatform = MockYifiPlatform();
    YifiPlatform.instance = fakePlatform;

    expect(await yifiPlugin.getPlatformVersion(), '42');
  });
}
