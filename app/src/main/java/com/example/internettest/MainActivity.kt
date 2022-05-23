package com.example.internettest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHTTP = findViewById<Button>(R.id.btnHTTP)
        btnHTTP.setOnClickListener{

            val flickr =
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
            Thread{
                val connection = URL(flickr).openConnection() as HttpURLConnection
                val data = connection.inputStream.bufferedReader().readText()
                connection.disconnect()
                Log.i("Flickr cat", data)
            }.start()
        }

        val btnOkHTTP = findViewById<Button>(R.id.btnOkHTTP)
        btnOkHTTP.setOnClickListener {

            val flickr =
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1"
            val client = OkHttpClient()
            val request = Request.Builder().url(flickr).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException){
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response){
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        Log.i("Flickr OkCats", response.body()!!.string())
                    }
                }
            })

        }
    }
}