package com.hawy.www.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.hawy.www.ui.auth.AuthActivity
import com.hawy.www.R
import com.hawy.www.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    //TODO :- [1] Create binding Object
    private lateinit var binding: ActivityOnBoardingBinding

    private lateinit var mSharedPreferences: SharedPreferences

    private var itemList = ArrayList<OnBoardingData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO :- [2] Layout View Binding
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        mSharedPreferences = getSharedPreferences("myBoarding", MODE_PRIVATE)

        val isFirstTime = mSharedPreferences.getBoolean("firstTime", true)

        if(isFirstTime) {

            setUpViewPager()

        } else {

            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

//        Toast.makeText(this, "$isFirstTime", Toast.LENGTH_SHORT).show()
    }

    private fun setUpViewPager() {

        itemList = getItems()
        binding.viewPager.adapter = PagerAdapter(itemList)
        binding.wormDot.setViewPager2(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(pageChaneCallback)
    }

    private var pageChaneCallback : ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            binding.previousBtn.visibility = View.INVISIBLE.takeIf { position == 0 }?: View.VISIBLE

            if(position == itemList.size -1) {

                binding.nextBtn.text = getString(R.string.get_started)
                binding.skipBtn.visibility = View.GONE

            } else {

                binding.nextBtn.text = getString(R.string.next)
                binding.skipBtn.visibility = View.VISIBLE
            }
        }
    }

    fun getItems() : ArrayList<OnBoardingData> {

        val items = ArrayList<OnBoardingData>()

        items.add(
            OnBoardingData(
            getString(R.string.on_boarding1_title),
            getString(R.string.on_boarding1_desc),
            R.drawable.boboarding1
        )
        )

        items.add(
            OnBoardingData(
            getString(R.string.on_boarding2_title),
            getString(R.string.on_boarding2_desc),
            R.drawable.onboarding2
        )
        )

        return items
    }

    fun onClick(view : View) {

        when(view) {

            binding.previousBtn -> {

                val currentItemPosition = binding.viewPager.currentItem
                if(currentItemPosition == 0) return

                binding.viewPager.setCurrentItem(currentItemPosition -1, true)

            }
            binding.nextBtn -> {

                val currentItemPosition = binding.viewPager.currentItem
                if(currentItemPosition == itemList.size -1) {

                    val editor = mSharedPreferences.edit()
                    editor.putBoolean("firstTime", false)
                    editor.commit()

                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()

                    return
                }

                binding.viewPager.setCurrentItem(currentItemPosition +1, true)
            }
            binding.skipBtn -> {

                val editor = mSharedPreferences.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()

                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()

                return
            }
        }
    }
}