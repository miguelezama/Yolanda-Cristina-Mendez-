package com.example.noticias

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.noticias.adapters.ArticleAdapter
import com.example.noticias.models.Article
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ArticleAdapter.ArticleViewHolder.OnButtonClickListener{


    val TAG = "MainActivity.kt"
    var articleAdapter: ArticleAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        peticionNoticias()
        val appb = appbar as Toolbar
        appb.title =""
        setSupportActionBar(appb)
        articleAdapter = ArticleAdapter(listaArticulos, this)

        my_recycler.layoutManager = LinearLayoutManager(baseContext)
        my_recycler.adapter = articleAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val mybtnSearch = menu.findItem(R.id.search)
        val searchView = mybtnSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                toast(query)
                if (!query.isEmpty()){
                    val term = "https://newsapi.org/v2/top-headlines?country=us&q=$query&apiKey=7cacd0af564c484c8e5e977b1dbced3d"
                    peticionNoticias(term)

                }
                mybtnSearch.collapseActionView()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        } )

        return true
    }

    override fun ObButtonVerNoticiasClic(article: Article, position: Int) {
       val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("article", position)
        startActivity(intent)
    }

    override fun ObButtonCompartirClic(article: Article, position: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, article.url)
        startActivity(intent)
    }

    fun peticionNoticias(urlnoticia: String = url ){

        val coladepeticiones = Volley.newRequestQueue(this)

        val peticionJSON = JsonObjectRequest(Request.Method.GET, urlnoticia, null,
            Response.Listener<JSONObject> {
                response ->
                try {
                    listaArticulos.removeAll(listaArticulos)
                    listaArticulos.addAll(procesarJSON(response))
                    articleAdapter!!.notifyDataSetChanged()


                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error -> Log.d(TAG, error.toString())}
        )
        coladepeticiones.add(peticionJSON)

    }
    fun procesarJSON(json:JSONObject):ArrayList<Article>{
        val articles: JSONArray = json.getJSONArray("articles")
        val listArticles = ArrayList<Article>()
        for(i in articles){
            val author = i.getString("author")
            val title = i.getString("title")
            val description = i.getString("description")
            val url = i.getString("url")
            val urlImg = i.getString("urlToImage")
            listArticles.add(Article(author, title, description, url, urlImg))
        }
        return listArticles
    }
}
