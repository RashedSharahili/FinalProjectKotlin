package com.hawy.www.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hawy.www.constants.Constants
import com.hawy.www.databinding.ItemOnboardingScreen1Binding
import com.hawy.www.databinding.ItemOnboardingScreen2Binding

class PagerAdapter(private val items: ArrayList<OnBoardingData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {

            Constants.SHOW_IMAGE_ON_TOP -> {
                TopViewHolder(
                    ItemOnboardingScreen1Binding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }

            else -> {

                BottomViewHolder(
                    ItemOnboardingScreen2Binding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data = items[position]

        when(holder) {

            is TopViewHolder -> holder.bind(data)
            is BottomViewHolder -> holder.bind(data)
        }
    }

//    override fun getItemCount(): Int {
//        return items.size
//    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return Constants.SHOW_IMAGE_ON_TOP.takeIf { position % 2 == 0 }?: Constants.SHOW_IMAGE_ON_BOTTOM
    }

    class TopViewHolder(private val binding: ItemOnboardingScreen1Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: OnBoardingData) {

            binding.titleOnboarding1.text = data.title
            binding.textOnboarding1.text = data.text
            binding.imageOnboarding1.setImageResource(data.image)
        }
    }

    class BottomViewHolder(private val binding: ItemOnboardingScreen2Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: OnBoardingData) {

            binding.titleOnboarding2.text = data.title
            binding.textOnboarding2.text = data.text
            binding.imageOnboarding2.setImageResource(data.image)
        }
    }
}