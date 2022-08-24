package com.burakcanduzcan.currencylisterx.ui.updates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.currencylisterx.databinding.ItemCryptoNewsBinding
import com.prof.rssparser.Article

class NewsAdapter(
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
            binding.tvPubDate.text = article.pubDate
            binding.cvNews.setOnClickListener {
                onNewsClick(article)
            }
        }
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