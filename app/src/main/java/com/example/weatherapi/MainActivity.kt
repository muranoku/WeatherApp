package com.example.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewの取得
        val btnHKD:Button = findViewById(R.id.btnHKD)
        val btnHKT:Button = findViewById(R.id.btnHKT)
    }
}