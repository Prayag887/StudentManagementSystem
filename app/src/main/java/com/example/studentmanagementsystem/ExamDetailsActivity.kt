package com.example.studentmanagementsystem

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagementsystem.StudentData.AppDatabase
import com.example.studentmanagementsystem.databinding.ExamDetailsBinding
import kotlinx.coroutines.launch

class ExamDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ExamDetailsBinding
    private var data: List<String> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ExamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            data = AppDatabase(this@ExamDetailsActivity).userDao().getNames()
            val allinfo = AppDatabase(this@ExamDetailsActivity).userDao().getAllUser()
            println("database:: $data")

            println("data:::: $data")

            val arrayAdapter = ArrayAdapter<String>(
                this@ExamDetailsActivity,
                android.R.layout.simple_spinner_dropdown_item,
                data
            )
            binding.spinnerExamDetails.adapter = arrayAdapter
            binding.spinnerExamDetails.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            this@ExamDetailsActivity,
                            "Name $data Selected Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val myNameView = findViewById<TextView>(R.id.exam_name)
                        myNameView.text = data[position]

                        val myIdView = findViewById<TextView>(R.id.exam_roll)
                        myIdView.text = allinfo[position].roll_no.toString()

                        val myGradeView = findViewById<TextView>(R.id.exam_grades)
                        myGradeView.text = allinfo[position].grade

                        val examRemarks = findViewById<TextView>(R.id.remarks)
                        examRemarks.text =
                            "Congrats!! You have achieved " + allinfo[position].grade + " in your final examination"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        finish()
                    }
                }
        }
    }
}
