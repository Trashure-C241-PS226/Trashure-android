package com.example.trashure.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.trashure.R
import com.example.trashure.data.model.User
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.databinding.ActivityLoginBinding
import com.example.trashure.ui.dashboard.DashboardActivity
import com.example.trashure.ui.register.RegisterActivity
import com.example.trashure.ui.register.RegisterViewModel
import com.example.trashure.viewModelFactory.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        viewModel.login(email, password).observe(this@LoginActivity) {
            when (it) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }

                is ApiResponse.Success -> {
                    showLoading(false)
                    val token = it.data.loginResult?.token
                    viewModel.saveSession(User(email, token!!, true))
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }

                is ApiResponse.Error -> {
                    showLoading(false)
                    showToast(it.error)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}