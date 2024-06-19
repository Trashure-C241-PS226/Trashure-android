package com.example.trashure.ui.setor.prediction

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.trashure.R
import com.example.trashure.databinding.ActivityPredictionBinding
import com.example.trashure.viewModelFactory.ViewModelFactory

class PredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionBinding

    private val viewModel by viewModels<PredictionViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri: Uri? = intent.getParcelableExtra("IMAGE_URI")

        viewModel.getPredict().observe(this) {
            binding.tvCategoryResult.text =
                getString(R.string.kategori) + it.category.toString() + ":"
            binding.tvPriceResult.text = it.harga
        }

        if (imageUri != null) {
            displayImage(imageUri)
        }
    }

    private fun displayImage(imageUri: Uri) {
        binding.imgLayout.setImageURI(imageUri)
    }
}