package com.example.placar

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailHistoryActivity() : AppCompatActivity(), OnMapReadyCallback{
    var lat:Double = 0.0
    var long:Double = 0.0
    var locationPermissionGranted: Boolean = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history)
        findViewById<TextView>(R.id.tv_home_detail).text = intent?.getStringExtra("PlayerHome").toString()
        findViewById<TextView>(R.id.tv_away_detail).text = intent?.getStringExtra("PlayerAway")
        findViewById<TextView>(R.id.tv_phome_detail).text = intent?.getStringExtra("PointHome")
        findViewById<TextView>(R.id.tv_paway_detail).text = intent?.getStringExtra("PointAway")
        lat = intent?.getStringExtra("Lat").toString().toDouble()
        long = intent?.getStringExtra("Long").toString().toDouble()
        Log.v("PDM","FROM DETAIL = " + intent?.getStringExtra("Lat").toString() + "," + intent?.getStringExtra("Long").toString())
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        checkPermission()
        if(locationPermissionGranted){
            p0?.isMyLocationEnabled = true
            p0?.uiSettings?.isMyLocationButtonEnabled = true
            p0?.addMarker(MarkerOptions()
                .title("Local da Partida")
                .position(LatLng(lat,long)))
            p0.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,long),18F))
        }
        else{
            p0?.isMyLocationEnabled = false
            p0?.uiSettings?.isMyLocationButtonEnabled = false
        }
    }
    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }
    }

}