package com.suhakarakaya.sifalisesler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class SlideAdapter(val mDataList: ArrayList<Data>) :
    RecyclerView.Adapter<SlideAdapter.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)
        fun bind(mDataList: Data) {
            imageIcon.setImageResource(mDataList.image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_slider_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SlideAdapter.MyViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }
}