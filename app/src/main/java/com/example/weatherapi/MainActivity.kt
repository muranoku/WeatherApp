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
        val clear:Button = findViewById(R.id.clear)

        val apiKey = "fbf776044aae069019d6851d8622de8f"
        val mainUrl = "https://api.openweathermap.org/data/2.5/weather?lang=ja"

        btnHKD.setOnClickListener {
            val weatherUrl = "$mainUrl&q=hakodate&appid=$apiKey"
            weatherTask(weatherUrl)
        }

        btnHKT.setOnClickListener {
            val weatherUrl = "$mainUrl&q=sapporo&appid=$apiKey"
            weatherTask(weatherUrl)
        }

        clear.setOnClickListener{
            cityName.text = "都市名"
            cityWeather.text = "都市の天気"
            highTem.text = "最高気温"
            lowTem.text = "最低気温"


        }

    }


    private fun weatherTask(weatherUrl: String){
        lifecycleScope.launch{
            //HTTP通信
            val result = weatherBackgroundTask(weatherUrl)

            weatherJsonTask(result)
        }
    }

    private suspend fun weatherBackgroundTask(weatherUrl:String):String{
        val response = withContext(Dispatchers.IO){
            var httpResult = ""

            try{

                val urlObj = URL(weatherUrl)
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