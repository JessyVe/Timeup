package com.fh.android.timeup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txEmail
import kotlinx.android.synthetic.main.activity_login.txPassword

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        btLogin.setOnClickListener {
            val email = txEmail.text.toString()
            val password = txPassword.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{

                }
                .addOnFailureListener{

                }
        }

        tvShowRegistration.setOnClickListener {
            finish()
        }
    }
}