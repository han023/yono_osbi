package com.example.yono_osbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.yono_osbi.databinding.ActivitySecondpageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class secondpage : AppCompatActivity() {

    private lateinit var binding : ActivitySecondpageBinding
    private val dateOfBirthTextWatcher = DateOfBirthTextWatcher()
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.e2.addTextChangedListener(dateOfBirthTextWatcher)

        binding.submit.setOnClickListener {
            if (binding.e1.text.toString().isEmpty() || binding.e2.text.toString().isEmpty()){
                Toast.makeText(this,"Fill all fields", Toast.LENGTH_SHORT).show()
            } else{

                val intentff = Intent(this,thirdpage::class.java)

                val data = SecondPagem(customer_name = binding.e1.text.toString() ,
                    mobile = util.getLocalData(this,"u"), dob = binding.e2.text.toString())
                val apiService = ApiClient.getClient().create(ApiService::class.java)
                val call = apiService.secondpage(data)
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

class DateOfBirthTextWatcher : TextWatcher {
    private var isFormatting = false
    private val dateSeparator = '/'
    private val datePattern = Regex("[0-9]{2}/[0-9]{2}/[0-9]{4}")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) {
            return
        }

        isFormatting = true
        formatDate(s)
        isFormatting = false
    }

    private fun formatDate(text: Editable?) {
        text?.let {
            val dateLength = text.length
            if (dateLength == 3 || dateLength == 6) {
                if (text[dateLength - 1] != dateSeparator) {
                    text.insert(dateLength - 1, dateSeparator.toString())
                }
            }

            if (dateLength >= 10) {
                val date = text.toString()
                if (!datePattern.matches(date)) {
                    // Invalid date format, clear the text
                    text.clear()
                }
            }
        }
    }
}

