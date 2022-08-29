package com.burakcanduzcan.currencylisterx.ui.updates

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakcanduzcan.currencylisterx.databinding.ItemCryptoNewsBinding
import com.prof.rssparser.Article

class NewsAdapter(
    private val context: Context,
    private val onNewsClick: (article: Article) -> Unit,
) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemCryptoNewsBinding.inflate(from, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class NewsViewHolder(
        private val binding: ItemCryptoNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            Glide.with(context)
                .load(article.image)
                .into(binding.ivImage)
            binding.tvPubDate.text = removeExtraFromPubDate(article.pubDate!!)
            binding.cvNews.setOnClickListener {
                onNewsClick(article)
            }
        }
    }

    private fun removeExtraFromPubDate(pubDate: String): String {
        return pubDate.substringBefore('+').trim()
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }

}