package com.burakcanduzcan.currencylisterx.ui.detail

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.core.BaseFragment
import com.burakcanduzcan.currencylisterx.databinding.FragmentDetailBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.PriceUtil
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var coin: CryptoCoinUiModel

    override fun onResume() {
        super.onResume()
        Timber.i("DetailFragment onResume")
    }

    override fun initializeViews() {
        Timber.i("DetailFragment onCreateView")
        coin = args.incomingCoin
        Timber.i("DetailFragment given coin: ${coin.name}")

        // Views
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvCoinName.text = coin.name
        binding.tvCoinPrice.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(coin.currentPrice))
        setLineChart()
        //favorite button
        var isClicked = false
        binding.ivFavorite.setOnClickListener {
            isClicked = if (isClicked) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        }
        //cardView: details
        binding.tv24hHighest.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(coin.high24H))
        binding.tv24hLowest.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(coin.low24H))
    }

    private fun setLineChart() {
        // DATA
        val entries = ArrayList<Entry>()
        for (i in 0 until coin.sparklineIn7D!!.size) {
            entries.add(Entry(i.toFloat(), coin.sparklineIn7D!![i].toFloat()))
        }
        // LINE
        val dataSet = LineDataSet(entries, null)
        dataSet.apply {
            mode = LineDataSet.Mode.LINEAR
            setDrawCircles(false)
            // COLORING; lighter on main color, darker on fillColor
            setDrawFilled(true)
            if (coin.priceChangePercentage24H > 0) {
                //greens
                color = requireContext().getColor(R.color.green)
                fillColor = requireContext().getColor(R.color.darker_green)
                fillAlpha = 85 //default
            } else {
                //red
                color = requireContext().getColor(R.color.light_red)
                fillColor = requireContext().getColor(R.color.red)
                fillAlpha = 85 //default
            }
        }
        // CHART
        val lineData = LineData(dataSet)
        binding.lineChart.apply {
            this.data = lineData
            //legend
            description = null //text right bottom corner
            legend.isEnabled = false //color indicator at bottom left corner
            xAxis.isEnabled = false //xAxis, located at top
            axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.white)
            axisRight.isEnabled = false //yAxis on right side
            //screen
            setTouchEnabled(false)
            isDragEnabled = false
            setScaleEnabled(false)
            setPinchZoom(false)
            fitScreen()
            animateX(500)
            invalidate()
        }
    }
}