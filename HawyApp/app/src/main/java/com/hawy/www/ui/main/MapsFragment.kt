package com.hawy.www.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hawy.www.R
import com.hawy.www.constants.Constants
import com.hawy.www.data.model.session.Session
import com.hawy.www.ui.auth.currentLocation
import com.hawy.www.ui.main.session.SessionViewModel
import com.hawy.www.ui.main.session.SessionViewModelFactory


class MapsFragment : Fragment() {

//    var currentLocation : Location? = null
//    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private var sessions = listOf<Session>()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val areas = arrayOf(
            LatLng(-35.016, 143.321),
            LatLng(-34.747, 145.592),
            LatLng(-34.364, 147.891),
            LatLng(-33.501, 150.217),
            LatLng(-32.306, 149.248),
            LatLng(-32.491, 147.309),
            LatLng(24.854, 46.710)
        )

        val myLocation = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//        val myLocation = LatLng(-34.0, 151.0)
//        val markOptions = MarkerOptions().position(myLocation).title("I'm Here")
        googleMap.isMyLocationEnabled = true
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
//        googleMap.addMarker(markOptions)

        for(i in areas) {

            val area = MarkerOptions().position(i).title("I'm Here")

            googleMap.addMarker(area)
        }

        googleMap.setOnInfoWindowClickListener {

            val source = "${myLocation.latitude},${myLocation.longitude}"

            drawTrack(source, "24.854,46.715")
        }
    }

    private val sessionViewModel : SessionViewModel by activityViewModels {

        SessionViewModelFactory()
    }

    private fun drawTrack(source: String, destination: String) {

        try {

            // create a uri

            // create a uri
            val uri = Uri.parse("https://www.google.co.in/maps/dir/$source/$destination")

            // initializing a intent with action view.

            // initializing a intent with action view.
            val i = Intent(Intent.ACTION_VIEW, uri)

            // below line is to set maps package name
            i.setPackage("com.google.android.apps.maps")

            // below line is to set flags
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // start activity
            startActivity(i)

        } catch(e: ActivityNotFoundException) {

            // when the google maps is not installed on users device
            // we will redirect our user to google play to download google maps.

            // when the google maps is not installed on users device
            // we will redirect our user to google play to download google maps.
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")

            // initializing intent with action view.
            val i = Intent(Intent.ACTION_VIEW, uri)

            // set flags
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // to start activity
            startActivity(i)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionViewModel.getSessions()

        sessionViewModel.session.observe(viewLifecycleOwner) {

            sessions = it

//            Log.d("TAG", "onViewCreated: ${it}")

        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

//    @SuppressLint("MissingPermission")
//    private fun fetchLocation() {
//
//        if(checkPermissions()) {
//
//            if(isLocationEnabled()) {
//
//                // Final LatLog Code Here
//
//                fusedLocationProviderClient!!.lastLocation
//                    .addOnCompleteListener { task ->
//
//                        val location: Location? = task.result
//
//                        if(location == null) {
//
//                            Toast.makeText(requireContext(), "Null Received", Toast.LENGTH_LONG).show()
//
//                        } else {
//
//                            Toast.makeText(requireContext(), "Get Success", Toast.LENGTH_LONG).show()
//                            currentLocation = location
//
//                            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//                            mapFragment?.getMapAsync(callback)
//
//                            Toast.makeText(requireContext(), "${currentLocation!!.latitude} || ${currentLocation!!.longitude}", Toast.LENGTH_LONG).show()
//                        }
//                    }
//
//            } else {
//
//                // Setting Open Here
//
//                Toast.makeText(requireContext(), "Turn On Location", Toast.LENGTH_LONG).show()
//
//                val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//
//        } else {
//
//            // Request Permission Here
//            requestPermissions()
//        }
//
//    }

//    private fun isLocationEnabled() : Boolean {
//
//        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }
//
//    private fun checkPermissions() : Boolean {
//
//        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//
//            return true
//        }
//
//        return false
//    }

//    private fun requestPermissions() {
//
//        ActivityCompat.requestPermissions(
//            requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
//            Constants.PERMISSIONS_REQUEST_ACCESS_LOCATION
//        )
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if(requestCode == Constants.PERMISSIONS_REQUEST_ACCESS_LOCATION) {
//
//            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                fetchLocation()
//
//            } else {
//
//                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

//    override fun onMapReady(p0: GoogleMap) {
//
//        val myLocation = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//        val markOptions = MarkerOptions().position(myLocation).title("I'm Here")
//        p0.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
//        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
//        p0.addMarker(markOptions)
//    }
}