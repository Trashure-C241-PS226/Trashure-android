package com.example.trashure.ui.thankyou

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trashure.databinding.ActivityThankYouBinding
import com.example.trashure.ui.dashboard.DashboardActivity

class ThankYouActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThankYouBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOkay.setOnClickListener {
            val intent = Intent(this@ThankYouActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}