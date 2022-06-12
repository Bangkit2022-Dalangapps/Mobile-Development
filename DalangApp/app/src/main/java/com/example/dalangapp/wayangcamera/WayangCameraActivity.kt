package com.example.dalangapp.wayangcamera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dalangapp.MainActivity
import com.example.dalangapp.R
import com.example.dalangapp.content.detail.WayangDetailActivity
import com.example.dalangapp.databinding.ActivityWayangCameraBinding
import com.example.dalangapp.ml.WayangModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class WayangCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWayangCameraBinding
    private lateinit var bitmap: Bitmap


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val EXTRA_OPTION = "extra_option"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWayangCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getStringExtra(EXTRA_OPTION)) {
            "camera" -> {
                startCamera()
            }
            "gallery" -> {
                startGallery()
            }
            else -> {
                startCamera()
            }
        }

        binding.btnGallery.setOnClickListener {
            Log.d("mssg", "button pressed")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        }

        binding.btnCamera.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, 200)
        }

        binding.btnBackToHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun startCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 200)
    }

    private fun startGallery() {
        Log.d("mssg", "button pressed")
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(intent, 250)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 250 && data != null) {

            binding.cardGolek.visibility = View.GONE
            binding.cardKulit.visibility = View.GONE
            binding.cardSuluh.visibility = View.GONE
            binding.cardGedog.visibility = View.GONE
            binding.cardKrucil.visibility = View.GONE
            binding.cardBeber.visibility = View.GONE

            binding.tvClassifyAs.text = getString(R.string.classified_as)
            binding.ivImagePreview.visibility = View.VISIBLE


            binding.ivImagePreview.setImageURI(data.data)

            val uri: Uri? = data.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            // AUTOMATICALLY RUN ML AFTER SELECTING PHOTO FROM GALLERY
            val labels =
                application.assets.open("WayangLabel.txt").bufferedReader().use { it.readText() }
                    .split("\n")

            val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = WayangModel.newInstance(this)

            val image = TensorImage(DataType.FLOAT32)
            image.load(resized)

            val byteBuffer = image.buffer

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            when (val max = getMaxAccuracy(outputFeature0.floatArray)) {
                0 -> {
                    binding.cardBeber.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardBeber.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "1")
                            startActivity(it)
                        }
                    }
                }

                1 -> {
                    binding.cardGedog.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardGedog.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "2")
                            startActivity(it)
                        }
                    }
                }

                2 -> {
                    binding.cardGolek.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardGolek.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "5")
                            startActivity(it)
                        }
                    }
                }

                3 -> {
                    binding.cardKrucil.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardKrucil.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "6")
                            startActivity(it)
                        }
                    }
                }

                4 -> {
                    binding.cardKulit.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardKulit.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "3")
                            startActivity(it)
                        }
                    }
                }

                5 -> {
                    binding.cardSuluh.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardSuluh.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "4")
                            startActivity(it)
                        }
                    }
                }
                else -> binding.tvResultTitle.text = "UNKNOWN"
            }
            model.close()


        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {

            binding.cardGolek.visibility = View.GONE
            binding.cardKulit.visibility = View.GONE
            binding.cardSuluh.visibility = View.GONE
            binding.cardGedog.visibility = View.GONE
            binding.cardKrucil.visibility = View.GONE
            binding.cardBeber.visibility = View.GONE

            binding.tvClassifyAs.text = getString(R.string.classified_as)
            binding.ivImagePreview.visibility = View.VISIBLE

            bitmap = data.extras?.get("data") as Bitmap
            binding.ivImagePreview.setImageBitmap(bitmap)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)

            val labels =
                application.assets.open("WayangLabel.txt").bufferedReader().use { it.readText() }
                    .split("\n")
            val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = WayangModel.newInstance(this)

            val image = TensorImage(DataType.FLOAT32)
            image.load(resized)

            val byteBuffer = image.buffer
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer


            when (val max = getMaxAccuracy(outputFeature0.floatArray)) {
                0 -> {
                    binding.cardBeber.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardBeber.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "1")
                            startActivity(it)
                        }
                    }
                }

                1 -> {
                    binding.cardGedog.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardGedog.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "2")
                            startActivity(it)
                        }
                    }
                }

                2 -> {
                    binding.cardGolek.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardGolek.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "5")
                            startActivity(it)
                        }
                    }
                }

                3 -> {
                    binding.cardKrucil.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardKrucil.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "6")
                            startActivity(it)
                        }
                    }
                }

                4 -> {
                    binding.cardKulit.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardKulit.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "3")
                            startActivity(it)
                        }
                    }
                }

                5 -> {
                    binding.cardSuluh.visibility = View.VISIBLE
                    binding.tvResultTitle.text = labels[max]
                    binding.btnCardSuluh.setOnClickListener {
                        Intent(this, WayangDetailActivity::class.java).also {
                            it.putExtra(WayangDetailActivity.EXTRA_ID, "4")
                            startActivity(it)
                        }
                    }
                }
                else -> binding.tvResultTitle.text = "UNKNOWN"
            }
            model.close()
        } else {
            binding.tvResultTitle.text = getString(R.string.select_first)
            binding.tvClassifyAs.text = getString(R.string.classified_as_please)
            binding.ivImagePreview.setImageResource(R.drawable.no_image_selected)
            binding.cardKrucil.visibility = View.GONE
            binding.cardGedog.visibility = View.GONE
            binding.cardSuluh.visibility = View.GONE
            binding.cardKulit.visibility = View.GONE
            binding.cardGolek.visibility = View.GONE
            binding.cardBeber.visibility = View.GONE
            Toast.makeText(
                this,
                "Please select an image first",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun getMaxAccuracy(arr: FloatArray): Int {
        var index = 0
        var min = 0.0f

        for (i in 0..5) {
            if (arr[i] > min) {
                min = arr[i]
                index = i
            }
        }
        return index
    }
}