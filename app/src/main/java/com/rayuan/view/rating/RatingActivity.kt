package com.rayuan.view.rating

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.WindowInsets
import android.view.WindowManager
import com.rayuan.R
import com.rayuan.databinding.ActivityRatingBinding
import com.rayuan.view.welcome.WelcomeActivity

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        showRating()
        setupView()
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
        val labelRating = intent.getStringExtra(LABEL)
        val handwritingPhoto = (intent.getStringExtra(HANDWRITING))
        val imageBytes = Base64.decode(handwritingPhoto, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        when (labelRating) {
            "Uncertain" -> {
                binding.previewImageView.setImageResource(R.drawable.stars1)
            }
            "Poor" -> {
                binding.previewImageView.setImageResource(R.drawable.stars2)
            }
            "Okay" -> {
                binding.previewImageView.setImageResource(R.drawable.stars3)
            }
            "Great" -> {
                binding.previewImageView.setImageResource(R.drawable.stars4)
            }
            "Excellent" -> {
                binding.previewImageView.setImageResource(R.drawable.stars5)
            }
        }
        binding.apply {
            ratingTextView.text=getString(R.string.viewRating)
            previewHandwritingView.setImageBitmap(decodedImage)
            labelTextView.text=labelRating
        }
    }

    companion object{
        const val LABEL="label"
        const val HANDWRITING="handwriting"
    }

}