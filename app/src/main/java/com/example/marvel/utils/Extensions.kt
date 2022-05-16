package com.example.marvel.utils

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import java.math.BigInteger
import java.security.MessageDigest

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun AppCompatActivity.checkConnection(): Boolean{
    val connectivityManager = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    // In here we return true if network is not null and Network is connected
    if(networkInfo != null && networkInfo.isConnected){
        return true
    }
    return false
}