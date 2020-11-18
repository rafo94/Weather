package com.example.weather.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lland.LLandActivity
import com.example.weather.R
import com.example.weather.interfaces.ExitWithAnimation
import com.example.weather.model.Forecastday
import com.example.weather.util.getImageByCode
import com.example.weather.util.startCircularReveal
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment(), ExitWithAnimation {

    override var postX: Int? = null

    override var postY: Int? = null

    override fun isToBeExitedWithAnimation() = true

    lateinit var forecastday: Forecastday

    var count = 7

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.startCircularReveal(postX, postY)

        fragWeatherStateLav.getImageByCode(forecastday.day.condition.code, 1)
        fragWeatherTempTv.text = "${forecastday.day.maxtemp_c.toInt()}°"
        fragWeatherTypeTv.text = forecastday.day.condition.text

        fragMaxWindTv.text = "${forecastday.day.maxwind_kph}KM/h"
        fragPrecipTv.text = "${forecastday.day.totalprecip_mm}mm"
        fragHumidityTv.text = "${forecastday.day.avghumidity}%"
        fragSunriseTv.text = forecastday.astro.sunrise
        fragSunsetTv.text = forecastday.astro.sunset
        fragMoonriseTv.text = forecastday.astro.moonrise
        fragMoonsetTv.text = forecastday.astro.moonset

        fragWeatherTempTv.setOnTouchListener { _, _ ->
            count--
            if (count == 0) {
                startActivity(Intent(requireActivity(), LLandActivity::class.java))
                Toast.makeText(requireContext(), "Tap display for start", Toast.LENGTH_LONG).show()
            } else {
                if (count == 6) {
                    Toast.makeText(
                        requireContext(),
                        "Hmm... Is there anything here",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "you succeed through $count",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            return@setOnTouchListener false
        }

        fragWeatherStateLav.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragWeatherTypeTv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragMaxWindStateCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragPrecipCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragHumidityCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragSunriseCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragSunsetCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragMoonriseCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
        fragMoonsetCv.setOnClickListener {
            Toast.makeText(requireContext(), "Easter eggs are not here", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(exit: IntArray? = null, day: Forecastday): WeatherFragment =
            WeatherFragment().apply {
                if (exit != null && exit.size == 2) {
                    postX = exit[0]
                    postY = exit[1]
                    forecastday = day
                }
            }
    }
}