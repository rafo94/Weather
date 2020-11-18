package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.adapter.ForceCastAdapter
import com.example.weather.di.kodein
import com.example.weather.fragments.WeatherFragment
import com.example.weather.interfaces.ExitWithAnimation
import com.example.weather.model.ForecastBase
import com.example.weather.service.LocationUpdatesService
import com.example.weather.util.exitCircularReveal
import com.example.weather.util.findLocationOfCenterOnTheScreen
import com.example.weather.util.getImageByCode
import com.example.weather.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.instance
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    // Tracks the bound state of the service.
    private var mBound = false

    private var myReceiver: MyReceiver? = null
    private var mService: LocationUpdatesService? = null

    private var mServiceConnection: ServiceConnection? = null

    //adapters
    private val adapter: ForceCastAdapter by kodein.instance(tag = "forceCastAdapter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        nextDaysList.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        nextDaysList.adapter = adapter
        myReceiver = MyReceiver()

        mServiceConnection()

        if (!statusCheck())
            if (Build.VERSION.SDK_INT >= 23) {
                setPermissions()
            }

        beautyButton.setOnClickListener {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 105)
        }

        weatherViewModel.responseLiveData.observe(this, { response ->
            if (mService != null)
                mService!!.removeLocationUpdates()
            onResponse(response)
            val foreCastDayList = response.forecast.forecastday
            adapter.setData(foreCastDayList)
        })

        weatherViewModel.errorLiveData.observe(this, {
            mService!!.removeLocationUpdates()
            onFailure(it)
        })

        adapter.callback = { day, view ->
            val positions = view.findLocationOfCenterOnTheScreen()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, WeatherFragment.newInstance(positions, day))
                .addToBackStack(null).commit()
        }
    }

    private fun mServiceConnection() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder: LocationUpdatesService.LocalBinder =
                    service as LocationUpdatesService.LocalBinder
                mService = binder.service
                mBound = true
                mService!!.requestLocationUpdates()
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mService = null
                mBound = false
            }
        }
    }

    private fun setPermissions() {
        val permissionLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val permissions = arrayOfNulls<String>(1)
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION
            ActivityCompat.requestPermissions(this, permissions, 103)
        }
    }

    private fun statusCheck(): Boolean {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, resources.getString(R.string.gps_disable), Toast.LENGTH_LONG)
                .show()
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 105)
            return true
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun onResponse(weather: ForecastBase) {
        darkMode(weather.current.is_day)
        Log.i("TAG", "onResponse: hi")
        mainContainer.getImageByCode(weather.current.condition.code, weather.current.is_day)
        weatherStateLav.getImageByCode(weather.current.condition.code, weather.current.is_day)
        val temp = "${weather.current.temp_c}℃"
        weatherTempTv.text = temp
        weatherTypeTv.text = weather.current.condition.text
        val location = "${weather.location.name}, ${weather.location.country}"
        weatherLocationTv.text = location
        beautyButton.visibility = View.GONE
        weatherLocationLav.visibility = View.GONE
    }

    private fun darkMode(isDay: Int) {
        if (isDay == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            }
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                window.statusBarColor = Color.BLACK
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            }
        }
    }

    private fun onFailure(throwable: Throwable) {
        Toast.makeText(applicationContext, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val location =
                intent.getParcelableExtra<Location>(LocationUpdatesService.EXTRA_LOCATION)
            if (location != null) {
                val gcd = Geocoder(context, Locale.ENGLISH)
                val addresses: List<Address>
                try {
                    addresses = gcd.getFromLocation(
                        location.latitude,
                        location.longitude, 1
                    )
                    if (addresses.isNotEmpty()) {
                        val cityName = addresses[0].locality
                        weatherViewModel.sendRequest(
                            "${location.latitude},${location.longitude}"
                            /*cityName*/,
                            context.getString(R.string.forecast_api_key)
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 105) {
            if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Marshmallow+ Permission APIs
                    setPermissions()
                    mService!!.requestLocationUpdates()
                    beautyButton.visibility = View.GONE
                    weatherLocationLav.visibility = View.GONE
                }
            } else {
                beautyButton.visibility = View.VISIBLE
                weatherLocationLav.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, LocationUpdatesService::class.java), mServiceConnection!!,
            BIND_AUTO_CREATE
        )
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            myReceiver!!,
            IntentFilter(LocationUpdatesService.ACTION_BROADCAST)
        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver!!)
        super.onPause()
    }

    override fun onStop() {
        if (mBound) {
            unbindService(mServiceConnection!!)
            mBound = false
        }
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 103) { // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mService!!.requestLocationUpdates()
            } else {
                Toast.makeText(applicationContext, "Please enable location", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        with(supportFragmentManager.findFragmentById(R.id.fragmentContainer)) {
            if ((this as? ExitWithAnimation)?.isToBeExitedWithAnimation() == true) {
                if (this.postX == null || this.postY == null) {
                    super.onBackPressed()
                } else {
                    this.view?.exitCircularReveal(this.postX!!, this.postY!!) {
                        super.onBackPressed()
                    } ?: super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        }
    }
}