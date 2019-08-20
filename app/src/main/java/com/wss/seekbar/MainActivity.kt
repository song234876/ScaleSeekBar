package com.wss.seekbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wss.library.ScaleSeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        mSpeedSb.setOnSeekChangeListener(object :ScaleSeekBar.OnSeekChangeListener{
            override fun onSeek(progress: Float) {
                Log.d("wss","onSeek$progress")
            }

            override fun OnSeekEnd(progress: Float) {
                Log.d("wss","OnSeekEnd")
            }
        })
    }
}
