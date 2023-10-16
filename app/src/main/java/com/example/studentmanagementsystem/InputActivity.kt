package com.example.studentmanagementsystem

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagementsystem.StudentData.AppDatabase
import com.example.studentmanagementsystem.StudentData.User
import com.example.studentmanagementsystem.databinding.FillUpFormBinding
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class InputActivity : AppCompatActivity() {
    private lateinit var binding: FillUpFormBinding
    private var user: User? = null
    var EMAIL_STRING =
        ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")

    var PHONE_STRING = ("^[0-9]{10}\$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FillUpFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val source = intent.getStringExtra("source")
        if (source == "edit") {
            user = intent.getSerializableExtra("Data") as User
            binding.addOrUpdateBtn.text = "Update"
            binding.noBtn.text = "Cancel"
            binding.eTFullName.setText(user?.name.toString())
            binding.eTPhone.setText(user?.phone.toString())
            binding.eTEmail.setText(user?.email.toString())
        }

        binding.addOrUpdateBtn.setOnClickListener {
            addUser()
        }

        binding.noBtn.setOnClickListener {
            finish()
        }

    }

    private fun isValidEmail(email: String): Boolean {
//        it returns true if the email has the required symbols
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    private fun isValidPhone(number: String): Boolean {
//        it returns true if the number of numeral in phone is 10
        return Pattern.matches(PHONE_STRING, number)
    }

    private fun addUser() {
        val fullname = binding.eTFullName.text.toString()
        val age = binding.eTAge.text.toString()
        val phone = binding.eTPhone.text.toString()
        val email = binding.eTEmail.text.toString()
        val grade = binding.eTExamGrades.text.toString()
        val book = binding.eTBookLibrary.text.toString()
        val emailError = findViewById<EditText>(R.id.eTEmail)
        val phoneError = findViewById<EditText>(R.id.eTPhone)


        lifecycleScope.launch {
//            initializing the variables to store the input texts
            if (isValidEmail(email) == false) {
                Toast.makeText(this@InputActivity, "Email address error", Toast.LENGTH_SHORT).show()
                emailError.setBackgroundColor(Color.RED)
                emailError.background.setTint(resources.getColor(R.color.error))
            } else if (isValidPhone(phone) == false) {
                emailError.setBackgroundColor(Color.GREEN)
                emailError.background.setTint(resources.getColor(R.color.success))
                Toast.makeText(this@InputActivity, "Phone number error", Toast.LENGTH_SHORT).show()
                phoneError.setBackgroundColor(Color.RED)
                phoneError.background.setTint(resources.getColor(R.color.error))

            } else {
                if (user == null) {
                    val user = User(name = fullname, phone = phone, email = email, grade = grade, age = age, book_library =book)
                    AppDatabase(this@InputActivity).userDao().addUser(user)
                } else {
                    var u = User(fullname, phone, email, grade, age, book)
                    u.roll_no = user?.roll_no ?: 0
                    AppDatabase(this@InputActivity).userDao().updateUser(u)
                }
                //return back to original page where we created
                val intent = Intent()
                intent.putExtra("MESSAGE", "Data Saved Successfully!!!!")
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
