package com.androdocs.mylocation

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)

class GetLAstLocationTest : MainActivity() {


    @Test
    fun getLastLocation(){
        var location = MainActivity()
        lateinit var locationTest: GetLAstLocationTest


        assert(!(location.x).isNaN() && location.x<=180 && location.x>=0)
        assert(!(location.y).isNaN() && location.y<=90 && location.y>=0)
    }



}