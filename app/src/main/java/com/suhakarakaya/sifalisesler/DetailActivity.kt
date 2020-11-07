package com.suhakarakaya.sifalisesler

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import android.widget.Toast
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.suhakarakaya.sifalisesler.MainActivity.Companion.dataList
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.gv_images
import java.lang.Math.abs

class DetailActivity : AppCompatActivity() {

    var mPosition: Int = 0
    lateinit var detailGvAdapter: DetailGVAradpter
    lateinit var slideAdapter: SlideAdapter
    lateinit var audioManager: AudioManager
    var currentIndex: Int = 0
    var mediaPlayer = MediaPlayer()
    var handler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mPosition = intent.getIntExtra("whichOne", -1)

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        slideAdapter = SlideAdapter(dataList)




        music_name.text = dataList[mPosition].name
        music_desc.text = dataList[mPosition].description
        music_descEN.text = dataList[mPosition].descriptionEn
        music_composer.text = dataList[mPosition].composer
        detailGvAdapter = DetailGVAradpter(applicationContext, dataList)
        vp_slider.adapter = slideAdapter
        vp_slider.clipToPadding = false
        vp_slider.clipChildren = false
        vp_slider.offscreenPageLimit = 5
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(0))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f * r + 0.25f

        }

        vp_slider.setPageTransformer(compositePageTransformer)
        gv_detail_images.adapter = detailGvAdapter

        vp_slider.currentItem = mPosition
        mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(currentIndex).music)


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

            seekBar_music.max = mediaPlayer.duration
            if (mediaPlayer != null && mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                img_play.setImageResource(R.drawable.play)
                vp_slider.currentItem = dataList.get(currentIndex).image
            } else {
                mediaPlayer.start()
                img_play.setImageResource(R.drawable.pause)
                vp_slider.currentItem = dataList.get(currentIndex).image
            }
            songDetails()

        }

        img_next.setOnClickListener {
            if (mediaPlayer != null) {
                img_play.setImageResource(R.drawable.pause)
                vp_slider.currentItem = dataList.get(currentIndex).image
            }
            if (currentIndex < dataList.size - 1) {
                currentIndex++
                vp_slider.currentItem = dataList.get(currentIndex).image
            } else {
                currentIndex = 0
                vp_slider.currentItem = dataList.get(currentIndex).image
            }
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }

            mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(currentIndex).music)
            mediaPlayer.start()
            songDetails()
        }

        img_prev.setOnClickListener {
            if (mediaPlayer != null) {
                img_play.setImageResource(R.drawable.pause)
                vp_slider.currentItem = dataList.get(currentIndex).image
            }
            if (currentIndex > 0) {
                currentIndex--
                vp_slider.currentItem = dataList.get(currentIndex).image
            } else {
                currentIndex = dataList.size - 1
                vp_slider.currentItem = dataList.get(currentIndex).image
            }
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer = MediaPlayer.create(applicationContext, dataList.get(currentIndex).music)
            mediaPlayer.start()
            songDetails()

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








        Toast.makeText(this, dataList.get(mPosition).name, Toast.LENGTH_SHORT).show()
    }


    private fun songDetails() {
        for (num in dataList) {
            if (currentIndex == num.id - 1) {
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

    fun AudioManager.setMediaVolume(volumeIndex: Int, progress: Int, flags: Int) {
        // Set media volume level
        this.setStreamVolume(
            AudioManager.STREAM_MUSIC, // Stream type
            volumeIndex, // Volume index
            AudioManager.FLAG_SHOW_UI// Flags
        )
    }



}