package com.example.studentmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementsystem.databinding.LoginFormBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginFormBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.login.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val pass = binding.userPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Empty fields are not allowed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}