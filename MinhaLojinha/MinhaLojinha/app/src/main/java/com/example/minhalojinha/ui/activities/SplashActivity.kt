package com.example.minhalojinha.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.minhalojinha.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECATION")
        Handler(Looper.getMainLooper()).postDelayed({
            //launch the Main Activity
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish() //quando a activity deve ser fechada
        }, 2000)
    }
}