package com.suhakarakaya.sifalisesler

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.suhakarakaya.sifalisesler.MainActivity.Companion.dataList
import com.suhakarakaya.sifalisesler.MainActivity.Companion.mPosition
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.gv_images
import java.lang.Math.abs

class DetailActivity : AppCompatActivity(), DetailGVAradpter.onImageClickListener, Playable {

    lateinit var detailGvAdapter: DetailGVAradpter
    lateinit var slideAdapter: SlideAdapter
    lateinit var audioManager: AudioManager
    lateinit var notificationmanager: NotificationManager

    var mediaPlayer = MediaPlayer()
    var handler: Handler = Handler()


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //mPosition = intent.getIntExtra("whichOne", -1)
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        slideAdapter = SlideAdapter(dataList)
        initInfo()
        detailGvAdapter = DetailGVAradpter(dataList, this)
        vp_slider.adapter = slideAdapter
        vp_slider.clipToPadding = false
        vp_slider.clipChildren = false
        vp_slider.offscreenPageLimit = 3
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.40f * r + 0.60f
        }
        vp_slider.setPageTransformer(compositePageTransformer)
        rv_detail_images.layoutManager =
            LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_detail_images.adapter = detailGvAdapter
        vp_slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (mediaPlayer.isPlaying) {
                    if (mPosition <= position) {
                        mPosition = position
                        vp_slider.currentItem = mPosition
                        nextMusic()
                        onTrackNext()
                    } else {
                        mPosition = position
                        vp_slider.currentItem = mPosition
                        prevMusic()
                        onTrackPrevious()
                    }
                } else {
                    mPosition = position
                    if (0 <= mPosition && mPosition <= 11) {
                        music_name.text = dataList[mPosition].name
                        music_desc.text = dataList[mPosition].description
                        music_descEN.text = dataList[mPosition].descriptionEn
                        music_composer.text = dataList[mPosition].composer
                    }
                }
                initInfo()
            }
        })
        mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(mPosition).music)
        var maxV: Int = audioManager.mediaCurrentVolume
        var curv: Int = audioManager.mediaMaxVolume
        seekBar_music.max = maxV
        seekBar_volume.setProgress(curv)
        seekBar_volume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setMediaVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        img_play.setOnClickListener {
            playMusic()
        }
        img_next.setOnClickListener {
            if (mPosition < 11) {
                onTrackNext()
                vp_slider.currentItem = mPosition
                nextMusic()
            }

        }
        img_prev.setOnClickListener {
            if (mPosition > 0) {
                onTrackPrevious()
                vp_slider.currentItem = mPosition
                prevMusic()
            }

        }
        seekBar_music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    seekBar_music.setProgress(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        //Toast.makeText(this, dataList.get(mPosition).name, Toast.LENGTH_SHORT).show()
        vp_slider.currentItem = mPosition

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannler()
            registerReceiver(broadcastReceive, IntentFilter("TRACKS TRACKS"))
            startService(Intent(baseContext, OnClearFromRecentService::class.java))
        }

    }

    private fun songDetails() {
        for (num in dataList) {
            if (mPosition == num.id - 1) {
                music_name.text = num.name
            }
        }

        mediaPlayer.setOnPreparedListener {
            seekBar_music.max = mediaPlayer.duration
            mediaPlayer.start()
        }

        Thread(object : Runnable {
            override fun run() {
                while (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying) {
                            var message = Message()
                            message.what = mediaPlayer.currentPosition
                            handler.handleMessage(message)
                            Thread.sleep(1000)
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        ).start()
    }

    val AudioManager.mediaMaxVolume: Int
        get() = this.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    val AudioManager.mediaCurrentVolume: Int
        get() = this.getStreamVolume(AudioManager.STREAM_MUSIC)

    var broadcastReceive: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var action = intent?.extras?.getString("actionname")

            when (action) {
                CreateNotification.ACTION_PREVIOUS ->
                    onTrackPrevious()
                CreateNotification.ACTION_PLAY ->
                    if (mediaPlayer.isPlaying) {
                        onTrackPause()
                        mediaPlayer.pause()
                    } else {
                        onTrackPlay()
                        mediaPlayer.start()
                    }
                CreateNotification.ACTION_NEXT ->
                    onTrackNext()
            }
        }

    }


    fun AudioManager.setMediaVolume(volumeIndex: Int, progress: Int, flags: Int) {
        // Set media volume level
        this.setStreamVolume(
            AudioManager.STREAM_MUSIC, // Stream type
            volumeIndex, // Volume index
            AudioManager.FLAG_SHOW_UI// Flags
        )
    }

    override fun onItemClick(position: Int, item: Data) {
        mPosition = position
        vp_slider.currentItem = mPosition
        initInfo()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.stop()
    }

    fun nextMusic() {
        if (mediaPlayer != null) {
            img_play.setImageResource(R.drawable.pause)
            //vp_slider.currentItem = mPosition
            initInfo()
        }
        if (mPosition <= dataList.size - 1) {
            //mPosition++
            //vp_slider.currentItem = mPosition
            initInfo()
        } else {
            mPosition = 0
            //vp_slider.currentItem = mPosition
            initInfo()
        }
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(mPosition).music)
        mediaPlayer.start()
        songDetails()

    }

    fun prevMusic() {
        if (mediaPlayer != null) {
            img_play.setImageResource(R.drawable.pause)
            //vp_slider.currentItem = mPosition
            initInfo()
        }
        if (mPosition >= 0) {
            //mPosition--
            //vp_slider.currentItem = mPosition
            initInfo()
        } else {
            mPosition = dataList.size - 1
            //vp_slider.currentItem = mPosition
            initInfo()
        }
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(mPosition).music)
        mediaPlayer.start()
        songDetails()
    }

    fun initInfo() {
        if (0 <= mPosition && mPosition <= 11) {
            music_name.text = dataList[mPosition].name
            music_desc.text = dataList[mPosition].description
            music_descEN.text = dataList[mPosition].descriptionEn
            music_composer.text = dataList[mPosition].composer
        }

    }

    fun playMusic() {
        seekBar_music.max = mediaPlayer.duration
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            img_play.setImageResource(R.drawable.play)
            onTrackPause()
        } else {
            //mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(mPosition).music)
            mediaPlayer.start()
            img_play.setImageResource(R.drawable.pause)
            vp_slider.currentItem = mPosition
            onTrackPlay()
        }
        songDetails()
    }

    fun createChannler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CreateNotification.CHANNEL_ID,
                "KOD Dev",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationmanager = getSystemService(NotificationManager::class.java)
            if (notificationmanager != null) {
                notificationmanager.createNotificationChannel(channel)
            }


        }
    }

    override fun onTrackPrevious() {
        mPosition--
        CreateNotification.createNotification(
            this,
            dataList.get(mPosition),
            R.drawable.pause_asset,
            mPosition,
            dataList.size - 1
        )
        if (mPosition >= 0) {
            //onTrackPrevious()
            //vp_slider.currentItem = mPosition
            prevMusic()
        }
        //initInfo()
    }

    override fun onTrackPlay() {
        CreateNotification.createNotification(
            this,
            dataList.get(mPosition),
            R.drawable.pause_asset,
            mPosition,
            dataList.size - 1
        )


    }

    override fun onTrackPause() {
        CreateNotification.createNotification(
            this,
            dataList.get(mPosition),
            R.drawable.play_asset,
            mPosition,
            dataList.size - 1
        )


    }

    override fun onTrackNext() {
        if (mPosition <= dataList.size - 1) {
            mPosition++
            CreateNotification.createNotification(
                this,
                dataList.get(mPosition),
                R.drawable.pause_asset,
                mPosition,
                dataList.size - 1
            )
            if (mPosition <= 11) {
                //onTrackNext()
                //vp_slider.currentItem = mPosition
                nextMusic()
            }
            //initInfo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationmanager.cancelAll()
        }
        unregisterReceiver(broadcastReceive)
    }


}