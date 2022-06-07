package com.rayuan.view.rating

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.rayuan.R
import com.rayuan.databinding.ActivityRatingBinding
import com.rayuan.view.welcome.WelcomeActivity

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private var a = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        setupView()
        showRating()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.welcomeButton.setOnClickListener { startWelcome() }
    }

    private fun startWelcome(){
        startActivity(Intent(this, WelcomeActivity::class.java))
    }

    private fun showRating(){
        if (a==1){
            binding.previewImageView.setImageResource(R.drawable.stars1)
        }
        else if (a==2){
            binding.previewImageView.setImageResource(R.drawable.stars2)
        }
        else if (a==3){
            binding.previewImageView.setImageResource(R.drawable.stars3)
        }
        else if (a==4){
            binding.previewImageView.setImageResource(R.drawable.stars4)
        }
        else if (a==5){
            binding.previewImageView.setImageResource(R.drawable.stars5)
        }
    }

}