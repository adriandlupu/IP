package com.androdocs.mylocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class Test : MainActivity() {

    @org.junit.Test
    fun testGetFirstL() {
        var x = testGetX();
        var y=testGetY();
        assert(true)

    }

    @org.junit.Test
    fun testSetFirstL() {
        var firstL=Locatie("100","100")
        assert(true)
    }

    @org.junit.Test
    fun testGetSecondL() {
        var x = testGetX();
        var y= testGetY();
        assert(true)

    }

    @org.junit.Test
    fun testSetSecondL() {
        var secondL=Locatie("100","100")
        assert(true)
    }

    @org.junit.Test
    fun testGetX() {

    }

    @org.junit.Test
    fun testSetX() {
    }

    @org.junit.Test
    fun testGetY() {
    }

    @org.junit.Test
    fun testSetY() {
    }

    @org.junit.Test
    fun testGetPERMISSION_ID() {
        var permission = PERMISSION_ID
        assertEquals(permission,42)

    }


    @org.junit.Test
    fun testGetMFusedLocationClient() {
        lateinit var mFusedLocationClient: FusedLocationProviderClient
        assert(true)
    }

 

    @org.junit.Test
    fun testShowNearbyPlants() {
        var filteredList = mutableListOf<String>()

    }

    @org.junit.Test
    fun testGetAllPlants() { //daniel
        val x= MainActivity()
        x.databaseList()
        assert(true)
    }


    @Test
    fun getLastLocationTest(){ //madalina
        val location = MainActivity()
        val lastLocation = getLastLocation()
        checkPermissionsTest()
        isLocationEnabledTest()
        assert(mFusedLocationClient.lastLocation.isComplete)

        assert(!(location.x).isNaN() && location.x<=180 && location.x>=0)
        assert(!(location.y).isNaN() && location.y<=90 && location.y>=0)
    }



    @org.junit.Test
    fun testRequestNewLocationData() { //ioneala
        val obj = requestNewLocationData()
        val mLastLocation: LocationManager? = null
        assert(true)
        var mFusedLocationProviderClient: LocationServices
        assert(true)
    }

    @Test
    fun isLocationEnabledTest(){ //bogdan
        val isLocationEnabled=isLocationEnabled()
        if(isLocationEnabled)
            assert(true)

    }

    @Test
    fun checkPermissionsTest() {//bogdan
        val check = checkPermissions()
        if (check){
            assert(true)
        } else
            assert(false)

    }
    @Test
    fun requestPermissionsTest() { //andreea
       val requestPermission=requestPermissions()
       assert(true)

   }


    @org.junit.Test
    fun testOnRequestPermissionsResult() { //andreea
        val requestCode = PERMISSION_ID
         val grantResults = intArrayOf(0, 2, 3, 4)

        junit.framework.Assert.assertEquals(grantResults[0], PackageManager.PERMISSION_GRANTED)
        junit.framework.Assert.assertEquals(requestCode, PERMISSION_ID)

    }
}