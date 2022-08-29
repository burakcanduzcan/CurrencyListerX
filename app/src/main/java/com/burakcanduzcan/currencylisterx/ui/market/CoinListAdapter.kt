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
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.Globals
import com.burakcanduzcan.currencylisterx.util.PriceUtil


class CoinListAdapter(
    private val context: Context,
    private val onCoinClick: (coin: CryptoCoinUiModel) -> Unit,
) :
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
            //whole item
            binding.cl.setOnClickListener {
                onCoinClick(coin)
            }

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
            binding.tvPrice.text =
                PriceUtil.updateDecimalPartOfPrice(coin.currentPrice.toString()).toBigDecimal()
                    .toPlainString()

            //price change indicator
            if (coin.priceChangePercentage24H < 0) {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_down_24))
            } else {
                binding.ivChangeIndicator.setImageDrawable(AppCompatResources.getDrawable(context,
                    R.drawable.ic_sharp_arrow_drop_up_24))
            }

            //percentage text and color
            binding.tvChangePercentage.text = context.getString(R.string.percentage_s,
                PriceUtil.getPriceChangePercentageText(coin.priceChangePercentage24H))
            if (coin.priceChangePercentage24H >= 0) {
                binding.tvChangePercentage.setTextColor(context.getColor(R.color.green))
            } else {
                binding.tvChangePercentage.setTextColor(context.getColor(R.color.red))
            }
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
