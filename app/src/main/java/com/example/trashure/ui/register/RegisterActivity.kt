package com.example.trashure.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trashure.R
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.databinding.ActivityRegisterBinding
import com.example.trashure.ui.login.LoginActivity
import com.example.trashure.viewModelFactory.AuthViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            register()
        }
        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register() {
        val name = binding.editUsername.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        val nomor = "087515812"
        val provinsi = "DKI Jakarta"
        val kabKota = "Bekasi"
        val kecamatan = "Karanganyar"

        viewModel.register(name, email, password, nomor, provinsi, kabKota, kecamatan)
            .observe(this@RegisterActivity) {
                if (it != null) {
                    when (it) {
                        is ApiResponse.Loading -> {
                            showLoading(true)
                        }

                        is ApiResponse.Success -> {
                            showLoading(false)
                            AlertDialog.Builder(this).apply {
                                setTitle(getString(R.string.registration_success))
                                val registerMessage = getString(R.string.registration_message)
                                setMessage(registerMessage)
                                setPositiveButton(getString(R.string.next)) { _, _ ->
                                    val intent =
                                        Intent(this@RegisterActivity, LoginActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is ApiResponse.Error -> {
                            showLoading(false)
                            showToast(it.error)
                        }
                    }
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            listOf(editEmail, editPassword, editUsername, btnSignUp).forEach {
                it.isEnabled = !isLoading
                it.isClickable = !isLoading
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}