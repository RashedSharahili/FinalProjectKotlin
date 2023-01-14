package com.hawy.www.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.hawy.www.R
import com.hawy.www.constants.Constants
import com.hawy.www.databinding.ActivityAuthBinding

var currentLocation : Location? = null
var fusedLocationProviderClient: FusedLocationProviderClient? = null
class AuthActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var navController: NavController

    //TODO :- [1] Create binding Object
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO :- [2] Layout View Binding
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        this.supportActionBar!!.title = ""

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {

        if(checkPermissions()) {

            if(isLocationEnabled()) {

                // Final LatLog Code Here

                fusedLocationProviderClient!!.lastLocation
                    .addOnCompleteListener { task ->

                        val location: Location? = task.result

                        if(location == null) {

                            Toast.makeText(this, "Null Received", Toast.LENGTH_LONG).show()

                        } else {

//                            Toast.makeText(this, "Get Success", Toast.LENGTH_LONG).show()
                            currentLocation = location

//                            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//                            mapFragment?.getMapAsync(this)

//                            Toast.makeText(this, "${currentLocation!!.latitude} || ${currentLocation!!.longitude}", Toast.LENGTH_LONG).show()
                        }
                    }

            } else {

                // Setting Open Here

                Toast.makeText(this, "Turn On Location", Toast.LENGTH_LONG).show()

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {

            // Request Permission Here
            requestPermissions()
        }

    }

    private fun isLocationEnabled() : Boolean {

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermissions() : Boolean {

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            return true
        }

        return false
    }

    private fun requestPermissions() {

        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.PERMISSIONS_REQUEST_ACCESS_LOCATION
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == Constants.PERMISSIONS_REQUEST_ACCESS_LOCATION) {

            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                fetchLocation()

            } else {

                Toast.makeText(this, "Denied", Toast.LENGTH_LONG).show()

                finish()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {

        val myLocation = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//        val myLocation = LatLng(-34.0, 151.0)
//        val markOptions = MarkerOptions().position(myLocation).title("I'm Here")
        p0.isMyLocationEnabled = true
//        p0.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
//        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
        p0.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
//        p0.addMarker(markOptions)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}