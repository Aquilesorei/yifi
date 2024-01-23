# yifi

 **# Yifi Plugin for Flutter**

## Overview

This Flutter plugin provides a suite of features for managing network connectivity, particularly Wi-Fi hotspots and connected devices, on Android devices.

## Features

* **Managing Wi-Fi Hotspots:**
    - Get hotspot state (enabled, disabled, enabling, disabling, failed)
    - Prompt user to enable hotspot
    - Get hotspot SSID
    - Check if hotspot is enabled
    - Check if connected to Wi-Fi
    - Connect to Wi-Fi
* **Device Discovery:**
    - Get a list of connected devices on the network
* **Network Utilities:**
    - Get device IP address
    - Check port availability
    - Find an available port

## Installation

1. Add the plugin to your `pubspec.yaml` file:

```yaml
dependencies:
  yifi: ^latest_version
```

2. Install the plugin:

```bash
flutter pub get
```

## Usage

1. Import the plugin:

```dart
import 'package:yifi/yifi.dart';
```

2. Use the available methods:

```dart
// Get hotspot state
String hotspotState = await Yifi.getHotspotState();

// Prompt user to enable hotspot
Yifi.promptUserToEnableHotspot();

// Get device IP address
String ipAddress = await Yifi.getIp();

// Check port availability
bool isPortAvailable = await Yifi.isPortAvailable(8080);

// Get connected devices
String connectedDevicesJson = await Yifi.getConnectedDevices();
```

## Additional Notes

* This plugin currently supports Android only.
* For more detailed usage examples and API documentation, refer to the plugin's source code.
* Consider adding a GIF or screenshot to visually demonstrate the plugin's functionality.
* Provide information about available versions and compatibility.
* Encourage users to report issues or contribute to the plugin's development.
