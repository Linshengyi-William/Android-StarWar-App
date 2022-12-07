package com.example.mainactivity.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import com.example.mainactivity.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MediaActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentMusic = mutableListOf(R.raw.starwarstheme)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        controlMusic(currentMusic[0])
    }

    private fun controlMusic(id: Int){
        val musicPlay = findViewById<FloatingActionButton>(R.id.music_play)
        val musicPause = findViewById<FloatingActionButton>(R.id.music_pause)
        val musicStop = findViewById<FloatingActionButton>(R.id.music_stop)
        musicPlay.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MediaActivity", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("MediaActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

        musicPause.setOnClickListener {
            if (mp !== null) {
                mp?.pause()
                Log.d("MediaActivity", "Paused at: ${mp!!.currentPosition / 1000} seconds")
            }
            else {

            }
        }

        musicStop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }
    }

    private fun initialiseSeekBar() {
        val musicBar = findViewById<SeekBar>(R.id.music_bar)
        musicBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {


                    musicBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception){
                    musicBar.progress = 0
                }
            }
        }, 0)
    }
}