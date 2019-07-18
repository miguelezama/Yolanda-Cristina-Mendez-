package com.example.noticias

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val position = intent.getIntExtra("article", 0)
        webview.webViewClient = WebViewClient()
        webview.loadUrl(listaArticulos[position].url)
    }
}
