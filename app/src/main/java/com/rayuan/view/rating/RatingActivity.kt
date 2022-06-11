package com.rayuan.view.rating

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.net.toUri
import com.bumptech.glide.Glide
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
        val handwritingPhoto = intent.getStringExtra(HANDWRITING)
        val cameraStatus = intent.getStringExtra(BACK_CAMERA)

        when (labelRating) {
            "Poor" -> {
                binding.previewImageView.setImageResource(R.drawable.stars1)
            }
            "Uncertain" -> {
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
            labelTextView.text=labelRating
        }
        Log.d(BACK_CAMERA, cameraStatus.toString())
        when (cameraStatus) {
            "true" -> {
                Glide.with(this)
                    .load(rotateImageBack())
                    .into(binding.previewHandwritingView)
            }
            "false" -> {
                Glide.with(this)
                    .load(rotateImageFront())
                    .into(binding.previewHandwritingView)
            }
            else -> {
                Glide.with(this)
                    .load(handwritingPhoto)
                    .into(binding.previewHandwritingView)
            }
        }
    }

    private fun rotateImageBack(): Bitmap? {
        val handwritingPhoto = intent.getStringExtra(HANDWRITING)
        val toTransform = MediaStore.Images.Media.getBitmap(this.contentResolver, handwritingPhoto?.toUri())
        val matrix = Matrix()
        matrix.postRotate(90F)

        return Bitmap.createBitmap(
            toTransform,
            0,
            0,
            toTransform.width,
            toTransform.height,
            matrix,
            true
        )
    }

    private fun rotateImageFront(): Bitmap? {
        val handwritingPhoto = intent.getStringExtra(HANDWRITING)
        val toTransform = MediaStore.Images.Media.getBitmap(this.contentResolver, handwritingPhoto?.toUri())
        val matrix = Matrix()
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, toTransform.width / 2f, toTransform.height / 2f)

        return Bitmap.createBitmap(
            toTransform,
            0,
            0,
            toTransform.width,
            toTransform.height,
            matrix,
            true
        )
    }

    companion object{
        const val LABEL="label"
        const val HANDWRITING="handwriting"
        const val BACK_CAMERA="back"
    }


}