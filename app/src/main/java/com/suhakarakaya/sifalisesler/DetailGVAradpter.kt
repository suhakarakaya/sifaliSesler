package com.suhakarakaya.sifalisesler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


class DetailGVAradpter(
    var mList: ArrayList<Data>,
    var clickListener: onImageClickListener
) :
    RecyclerView.Adapter<DetailGVAradpter.MyViewHolder>() {


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageIcon = view.findViewById<ImageView>(R.id.img_detail_header)

        fun bind(mDataList: Data, action: onImageClickListener) {
            imageIcon.setImageResource(mDataList.image)
            imageIcon.setOnClickListener {
                action.onItemClick(adapterPosition, mDataList)
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailGVAradpter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_container_slider_detail_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailGVAradpter.MyViewHolder, position: Int) {
        //holder.bind(mList[position])
        holder.bind(mList.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface onImageClickListener {
        fun onItemClick(position: Int, item: Data)
    }
}



