package com.rayuan.view.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rayuan.R
import com.rayuan.api.ApiConfig
import com.rayuan.api.ApiService
import com.rayuan.databinding.ActivityUploadBinding
import com.rayuan.response.ResponseRating
import com.rayuan.response.ResponseRatingItem
import com.rayuan.view.camera.CameraActivity
import com.rayuan.view.rating.RatingActivity
import com.rayuan.view.utils.encoderBase64
import com.rayuan.view.utils.reduceFileImage
import com.rayuan.view.utils.rotateBitmap
import com.rayuan.view.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private var getFile: File? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        setupView()
        setupAction()

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction() {
        binding.cameraButton.setOnClickListener { startCameraX() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { startUpload() }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startUpload() {
        when {
            ((getFile != null)) -> {
                val imagefile = reduceFileImage(getFile as File)

//                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//                val imageMultipart : RequestBody.Part = MultipartBody.Part.createFormData(
//                    "photo",
//                    file.name,
//                    requestImageFile
//                )

                val imageEncoded = encoderBase64(imagefile).toRequestBody()
                Log.d(TAG, encoderBase64(imagefile))
                val service = ApiConfig().getApiService().uploadImage(ApiService.api_key,imageEncoded)
                service.enqueue(object : Callback<List<ResponseRating>> {
                    override fun onResponse(
                        call: Call<List<ResponseRating>>,
                        response: Response<List<ResponseRating>>
                    ){
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                Toast.makeText(this@UploadActivity, getString(R.string.uploadSuccess), Toast.LENGTH_SHORT).show()
                                Log.e(TAG, response.raw().toString())
                                val intent = Intent(this@UploadActivity, RatingActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@UploadActivity, "response.message()", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, response.message())
                        }
                    }
                    override fun onFailure(call: Call<List<ResponseRating>>, t: Throwable) {
                        Toast.makeText(this@UploadActivity, getString(R.string.failedRetrofitInstance), Toast.LENGTH_SHORT).show()
                        Log.e(TAG, t.message.toString())
                    }
                })


            }
            ((getFile == null)) -> {
                Toast.makeText(this@UploadActivity, getString(R.string.imageWarning), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "anjay ga ada gambar")
            }
//            else -> {
//                Toast.makeText(this@UploadActivity, getString(R.string.no_data_warning), Toast.LENGTH_SHORT).show()
//            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@UploadActivity)

            getFile = myFile

            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, getString(R.string.no_permission), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private const val TAG = "Error"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}