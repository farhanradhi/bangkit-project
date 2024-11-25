package com.capstone.sampahin.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerbtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginbtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        playAnimation()

    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.SCALE_X, 0.9F, 1.1F).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvWelcome = ObjectAnimator.ofFloat(binding.tvWelcome,View.ALPHA, 1F).setDuration(1000)
        val tvDesc = ObjectAnimator.ofFloat(binding.tvDesc,View.ALPHA, 1F).setDuration(1000)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginbtn,View.ALPHA, 1F).setDuration(1000)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerbtn,View.ALPHA, 1F).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(btnLogin, btnRegister)
        }
        AnimatorSet().apply {
            playSequentially(tvWelcome, tvDesc, together)
            start()
        }
    }
}