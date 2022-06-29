package com.example.kartoved

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kartoved.databinding.ActivityMapsBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MapsActivity : AppCompatActivity() {
    private var _binding : ActivityMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("4e4e2541-093f-4e3a-bb64-309598927f17")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_maps)

        mapView = binding.mapview
        mapView.map.move(
            CameraPosition(Point(56.851900, 60.612200), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }
}