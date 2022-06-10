package com.rayuan.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.rayuan.PreferencesHelper
import com.rayuan.R
import com.rayuan.view.welcome.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val pref by lazy { PreferencesHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when(pref.getTheme("switchTheme")){
            true->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        setContentView(R.layout.activity_splash_screen)
        val time = 2000
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(applicationContext, WelcomeActivity::class.java))
            finish()
        }, time.toLong())
    }
}