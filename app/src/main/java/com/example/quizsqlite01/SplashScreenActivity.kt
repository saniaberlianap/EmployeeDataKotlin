package com.example.quizsqlite01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({

            startActivity(Intent(this@SplashScreenActivity,Login::class.java))
            finish()

        }, 3000)  //untuk waktu splash screen nya 3 detik
    }
}
