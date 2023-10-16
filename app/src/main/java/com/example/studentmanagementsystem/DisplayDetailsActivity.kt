package com.example.studentmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagementsystem.StudentData.AppDatabase
import com.example.studentmanagementsystem.StudentData.User
import com.example.studentmanagementsystem.databinding.DisplayDetailsBinding
import kotlinx.coroutines.launch


class DisplayDetailsActivity : AppCompatActivity() {
    private lateinit var binding: DisplayDetailsBinding
    private  var data :List<String> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DisplayDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Assuming that you have a list of strings
    override fun onResume() {
        super.onResume()


        lifecycleScope.launch {
            data = AppDatabase(this@DisplayDetailsActivity).userDao().getNames()
            val allinfo = AppDatabase(this@DisplayDetailsActivity).userDao().getAllUser()
            println("database:: $data")

            println("data:::: $data")

            val arrayAdapter = ArrayAdapter<String>(
                this@DisplayDetailsActivity,
                android.R.layout.simple_spinner_dropdown_item,
                data
            )
            binding.spinnerDisplayDetails.adapter = arrayAdapter
            binding.spinnerDisplayDetails.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            this@DisplayDetailsActivity,
                            "Name Selected Successfully, keep it up...",
                            Toast.LENGTH_SHORT
                        ).show()

                        val myNameView = findViewById<TextView>(R.id.display_name)
                        myNameView.text = data[position]

                        val myIdView = findViewById<TextView>(R.id.display_roll)
                        myIdView.text = allinfo[position].roll_no.toString()

                        val myEmailView = findViewById<TextView>(R.id.display_email)
                        myEmailView.text = allinfo[position].email

                        val myAgeView = findViewById<TextView>(R.id.display_age)
                        myAgeView.text = allinfo[position].age

                        val myPhoneView = findViewById<TextView>(R.id.display_phone)
                        myPhoneView.text = allinfo[position].phone

                        val myGradeView = findViewById<TextView>(R.id.display_grade)
                        myGradeView.text = allinfo[position].grade
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }


                }
        }
    }
}
