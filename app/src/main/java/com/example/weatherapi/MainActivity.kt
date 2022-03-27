package com.example.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewの取得
        val btnHKD:Button = findViewById(R.id.btnHKD)
        val btnHKT:Button = findViewById(R.id.btnHKT)
        val cityName:TextView = findViewById(R.id.cityName)
        val cityWeather:TextView = findViewById(R.id.cityWeather)
        val highTem:TextView = findViewById(R.id.highTem)
        val lowTem:TextView = findViewById(R.id.lowTem)
        val clear:TextView = findViewById(R.id.clear)



    }
}