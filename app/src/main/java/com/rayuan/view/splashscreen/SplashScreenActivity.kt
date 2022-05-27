package com.rayuan.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rayuan.R
import com.rayuan.view.welcome.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val time = 2000
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }, time.toLong())
    }
}