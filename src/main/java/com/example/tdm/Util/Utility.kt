package com.example.tdm.Util

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import java.lang.Exception
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64.DEFAULT
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream


import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}

fun verifyLoginInput(email:String , password:String) : Boolean{
    return email.length > 0 && password.length > 0
}

//facebook
fun newFacebookIntent(pm: PackageManager, url: String): Intent? {
    var uri = Uri.parse(url)
    try {
        val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo.enabled) {
            // http://stackoverflow.com/a/24547437/1048340
            uri = Uri.parse("fb://facewebmodal/f?href=$url")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    return Intent(Intent.ACTION_VIEW, uri)
}

fun isPackageInstalled(c: Context, targetPackage: String?): Boolean {
    val pm: PackageManager = c.getPackageManager()
    try {
        val info = pm.getPackageInfo(targetPackage!!, PackageManager.GET_META_DATA)
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
    return true
}

fun getDate() : String{
    val sdf = SimpleDateFormat("dd/M/yyyy")
    val currentDate = sdf.format(Date())
    val day = currentDate
    return (currentDate)
}

fun imageToBitmap(uri : Uri ,  contentResolver : ContentResolver) : Bitmap{

    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver ,uri)

    return Bitmap.createBitmap(bitmap)
}

@RequiresApi(Build.VERSION_CODES.O)
fun imageToBase64(bitmap : Bitmap) :String{
    val output : ByteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 75,output)
    val imageInByte = output.toByteArray()

    val imageInString = Base64.getEncoder().encodeToString(imageInByte)
    return  imageInString
}