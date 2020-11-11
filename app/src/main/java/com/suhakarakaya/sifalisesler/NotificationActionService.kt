package com.suhakarakaya.sifalisesler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        var mIntent: Intent = Intent("TRACKS TRACKS").putExtra("actionname", intent?.action)
        context?.let {
            it.sendBroadcast(mIntent)
        }
    }
}