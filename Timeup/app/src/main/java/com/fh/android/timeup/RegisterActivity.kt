package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_screen.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        btRegister.setOnClickListener{
            val email = txEmail.text.toString()
            val username = txUsername.text.toString()
            val password = txPassword.text.toString()
        }

        tvShowLogin.setOnClickListener {
            // launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}