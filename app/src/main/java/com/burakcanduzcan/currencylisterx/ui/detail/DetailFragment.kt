package com.burakcanduzcan.currencylisterx.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.core.BaseFragment
import com.burakcanduzcan.currencylisterx.databinding.FragmentDetailBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.Globals
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
    private lateinit var givenCoin: CryptoCoinUiModel
    private lateinit var sharedPref: SharedPreferences

    override fun onResume() {
        super.onResume()
        Timber.i("DetailFragment onResume")
    }

    @SuppressLint("ApplySharedPref")
    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("DetailFragment onDestroyView")
        sharedPref = requireActivity().getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        Timber.i("DetailFragment string to put in sharedPref: ${Globals.generateFavoriteListString()}")
        sharedPref.edit().putString("favorite_list_string", Globals.generateFavoriteListString())
            .commit()
    }

    override fun initializeViews() {
        Timber.i("DetailFragment onCreateView")
        givenCoin = args.incomingCoin
        Timber.i("DetailFragment given coin: ${givenCoin.name}")
        // Views
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvCoinName.text = givenCoin.name
        binding.tvCoinPrice.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(givenCoin.currentPrice))
        setLineChart()
        //favorite button
        var isClicked: Boolean
        //check whether given coin is in favorite list
        if (Globals.FAVORITE_LIST.contains(givenCoin.symbol)) {
            isClicked = true
            Timber.i("DetailFragment given coin is IN favorite list")
            //set imageView's state to colored
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            binding.ivFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
        } else {
            isClicked = false
            Timber.i("DetailFragment given coin is NOT favorite list")
        }
        binding.ivFavorite.setOnClickListener {
            if (isClicked) {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.ivFavorite.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.white))
                //remove from favorite list
                Globals.FAVORITE_LIST.remove(givenCoin.symbol)
                Timber.i("DetailFragment coin: ${givenCoin.symbol} removed from favorite list")
                isClicked = false
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.ivFavorite.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.red))
                //add to favorite list
                Globals.FAVORITE_LIST.add(givenCoin.symbol)
                Timber.i("DetailFragment coin: ${givenCoin.symbol} added to favorite list")
                isClicked = true
            }
        }
        //cardView: details
        binding.tv24hHighest.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(givenCoin.high24H))
        binding.tv24hLowest.text =
            PriceUtil.updateDecimalPartOfPrice(PriceUtil.updatePriceTextWithCurrency(givenCoin.low24H))
    }

    private fun setLineChart() {
        // DATA
        val entries = ArrayList<Entry>()
        for (i in 0 until givenCoin.sparklineIn7D!!.size) {
            entries.add(Entry(i.toFloat(), givenCoin.sparklineIn7D!![i].toFloat()))
        }
        // LINE
        val dataSet = LineDataSet(entries, null)
        dataSet.apply {
            mode = LineDataSet.Mode.LINEAR
            setDrawCircles(false)
            // COLORING; lighter on main color, darker on fillColor
            setDrawFilled(true)
            if (givenCoin.priceChangePercentage24H > 0) {
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