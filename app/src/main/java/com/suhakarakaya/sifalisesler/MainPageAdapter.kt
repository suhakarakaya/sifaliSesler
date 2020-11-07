package com.suhakarakaya.sifalisesler

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class MainPageAdapter(var mContext: Context, var mList: ArrayList<Data>) : BaseAdapter() {
    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(mContext, R.layout.item_main_container, null)
        var icons: ImageView = view.findViewById(R.id.img_container)
        icons.setImageResource(mList.get(position).image)
        icons.setOnClickListener {
            Intent(mContext, DetailActivity::class.java).also {
                it.putExtra("whichOne", mList.get(position).id - 1)
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(it)
            }
        }
        return view
    }
}