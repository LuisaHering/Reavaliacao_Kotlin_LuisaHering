package com.example.minhalojinha.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minhalojinha.R
import com.example.minhalojinha.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // cria uma instancia do Android SharedPreferences
        val sharedPreferences =
            getSharedPreferences(Constants.MINHALOJINHA_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!

        // SET o resultado ao tv_main
        tv_main.text= "Olá $username."
    }
}