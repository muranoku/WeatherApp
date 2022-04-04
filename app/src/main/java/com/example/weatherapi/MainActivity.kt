package com.example.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

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

        val apiKey = "fbf776044aae069019d6851d8622de8f"
        val url = "https://api.openweathermap.org/data/2.5/weather?lang=ja"

        btnHKD.setOnClickListener {
            val url = "$url&q=hakodate&appid=$apiKey"
            weatherTask(url)
        }

        btnHKT.setOnClickListener {
            val url = "$url&q=sapporp&appid=$apiKey"
            weatherTask(url)
        }

        clear.setOnClickListener{
            cityName.text = "都市名"
            cityWeather.text = "都市の天気"
            highTem.text = "最高気温"
            lowTem.text = "最低気温"


        }

    }


    private fun weatherTask(url:String){
        lifecycleScope.launch{
            //HTTP通信
            val result = weatherBackgroundTask(url)

            weatherJsonTask(result)
        }
    }

    private suspend fun weatherBackgroundTask(url:String):String{
        val response = withContext(Dispatchers.IO){
            var httpResult = ""

            try{

                val urlObj = URL(url)
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()//文字列に変換

            } catch (e:IOException){
                e.printStackTrace()
            } catch (e:JSONException){
                e.printStackTrace()
            }

            return@withContext httpResult
        }

         return response
   }
    private fun weatherJsonTask(result: String){
        val cityName:TextView = findViewById(R.id.cityName)
        val cityWeather:TextView = findViewById(R.id.cityWeather)
        val highTem:TextView = findViewById(R.id.highTem)
        val lowTem:TextView = findViewById(R.id.lowTem)

        val jsonObj = JSONObject(result)
        val cityName1 = jsonObj.getString("name")
        cityName.text = cityName1

        val weatherJSONArray = jsonObj.getJSONArray("weather")
        val weatherJSON = weatherJSONArray.getJSONObject(0)
        val weather = weatherJSON.getString("description")
        cityWeather.text = weather

        val main = jsonObj.getJSONObject("main")
        highTem.text = "最高気温:${main.getInt("tempMax")-273}℃"
        lowTem.text = "最低気温:${main.getInt("tempLow")-273}℃"




    }
}