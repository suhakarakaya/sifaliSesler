package com.suhakarakaya.sifalisesler

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CreateNotification {

    companion object {

        val CHANNEL_ID: String = "channell"
        val ACTION_PREVIOUS: String = "actionprevious"
        val ACTION_PLAY: String = "actionplay"
        val ACTION_NEXT: String = "actionnext"

        lateinit var notificaiton: Notification


        fun createNotification(
            context: Context,
            mTrack: Data,
            playButton: Int,
            pos: Int,
            size: Int
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var notificaitonManagementCompat: NotificationManagerCompat =
                    NotificationManagerCompat.from(context)

                var mediaSessionCompat: MediaSessionCompat = MediaSessionCompat(context, "tag")
                var icon: Bitmap = BitmapFactory.decodeResource(context.resources, mTrack.image)

                var pendingIntentPreivous: PendingIntent?
                var drw_previous: Int
                if (pos == 0) {
                    pendingIntentPreivous = null
                    drw_previous = 0
                } else {
                    var intentPrevious =
                        Intent(context, NotificationActionService::class.java).setAction(
                            ACTION_PREVIOUS
                        )
                    pendingIntentPreivous = PendingIntent.getBroadcast(
                        context,
                        0,
                        intentPrevious,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    drw_previous = R.drawable.skip_previous
                }

                var intentPlay =
                    Intent(context, NotificationActionService::class.java).setAction(
                        ACTION_PLAY
                    )
                var pendingIntentPlay = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentPlay,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                drw_previous = R.drawable.skip_previous


                var pendingIntentNext: PendingIntent?
                var drw_next: Int
                if (pos == size) {
                    pendingIntentNext = null
                    drw_next = 0
                } else {
                    var intentNext =
                        Intent(context, NotificationActionService::class.java).setAction(
                            ACTION_NEXT
                        )
                    pendingIntentNext = PendingIntent.getBroadcast(
                        context,
                        0,
                        intentNext,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    drw_next = R.drawable.skip_next
                }


                //create notificaiton
                var notificaiton =
                    NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(mTrack.image)
                        .setContentTitle(mTrack.name)
                        .setContentText(mTrack.composer)
                        .setLargeIcon(icon)
                        .setOnlyAlertOnce(true)
                        .setShowWhen(false)
                        .addAction(drw_previous, "Previous", pendingIntentPreivous)
                        .addAction(playButton, "Play", pendingIntentPlay)
                        .addAction(drw_next, "Next", pendingIntentNext)
                        .setStyle(
                            androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2)
                                .setMediaSession(mediaSessionCompat.sessionToken)
                        )
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build()

                notificaitonManagementCompat.notify(1, notificaiton)

            }

        }


    }
}