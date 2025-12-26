package com.ztransfer.yifi


import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun extractApkByPackageName(context: Context, packageName: String): String? {
    try {
        val packageManager: PackageManager = context.packageManager
        val applicationInfo: ApplicationInfo = packageManager.getApplicationInfo(packageName, 0)

        val sourceDir: String = applicationInfo.sourceDir
        val apkFile = File(sourceDir)

        if (!apkFile.exists()) {
            return null
        }

        val outputDir = context.cacheDir // You can change this to your desired directory
        val outputFile = File(outputDir, "${getApplicationName(packageName,context)}.apk")

        // Copy the APK file to the output directory
        val inputStream = apkFile.inputStream()
        val outputStream = FileOutputStream(outputFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        inputStream.close()
        outputStream.close()

        return outputFile.absolutePath
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}




 fun getApplicationName(packageName: String,context : Context): String? {
    val pm = context.packageManager
    val applicationInfo = try {
        pm.getApplicationInfo(packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        return null
    }
    return pm.getApplicationLabel(applicationInfo).toString()
}