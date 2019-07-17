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
import com.example.noticiass.models.Article
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Home : AppCompatActivity() {

    val TAG = "HomeActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
9
        peticionNoticias()
    }

    fun peticionNoticias(){

        val coladepeticiones = Volley.newRequestQueue(this)

        val peticionJSON = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> {

                response ->
                try {
                    listaArticulos.removeAll(listaArticulos)
                    listaArticulos.addAll(procesarJSON(response))
                    for (i in listaArticulos){
                        Log.d(TAG, i.toString())
                    }

                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Log.d(TAG, error.toString()) }
            )
        coladepeticiones.add(peticionJSON)
    }
    fun procesaJSON(json:JSONObject):ArrayList<Article> {
        val articles: JSONArray = json.getJSONArray(  "articles" )
        val lisArticles = ArrayList<Article>()
        for(i in articles){
            val author = i.getString(  "author")
            val title = i.getString(  "title")
            val description = i.getString( "description")
            val url = i.getString(  "url")
            val urlImg = i.getString(  "urlToImage")
            listArticles.add(Article(author, title, description, url, urlImg))
        }return listArticles

    }s
}
