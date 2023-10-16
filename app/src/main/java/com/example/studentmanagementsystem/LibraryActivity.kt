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
import com.example.studentmanagementsystem.databinding.LibraryBooksBinding
import kotlinx.coroutines.launch

class LibraryActivity:AppCompatActivity() {
    private lateinit var binding: LibraryBooksBinding
    private  var data :List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LibraryBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            data = AppDatabase(this@LibraryActivity).userDao().getNames()
            val allinfo = AppDatabase(this@LibraryActivity).userDao().getAllUser()
            println("database:: $data")

            println("data:::: $data")

            val arrayAdapter = ArrayAdapter<String>(
                this@LibraryActivity,
                android.R.layout.simple_spinner_dropdown_item,
                data
            )
            binding.spinnerLibraryDetails.adapter = arrayAdapter
            binding.spinnerLibraryDetails.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            this@LibraryActivity,
                            "Name $data Selected Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val myNameView = binding.libraryName
                        myNameView.text = allinfo[position].name

                        val myIdView = binding.libraryRoll
                        myIdView.text = allinfo[position].roll_no.toString()

                        val myBookName = binding.bookName
                        myBookName.text = allinfo[position].grade

                        val bookName = binding.bookNameRemarks
                        bookName.text = "You had taken " +allinfo[position].book_library+ " last time"
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        finish()
                    }


                }
        }
    }

}
