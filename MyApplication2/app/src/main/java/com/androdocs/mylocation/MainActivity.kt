package com.androdocs.mylocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
//import android.icu.util.TimeUnit
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    var firstL= Locatie("110.1","110.1")
    var secondL= Locatie("110.1","110.1")
    var x=1.1
    var y=1.1

    private lateinit var database: FirebaseDatabase
    private var plantList = mutableListOf<Plant>()
    companion object {
        const val TAG = "MainActivity"
    }

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        database = FirebaseDatabase.getInstance()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        getAllPlants()
    }





    fun showNearbyPlants() {
        var filteredList = mutableListOf<String>()
        plantList.forEach { plant ->
            firstL.lat="110.1"
            firstL.long="110.1"
            secondL.lat="110.1"
            secondL.long="110.1"
            plant.locatii.forEach{ locatie ->
                if(((locatie.lat.toDouble()-y)*(locatie.lat.toDouble()-y)+(locatie.long.toDouble()-x)*(locatie.long.toDouble()-x))<((firstL.lat.toDouble()-y)*(firstL.lat.toDouble()-y+(firstL.long.toDouble()-x)*(firstL.long.toDouble()-x))))
                {
                    firstL.lat=locatie.lat
                    firstL.long=locatie.long
                }
                else if(((locatie.lat.toDouble()-y)*(locatie.lat.toDouble()-y)+(locatie.long.toDouble()-x)*(locatie.long.toDouble()-x))<((secondL.lat.toDouble()-y)*(secondL.lat.toDouble()-y)+(secondL.long.toDouble()-x)*(secondL.long.toDouble()-x)))
                {
                    secondL.lat=locatie.lat
                    secondL.long=locatie.long
                }

            }

            if(abs((secondL.lat.toDouble()-firstL.lat.toDouble())*x-(secondL.long.toDouble()-firstL.long.toDouble())*y+secondL.long.toDouble()*firstL.lat.toDouble()-secondL.lat.toDouble()*firstL.long.toDouble())/sqrt((secondL.lat.toDouble()-firstL.lat.toDouble())*(secondL.lat.toDouble()-firstL.lat.toDouble())+(secondL.long.toDouble()-firstL.long.toDouble())*(secondL.long.toDouble()-firstL.long.toDouble())) <plant.range.toDouble()*0.0000089)
                filteredList.add(plant.name)
            else if(((plant.locatii[0].lat.toDouble()-y)*(plant.locatii[0].lat.toDouble()-y+(plant.locatii[0].long.toDouble()-x)*(plant.locatii[0].long.toDouble()-x)))<((firstL.lat.toDouble()-y)*(firstL.lat.toDouble()-y+(firstL.long.toDouble()-x)*(firstL.long.toDouble()-x))))
                filteredList.add(plant.name)
        }
        filteredList=filteredList.toSet().toMutableList()
        plantsListView.adapter =
            ArrayAdapter<String>(this@MainActivity, R.layout.plant_layout, filteredList)
    }

    fun savePlant(name: String, lat: Double, long: Double) {
        val myRef = database.getReference(name)

        myRef.child("lat").setValue(lat)
        myRef.child("long").setValue(long)
    }

    fun getPlantDetails(name: String) {
        database.getReference(name).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value.toString()
                val gson = Gson()
                val plant = gson.fromJson(value, Plant::class.java)
                plant.name = name
                Log.d(TAG, plant.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun getAllPlants() {
        database.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val gson = Gson()
                val plants = mutableListOf<Plant>()

                dataSnapshot.children.forEach { plant ->
                    val newPlant = Plant(plant.key.toString(), plant.child("range").toString(),mutableListOf())
                    dataSnapshot.child(plant.key.toString()).child("locatii").children.forEach{ locatie ->
                        newPlant.locatii.add(gson.fromJson(locatie.value.toString(), Locatie::class.java))

                    }
                    newPlant.name = plant.key.toString()
                    newPlant.range=plant.child("range").value.toString()
                    plants.add(newPlant)
                }

//                47.175454, 27.540770
  //              47.175495, 27.542046
    //            47.174933, 27.542078
      //          47.175137, 27.540688


                var locatie0 = Locatie("27.541000", "47.175000")
                var locatie1 = Locatie("27.540770", "47.175454")
                var locatie2 = Locatie("27.542046", "47.175495")
                var locatie3 = Locatie("27.542078", "47.174933")
                var locatie4 = Locatie("27.540688", "47.175137")

                val newPlant = Plant("Daniel", "10", mutableListOf())
                newPlant.locatii.add(locatie0)
                newPlant.locatii.add(locatie1)
                newPlant.locatii.add(locatie2)
                newPlant.locatii.add(locatie3)
                newPlant.locatii.add(locatie4)
                plants.add(newPlant)
                Log.d(TAG,plants.toString())
                plantList = plants
                showNearbyPlants()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        y=location.latitude;
                        x=location.longitude;
                        Log.d("here",x.toString())
                        getAllPlants()
                        requestNewLocationData()
                        //findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        //findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()

                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 100000

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            x=mLastLocation.longitude
            y=mLastLocation.latitude
            Log.d("here1",x.toString())
            Log.d("here2", y.toString())
            getAllPlants()
            //findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            //findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER

        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}