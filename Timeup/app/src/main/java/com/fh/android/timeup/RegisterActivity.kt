package com.fh.android.timeup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.register_screen.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        btRegister.setOnClickListener{
            performRegistration()
        }

        tvShowLogin.setOnClickListener {
            // launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegistration(){
        val email = txEmail.text.toString()
        val username = txUsername.text.toString()
        val password = txPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email and password may not be empty!", Toast.LENGTH_SHORT).show()
            return
        } else if(password.length < 6){
            Toast.makeText(this, "Password most consist of at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        // Firebase auth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful)return@addOnCompleteListener

                Log.d("Register", "Successfully crated user with uid: ${it.result?.user?.uid}")
            }
            .addOnFailureListener{
                val errorText = "Failed to create user! ${it.message}"
                Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
                Log.d("Register", errorText)
            }
    }
}