package com.azurebear.myapplication2.ui.home_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.azurebear.myapplication2.MyApplication2
import com.azurebear.myapplication2.R
import com.azurebear.myapplication2.databinding.FragmentFirstBinding
import java.util.*
import kotlin.concurrent.schedule

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as MyApplication2).repository)
    }

//    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
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
            binding.tvNowTemp.setText("${it.nowWeather.temp}℃")
            binding.tvNowFeelsLike.setText("体感温度${it.nowWeather.feelsLike}℃")
            binding.tvNowText.setText(it.nowWeather.text)
            binding.tvNowObsTime.setText(it.nowWeather.obsTime)
        }
    }

}