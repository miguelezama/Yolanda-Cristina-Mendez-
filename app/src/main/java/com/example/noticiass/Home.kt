package com.example.noticiass

import android.app.DownloadManager
import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Home : AppCompatActivity() {

    val TAG = "HomeActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        peticionNoticias()
    }

    fun peticionNoticias(){

        val coladepeticiones = Volley.newRequestQueue(this)

        val peticionJSON = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> {

                response ->
                try {
                    Log.d(TAG, response.toString())

                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Log.d(TAG, error.toString()) }
            )
        coladepeticiones.add(peticionJSON)
    }
}
