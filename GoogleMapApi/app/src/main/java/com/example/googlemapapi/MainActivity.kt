package com.example.googlemapapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {


    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            val location1 = LatLng(47.181,27.500)
            googleMap.addMarker(MarkerOptions().position(location1).title("Trandafir"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,14f))

            val location2 = LatLng(47.182,27.501)
            googleMap.addMarker(MarkerOptions().position(location2).title("Lalea"))

            val location3 = LatLng(47.183,27.503)
            googleMap.addMarker(MarkerOptions().position(location3).title("Petunie"))

        })
    }
}
