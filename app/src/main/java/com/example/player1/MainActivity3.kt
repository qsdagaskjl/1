package com.example.player1

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    var playBtton: Button? = null
    var start: SeekBar? = null
    var end: SeekBar? = null
    var startText: TextView? = null
    var endText: TextView? = null
    var song: MediaPlayer? = null
    var imageView: ImageView? = null
    var animation: Animation? = null
    var SongTotalTime = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        //隐藏进度条
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        playBtton = findViewById<Button>(R.id.play)
        startText = findViewById<TextView>(R.id.TextStart)
        endText = findViewById<TextView>(R.id.TextEnd)
        imageView = findViewById<ImageView>(R.id.img)
        animation = AnimationUtils.loadAnimation(this, R.anim.rotation)

        //添加歌曲
        song = MediaPlayer.create(this, R.raw.zf)
        song!!.isLooping = true
        song!!.seekTo(0)
        song!!.setVolume(0.5f, 0.5f)
        SongTotalTime = song!!.duration
        start = findViewById<SeekBar>(R.id.PlayLine)
        start?.run {
            setMax(SongTotalTime)
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    if (b) {
                        song!!.seekTo(i)
                        setProgress(i)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
        //音量调节
        end = findViewById<SeekBar>(R.id.volume)
        end?.run {
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    val volume = i / 100f
                    song!!.setVolume(volume, volume)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
        Thread {
            while (song != null) {
                try {
                    val message = Message()
                    message.what = song!!.currentPosition
                    handler.sendMessage(message)
                    Thread.sleep(1000)
                } catch (ignored: InterruptedException) {
                }
            }
        }.start()
    }

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        @SuppressLint("SetText18n")
        override fun handleMessage(message: Message) {
            val SeekBarPosition = message.what

            //更新进度条
            start?.progress ?:   SeekBarPosition
            val Time = createTimeText(SeekBarPosition)
            startText?.text ?:   Time

            //时间计算
            val remainingTime = createTimeText(SongTotalTime - SeekBarPosition)
            endText?.text ?:   "-$remainingTime"
        }
    }

    //时间显示
    fun createTimeText(time: Int): String {
        var timeText: String?
        val min = time / 1000 / 60
        val sec = time / 1000 % 60
        timeText = "$min:"
        if (sec < 10) timeText += "0"
        timeText += sec
        return timeText
    }

    fun PlayButton(view: View?) {
        if (!song!!.isPlaying) {
            song!!.start()
            imageView!!.startAnimation(animation)
            playBtton!!.setBackgroundResource(R.drawable.baseline_pause_24)
        } else {
            song!!.pause()
            imageView!!.clearAnimation()
            playBtton!!.setBackgroundResource(R.drawable.baseline_play_arrow_24)
        }
    }
}