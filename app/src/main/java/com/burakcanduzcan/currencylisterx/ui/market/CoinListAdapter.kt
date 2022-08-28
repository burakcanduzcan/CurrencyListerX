package com.burakcanduzcan.currencylisterx.ui.market

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.ItemCryptoCoinBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.Globals
import com.burakcanduzcan.currencylisterx.util.PriceUtil.updateDecimalPartOfPrice


class CoinListAdapter(private val context: Context) :
    ListAdapter<CryptoCoinUiModel, CoinListAdapter.CoinListViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemCryptoCoinBinding.inflate(from, parent, false)
        return CoinListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class CoinListViewHolder(
        private val binding: ItemCryptoCoinBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CryptoCoinUiModel) {
            //logo
            Glide.with(context)
                .load(coin.image)
                .into(binding.ivLogo)

            //crypto currency name
            binding.tvName.text = coin.name

            //currency exchange
            binding.tvExchange.text = context.getString(R.string.exchange_s_s,
                coin.symbol.uppercase(),
                Globals.CURRENCY)

            //current price
            binding.tvPrice.text = updateDecimalPartOfPrice(coin.currentPrice.toString()).toBigDecimal().toPlainString()

            //24 hours price change indicator and percentage
            setPriceChangeViews(coin.priceChangePercentage24H)
        }

        private fun setPriceChangeViews(priceChangePercentage: Double) {
            var tmpPriceChangePercentage = priceChangePercentage

            //indicator and color text according to the 24h percentage
            if (priceChangePercentage < 0) {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_down_24))
                tmpPriceChangePercentage *= (-1.0)
                binding.tvChangePercentage.setTextColor(ContextCompat.getColor(context,
                    R.color.red))
            } else {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_up_24))
                binding.tvChangePercentage.setTextColor(ContextCompat.getColor(context,
                    R.color.darker_green))
            }

            //percentage
            var extraIntegerDigits = 0
            if (priceChangePercentage >= 10.0 && priceChangePercentage < 100.0) {
                extraIntegerDigits = 1
            } else if (priceChangePercentage >= 100.0) {
                extraIntegerDigits = 2
            }

            //show only first 3 digits of percentage, excluding the comma
            var tmpText: String = tmpPriceChangePercentage.toString()
            if (tmpText.length > 3) {
                tmpText = tmpText.dropLast(tmpText.length - (4 + extraIntegerDigits))
            }
            binding.tvChangePercentage.text = context.getString(R.string.percentage_s, tmpText)
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<CryptoCoinUiModel>() {
            override fun areItemsTheSame(
                oldItem: CryptoCoinUiModel,
                newItem: CryptoCoinUiModel,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CryptoCoinUiModel,
                newItem: CryptoCoinUiModel,
            ): Boolean {
                return oldItem.currentPrice == newItem.currentPrice
            }
        }
    }
}
