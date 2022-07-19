package com.burakcanduzcan.currencylisterx.ui.market

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.ItemCryptoCoinBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoin


class CoinListAdapter(private val context: Context, private val currency: String) :
    ListAdapter<CryptoCoin, CoinListAdapter.CoinListViewHolder>(DiffCallBack) {

    inner class CoinListViewHolder(
        private val binding: ItemCryptoCoinBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CryptoCoin, currency: String) {
            //logo
            Glide.with(context)
                .load(coin.image)
                .into(binding.ivLogo)

            //crypto currency name and exchange
            binding.tvName.text = coin.name
            binding.tvExchange.text = "${coin.symbol.uppercase()}/${currency.uppercase()}"

            //current price
            binding.tvPrice.text =
                formatPriceWithCurrencySymbol(coin.currentPrice.toString(), currency)
            //binding.tvPrice.text = coin.currentPrice.toString()

            //24 hours difference percentage
            setPriceChangeViews(coin.priceChangePercentage24H)
        }

        private fun formatPriceWithCurrencySymbol(price: String, currency: String): String {
            when (currency) {
                "usd" -> {
                    return "$$price"
                }
                "try" -> {
                    return "$price₺"
                }
            }
            return "error"
        }

        private fun setPriceChangeViews(priceChangePercentage: Double) {
            var tmpPriceChangePercentage = priceChangePercentage

            //indicator
            if (priceChangePercentage < 0) {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_down_24))
                tmpPriceChangePercentage *= (-1.0)
            } else {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_up_24))
            }

            //percentage
            var extraIntegerDigits = 0
            if (priceChangePercentage >= 10.0 && priceChangePercentage < 100.0) {
                extraIntegerDigits = 1
            } else if (priceChangePercentage >= 100.0) {
                extraIntegerDigits = 2
            }

            var tmpText: String = tmpPriceChangePercentage.toString()
            if (tmpText.length > 3) {
                tmpText = tmpText.dropLast(tmpText.length - (4 + extraIntegerDigits))
            }
            binding.tvChangePercentage.text = "%$tmpText"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemCryptoCoinBinding.inflate(from, parent, false)
        return CoinListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, currency)
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<CryptoCoin>() {
            override fun areItemsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
