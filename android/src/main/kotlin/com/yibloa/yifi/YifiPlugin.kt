package com.yibloa.yifi

import android.content.Context
import com.google.gson.Gson
import com.yibloa.yifi.lib.DeviceFinder
import com.yibloa.yifi.lib.interfaces.OnDeviceFoundListener
import com.yibloa.yifi.lib.models.DeviceItem
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** YifiPlugin */
class YifiPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var context : Context;
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext;
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "yifi")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    val hotspot = Hotspot(context)

    when (call.method) {
      "getPlatformVersion" -> {
        result.success(android.os.Build.VERSION.RELEASE)
      }
      "getHotspotState" -> {
        val hotspotState = hotspot.getHotspotState()
        result.success(hotspotState)
      }
      "promptUserToEnableHotspot" -> {
        hotspot.promptUserToEnableHotspot()
        result.success(null)
      }

      "promptUserToConnectToWifi" -> {
        hotspot.promptUserToConnectToWifi(context)
        result.success(null)
      }
      "getSsid" -> {
        val ssid = hotspot.getSsid()
        result.success(ssid)
      }
      "isEnabling" -> {
        val isEnabling = hotspot.isEnabling()
        result.success(isEnabling)
      }
      "isEnabled" -> {
        val isEnabled = hotspot.isEnabled()
        result.success(isEnabled)
      }
      "isDisabling" -> {
        val isDisabling = hotspot.isDisabling()
        result.success(isDisabling)
      }
      "isDisabled" -> {
        val isDisabled = hotspot.isDisabled()
        result.success(isDisabled)
      }
      "failed" -> {
        val isFailed = hotspot.failed()
        result.success(isFailed)
      }
      "isNotEnabled" -> {
        val isNotEnabled = hotspot.isNotEnabled()
        result.success(isNotEnabled)
      }
      "isConnectedToWifi" -> {
        val isConnected = hotspot.isConnectedToWifi(context)
        result.success(isConnected)
      }
      "isNotConnectedToWifi" -> {
        val isNotConnected = hotspot.isNotConnectedToWifi(context)
        result.success(isNotConnected)
      }
      "connectToWifi" -> {
        val ssid = call.argument<String>("ssid")
        val password = call.argument<String>("password")
        if (ssid != null && password != null) {
          hotspot.connectToWifi(ssid, password, context)
          result.success(null)
        } else {
          result.error("MISSING_ARGUMENT", "SSID or password missing", null)
        }
      }
      "extractapk" ->{
        val packageName = call.argument<String>("packageName")
        if(packageName != null){

          val path = extractApkByPackageName(packageName = packageName, context = context)
          result.success(path)
        }else{
          result.error("MISSING_ARGUMENT", "package name is missing", null)
        }

      }
      "getIp" -> {
        val ip = hotspot.getIp()
        result.success(ip)
      }
      "isPortAvailable" -> {

        val port = call.argument<Int>("port")

        if(port == null) {
          result.error("MISSING_ARGUMENT", "SSID or password missing", null)
        }
        else {
          val isAvailable = hotspot.isPortAvailable(port)
          result.success(isAvailable)
        }
      }

      "findAvailablePort" -> {
        val availablePort = hotspot.findAvailablePort()
        result.success(availablePort)
      }
      
      "getConnectedDevices" -> {

        val devicesFinder = DeviceFinder(context, object : OnDeviceFoundListener {

          override fun onStart(deviceFinder: DeviceFinder?) {
            print("started")
          }

          override fun onFinished(
            deviceFinder: DeviceFinder?,
            deviceItems: MutableList<DeviceItem>?
          ) {
            val json: String = Gson().toJson(deviceItems);
              result.success(json);
          }

          override fun onFailed(deviceFinder: DeviceFinder?, errorCode: Int) {
            result.error(errorCode.toString(),"failed to perform scan","sorry");
          }
        })

        devicesFinder.setTimeout(5000).start()
      }
      else -> {
        result.notImplemented()
      }
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
