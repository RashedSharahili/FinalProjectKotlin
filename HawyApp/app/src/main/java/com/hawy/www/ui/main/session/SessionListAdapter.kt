package com.hawy.www.ui.main.session

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.maps.android.SphericalUtil
import com.hawy.www.R
import com.hawy.www.data.model.session.Session
import com.hawy.www.databinding.SessionItemBinding
import com.hawy.www.ui.auth.currentLocation
import com.hawy.www.ui.main.DiscoverFragmentDirections
import com.hawy.www.ui.main.HomeFragmentDirections
import kotlin.math.round

class SessionListAdapter(private val context: Context) :
    ListAdapter<Session, SessionListAdapter.SessionViewHolder>(DiffCallBack) {


    class SessionViewHolder(var binding: SessionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionViewHolder {
        return SessionViewHolder(
            SessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.sessionTitle.text = item.title

        holder.binding.sessionDistance.text = context.getString(R.string.sessions_distance, distance(currentLocation!!.latitude, currentLocation!!.longitude, item.lat!!, item.long!!).toString())

        holder.binding.sessionCategoryLabel.text = item.category

//        Glide.with(context)
//            .load("https://firebasestorage.googleapis.com/v0/b/finalproject-3363c.appspot.com/o/nx7dMUSA9mY9ehjOhILCNhwny102.jpeg?alt=media&token=87416067-b626-4d11-8401-e599dfe9c27f")
//            .into(holder.binding.userProfileImage)

        when(item.category) {

            "??????????" -> {

//                Log.d("TAG", "onBindViewHolder: ??????????")

                holder.binding.sessionCategoryImage.setImageResource(R.drawable.health)
            }

            "??????????????" -> {

//                Log.d("TAG", "onBindViewHolder: ??????????????")

                holder.binding.sessionCategoryImage.setImageResource(R.drawable.tech)
            }

            "????????" -> {

//                Log.d("TAG", "onBindViewHolder: ????????")

                holder.binding.sessionCategoryImage.setImageResource(R.drawable.art)
            }

            "??????????????" -> {

//                Log.d("TAG", "onBindViewHolder: ??????????????")

                holder.binding.sessionCategoryImage.setImageResource(R.drawable.engineering)
            }

            "??????????????" -> {

//                Log.d("TAG", "onBindViewHolder: ??????????????")

                holder.binding.sessionCategoryImage.setImageResource(R.drawable.sports)
            }
        }

        holder.itemView.setOnClickListener {

            val action = DiscoverFragmentDirections.actionDiscoverFragmentToSessionDetailsFragment(
                sessionCategory = item.category,
                sessionTitle = item.title,
                sessionDetails = item.details,
                sessionLat = item.lat.toString(),
                sessionLong = item.long.toString(),
                userPhoneNumber = item.phoneNumber
            )

            holder.itemView.findNavController().navigate(action)
        }

//        holder.binding.name.text = current.name
//
//
//        holder.binding
//            .cerdView.setOnClickListener {
//                val action =
//                    ListOfPersonsFragmentDirections.actionListOfPersonsFragmentToParsoneInfoFragment(
//                        current.id,current.name,current.lattLoac.toString(),current.longLoca.toString())
//                Log.d("TAG", "current id = ${current.id}")
//                holder.itemView.findNavController().navigate(action)
//            }

        //show person Image
//        Glide.with(context).load(current.imageUri.toUri()).circleCrop().placeholder(R.drawable.loading_animation)
//            .error(R.drawable.ic_baseline_account_circle_24).into(holder.binding.presonImage)
    }

    private fun distance(
        userLat: Double,
        userLong: Double,
        sessionLat: Double,
        sessionLong: Double
    ): Double {

//        val theta = userLat - userLong
//
//        var dist = Math.sin(deg2rad(userLat)) * Math.sin(deg2rad(sessionLat)) + Math.cos(deg2rad(userLat)) * Math.cos(deg2rad(sessionLat)) * Math.cos(deg2rad(theta))
//
//        dist = Math.acos(dist)
//        dist = rad2deg(dist)
//        dist *= 60 * 1.1515
//
//        return dist

        val userCoordinate = com.google.android.gms.maps.model.LatLng(userLat, userLong)
//        userCoordinate.setLatitude(userLat)
//        userCoordinate.setLongitude(userLong)

        val sessionCoordinate = com.google.android.gms.maps.model.LatLng(sessionLat, sessionLong)
//        sessionCoordinate.setLatitude(sessionLat)
//        sessionCoordinate.setLongitude(sessionLong)

        // on below line we are calculating the distance between sydney and brisbane
        val distance = SphericalUtil.computeDistanceBetween(userCoordinate, sessionCoordinate);

        return round(distance / 1000) * 1000 / 1000
    }

    private fun deg2rad(deg: Double) : Double {

        return (deg * Math.PI / 180.0)
    }

    private fun rad2deg(rad: Double) : Double {

        return (rad * 180.0 / Math.PI)
    }

    // to camper between
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(
                oldItem: Session,
                newItem: Session
            ): Boolean {
                return oldItem.title === newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Session,
                newItem: Session
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}