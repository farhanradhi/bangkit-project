package com.capstone.sampahin.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.ActivityRegisterBinding
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val factory: RegisterViewModelFactory = RegisterViewModelFactory.getInstance()
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        binding.btnSignUp.setOnClickListener { handleRegister() }
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun handleRegister() {
        val username = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val passwordConfirm = binding.etPasswordConfirm.text.toString().trim()

        var isValid = true

        if (username.isEmpty()) {
            binding.etlName.error =getString(R.string.must_fill_name)
            isValid = false
        } else {
            binding.etlName.error = null
        }

        if (email.isEmpty()) {
            binding.etlEmail.error = getString(R.string.must_fill_email)
            isValid = false
        } else if (!isValidEmail(email)) {
            binding.etlEmail.error = getString(R.string.email_format)
            isValid = false
        } else {
            binding.etlEmail.error = null
        }

        if (password.isEmpty()) {
            binding.etlPassword.error = getString(R.string.must_fill_password)
            isValid = false
        } else if (password.length < 8) {
            binding.etlPassword.error = getString(R.string.password_min_character)
            isValid = false
        } else {
            binding.etlPassword.error = null
        }

        if (passwordConfirm.isEmpty()) {
            binding.etlPasswordConfirm.error = getString(R.string.must_fill_password_confirm)
            isValid = false
        } else if (password != passwordConfirm) {
            binding.etlPasswordConfirm.error = getString(R.string.password_not_match)
            isValid = false
        } else {
            binding.etlPasswordConfirm.error = null
        }

         if (isValid) {
            registerViewModel.findRegister(username, email, password)
        }
    }


    private fun isValidEmail(email: String): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return email.matches(emailPattern.toRegex())
    }


    private fun setupObserver() {
        registerViewModel.registerResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    Intent(this, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
