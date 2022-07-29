package com.azurebear.myapplication2.ui.home_page

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.azurebear.myapplication2.MyApplication2
import com.azurebear.myapplication2.R
import com.azurebear.myapplication2.databinding.FragmentHomeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.concurrent.schedule

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as MyApplication2).repository)
    }

//    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.swiftMain.setColorSchemeColors(resources.getColor(R.color.primaryColor,))
        binding.swiftMain.setOnRefreshListener {
            viewModel.refresh()
            Timer().schedule(2000){
                binding.swiftMain.isRefreshing = false
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.currentWeather.observe(viewLifecycleOwner) {
            binding.tvNowTemp.text = "${it.nowWeather.temp}℃"
            binding.tvNowFeelsLike.setText("体感温度${it.nowWeather.feelsLike}℃")
            binding.tvNowText.setText(it.nowWeather.text)
            binding.tvNowObsTime.setText("观测时间 ${localDateTimeFormat(it.nowWeather.obsTime)}")
            binding.tvUpdateTime.setText("更新时间 ${localDateTimeFormat(it.updateTime)}")
            binding.tvNowWindDir.setText("${it.nowWeather.windDir} (${it.nowWeather.wind360}°)")
            binding.tvNowWindScale.setText("${it.nowWeather.windScale} 级")
            binding.tvNowWindSpeed.setText("${it.nowWeather.windSpeed} km/h")

            binding.tvNowPressure.setText("${it.nowWeather.pressure} hPa")
            binding.tvNowVis.setText("${it.nowWeather.vis} km")
            binding.tvNowPrecip.setText("${it.nowWeather.precip} mm")
            binding.tvNowHumidity.setText("${it.nowWeather.humidity} %")

            val svgLoader = context?.let {
                ImageLoader.Builder(it)
                    .components {
                        add(SvgDecoder.Factory())
                    }
                    .build()
            }

            binding.imageView.load("file:///android_asset/icons/${it.nowWeather.icon}.svg", svgLoader!!)
        }
    }

    private fun localDateTimeFormat(datetimeStr: String): String{
        return LocalDateTime.parse(datetimeStr, DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("HH:mm"))
    }

}