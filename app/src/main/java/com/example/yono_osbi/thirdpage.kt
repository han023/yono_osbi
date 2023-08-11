package com.example.yono_osbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.yono_osbi.databinding.ActivityThirdpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class thirdpage : AppCompatActivity() {

    private lateinit var binding : ActivityThirdpageBinding
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields", Toast.LENGTH_SHORT).show()
            } else{

                val intentff = Intent(this,fourthpage::class.java)

                val data = ThirdPagem(acc_no = binding.e1.text.toString() ,
                    mobile = util.getLocalData(this,"u"), pan_card = binding.e2.text.toString())
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val call = apiService.thirdpage(data)
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
}