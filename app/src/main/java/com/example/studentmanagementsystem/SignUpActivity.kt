package com.example.studentmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementsystem.databinding.SignUpFormBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignUpFormBinding
    private lateinit var firebaseAuth: FirebaseAuth

    var EMAIL_STRING =
        ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signup.setOnClickListener {
            val email = binding.signUpUserEmail.text.toString()
            val pass = binding.signUpPassword.text.toString()
            val repass = binding.reenterPassword.text.toString()

            if (isValidEmail(email) == false){
                Toast.makeText(this@SignUpActivity, "Email address error", Toast.LENGTH_SHORT).show()

            }else if (email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty()) {
                if (pass == repass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                "There was and error while creating the account",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@SignUpActivity, "The passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
//        it returns true if the email has the required symbols
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }
}

