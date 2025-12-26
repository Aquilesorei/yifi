package com.ztransfer.yifi

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.text.format.Formatter
import androidx.annotation.RequiresApi
import java.net.Inet4Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket

class Hotspot(private val context: Context) {

    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    companion object {
        private const val AP_STATE_DISABLING = 10
        private const val AP_STATE_DISABLED = 11
        private const val AP_STATE_ENABLING = 12
        private const val AP_STATE_ENABLED = 13
        private const val AP_STATE_FAILED = 14
    }



    fun getHotspotState(): Int? {
        try {
            val method = wifiManager.javaClass.getMethod("getWifiApState")
            method.isAccessible = true
            val invoke = method.invoke(wifiManager)
            return invoke?.toString()?.toInt()
        } catch (e: Exception) {
            // handle exception
        }
        return null
    }

   fun promptUserToEnableHotspot() {
        val tetherSettingsComponentName = ComponentName("com.android.settings", "com.android.settings.TetherSettings")
        val hotspotSettingsIntent = Intent(Intent.ACTION_MAIN).apply {
            component = tetherSettingsComponentName
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(hotspotSettingsIntent)
    }


    fun getSsid(): String? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val hotspotConfigMethod = wifiManager.javaClass.getDeclaredMethod("getWifiApConfiguration")
        val hotspotConfig = hotspotConfigMethod.invoke(wifiManager) as WifiConfiguration?
        return hotspotConfig?.SSID
    }


    fun isEnabling() = getHotspotState() == AP_STATE_ENABLING
    fun isEnabled() = getHotspotState() == AP_STATE_ENABLED
    fun isDisabling() = getHotspotState() == AP_STATE_DISABLING
    fun isDisabled() = getHotspotState() == AP_STATE_DISABLED
    fun failed() = getHotspotState() == AP_STATE_FAILED

    fun isNotEnabled() = !isEnabled()


    fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.type == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected
        }
    }

    fun isNotConnectedToWifi(context: Context) = !isConnectedToWifi(context)



    fun connectToWifi(ssid: String, password: String,context : Context) {
        // Get the WifiManager instance
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Create a WifiConfiguration object for the network we want to connect to
        val wifiConfig = WifiConfiguration().apply {
            SSID = "\"$ssid\""
            preSharedKey = "\"$password\""
            // Set the security type of the network (WPA/WPA2 etc)
            allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        }

        // Add the network configuration to the WifiManager
        val networkId = wifiManager.addNetwork(wifiConfig)

        // Enable the WifiManager to connect to the network we just added
        wifiManager.enableNetwork(networkId, true)

        // Reconnect to the Wifi network
        wifiManager.reconnect()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun connectToWifi(context: Context, ssid: String, password: String) {
        val suggestion = WifiNetworkSuggestion.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()

        val suggestionsList = listOf(suggestion)

        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val status = wifiManager.addNetworkSuggestions(suggestionsList)

        if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
            // network suggestions were not successfully added
            return
        }

        // connect to the network
        wifiManager.disconnect()

        val suggestedNetworkSpecifier = WifiNetworkSpecifier.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(suggestedNetworkSpecifier)
            .build()

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.requestNetwork(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // network is available, do what you need to do
                // remember to unregister the callback when done
                connectivityManager.unregisterNetworkCallback(this)
            }

            override fun onUnavailable() {
                // network is unavailable, handle the error
            }
        })
    }


    fun promptUserToConnectToWifi(context: Context) {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Check if Wi-Fi is enabled
        if (!wifiManager.isWifiEnabled) {
            // If Wi-Fi is not enabled, prompt the user to enable it
            val enableWifiIntent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
            context.startActivity(enableWifiIntent)
        } else {
            // If Wi-Fi is enabled, show the list of available networks
            val networkListIntent = Intent(WifiManager.ACTION_PICK_WIFI_NETWORK)
            context.startActivity(networkListIntent)
        }
    }
    private fun getIPv4Addresses(): List<String> {
        val list = mutableListOf<String>()

        for (address in NetworkInterface.getNetworkInterfaces()) {
            for (addr in address.inetAddresses) {
                if (!addr.isLinkLocalAddress && !addr.isLoopbackAddress && addr is Inet4Address) {
                    addr.hostAddress?.let { list.add(it) }
                }
            }
        }


        return list
    }


    fun getIp() : String {

        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress ?: "" }
        }
        return ""
    }

    fun isPortAvailable(port: Int): Boolean {
        return runCatching {
            Socket().use { socket ->
                socket.connect(InetSocketAddress("localhost", port), 1000)
            }
        }.isFailure
    }

    fun findAvailablePort(): Int {
        val socket = ServerSocket(0, 1, InetAddress.getByName("0.0.0.0"))
        val port = socket.localPort
        socket.close()
        return port
    }

}



