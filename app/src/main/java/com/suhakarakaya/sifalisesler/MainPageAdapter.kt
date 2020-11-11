package com.suhakarakaya.sifalisesler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MainPageAdapter(var mContext: Context, var mList: ArrayList<Data>) :BaseAdapter()
     {
         override fun getCount(): Int {
             return mList.size
         }

         override fun getItem(p0: Int): Any {
             return mList.get(p0)
         }

         override fun getItemId(p0: Int): Long {
             return p0.toLong()
         }

         override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
             var view:View= View.inflate(mContext,R.layout.item_main_container,null)
             var icons:ImageView=view.findViewById(R.id.img_container)
             icons.setImageResource(mList[p0].image!!)
             /*icons.setOnClickListener {
                 Intent(mContext, DetailActivity::class.java).also {
                     it.putExtra("whichOne", mList.get(p0).id - 1)
                     it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                     mContext.startActivity(it)
                 }
             }*/
             return view
         }


     }




