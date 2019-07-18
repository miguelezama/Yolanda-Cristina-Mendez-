package com.example.noticias.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.example.noticias.R
import com.example.noticias.models.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_news_items.view.*

class ArticleAdapter(
    val list:ArrayList<Article>,
    val butonClic: ArticleViewHolder.OnButtonClickListener

):RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_news_items, parent, false)
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        return holder.bind(list[position], butonClic)


    }

    override fun getItemCount(): Int = list.size

    class ArticleViewHolder(articleItemView: View) : RecyclerView.ViewHolder(articleItemView) {
        fun bind(article: Article, OnButtonClicListener: OnButtonClickListener) = with(itemView) {
            TextViewTitle.text = article.title
            TextViewDescription.text = article.description
            Picasso.with(context).load(article.urlToImage).into(ImageViewArticle)
            btnCompartir.setOnClickListener {
                OnButtonClicListener.ObButtonCompartirClic(article, position)
                btnVerNoticia.setOnClickListener {
                    OnButtonClicListener.ObButtonVerNoticiasClic(article, position)

                }
            }
        }

        interface OnButtonClickListener {
            fun ObButtonVerNoticiasClic(article: Article, position: Int)
            fun ObButtonCompartirClic(article: Article, position: Int)
        }


    }
}