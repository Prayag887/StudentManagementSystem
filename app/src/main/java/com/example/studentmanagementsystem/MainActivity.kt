package com.example.studentmanagementsystem


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementsystem.databinding.MainMenuBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dataEntry.setOnClickListener {
            val intent = Intent(this, StudentDataEntryActivity::class.java)
            intent.putExtra("source", "add")
            startActivity(intent)
        }

        binding.studentDetails.setOnClickListener {
            val intent = Intent(this, DisplayDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.studentExamDetails.setOnClickListener {
            val intent = Intent(this, ExamDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.library.setOnClickListener {
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
    }
}