package com.crime_reporting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class Start_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        var h = Handler()
        h.postDelayed(
            object : Runnable{
                override fun run() {


                        startActivity(Intent(this@Start_screen,
                            LoginActivity::class.java))
                        finish()
                    }



                },3000)
    }
}