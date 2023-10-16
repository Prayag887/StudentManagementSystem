package com.example.studentmanagementsystem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmanagementsystem.StudentData.AppDatabase
import com.example.studentmanagementsystem.databinding.DatabaseRecyclerviewBinding
import kotlinx.coroutines.launch

class StudentDataEntryActivity: AppCompatActivity() {
    private lateinit var binding: DatabaseRecyclerviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DatabaseRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idFABAdd.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("source", "add")
            addRecord.launch(intent)
        }

        binding.goBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            goBack.launch(intent)
        }
    }

    private val addRecord = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val message = data?.getStringExtra("MESSAGE")
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Cancelled by user", Toast.LENGTH_SHORT).show()
        }
    }

    private val goBack = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val message = data?.getStringExtra("MESSAGE")
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userList = AppDatabase(this@StudentDataEntryActivity).userDao().getAllUser()

            binding.recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(this@StudentDataEntryActivity, LinearLayoutManager.VERTICAL, false)
                adapter = UserAdapter().apply {
                    setData(userList)
                    setOnActionEditListener {
                        val intent = Intent(this@StudentDataEntryActivity, InputActivity::class.java)
                        intent.putExtra("source", "edit")
                        intent.putExtra("Data", it)
                        startActivity(intent)
                    }

                    setOnActionDeleteListener {
                        val builder = AlertDialog.Builder(this@StudentDataEntryActivity)
                        builder.setMessage("Are you sure?")
                        builder.setPositiveButton("Yes") { p0, p1 ->
                            lifecycleScope.launch {
                                AppDatabase(this@StudentDataEntryActivity).userDao().deleteUser(it)
                                recreate()
//                                overridePendingTransition(0, 0)
                            }
                            Toast.makeText(this@StudentDataEntryActivity,"Data Deleted Successfully, keep it up...",Toast.LENGTH_SHORT).show()
                            p0.dismiss()
                        }
                        builder.setNegativeButton("No") { p0, p1 ->
                            p0.dismiss()
                        }
                        builder.create()
                        builder.show()
                    }
                }
            }
        }
    }
}