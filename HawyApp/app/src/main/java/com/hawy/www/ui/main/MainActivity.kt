package com.hawy.www.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hawy.www.R
import com.hawy.www.constants.Constants
import com.hawy.www.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //TODO :- [1] Create binding Object
    private lateinit var binding: ActivityMainBinding


    val REQUEST_CODE = 101

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        this.supportActionBar!!.title = getString(R.string.home_tab)


        //TODO :- [2] Layout View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        binding.bottomNavigationView.setupWithNavController(navController)

//        binding.bottomNavigationView.setOnItemSelectedListener {
//
//            when(it.itemId) {
//
//                R.id.homeFragment -> {
//                    loadFragment(HomeFragment())
//                    this.supportActionBar!!.title = getString(R.string.home_tab)
////                    tvTitle.text = getString(R.string.home_tab)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.discoverFragment -> {
//                    loadFragment(DiscoverFragment())
//                    this.supportActionBar!!.title = getString(R.string.discover_tab)
////                    tvTitle.text = getString(R.string.discover_tab)
//                    return@setOnItemSelectedListener true
//                }
//                R.id.mapsFragment -> {
//                    loadFragment(MapsFragment())
//                    this.supportActionBar!!.title = getString(R.string.map_tab)
////                    tvTitle.text = getString(R.string.map_tab)
//                    return@setOnItemSelectedListener true
//                }
//                else -> {
//
//                    loadFragment(ProfileFragment())
//                    this.supportActionBar!!.title = getString(R.string.profile_tab)
////                    tvTitle.text = getString(R.string.profile_tab)
//                    return@setOnItemSelectedListener true
//                }
//            }
//        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}