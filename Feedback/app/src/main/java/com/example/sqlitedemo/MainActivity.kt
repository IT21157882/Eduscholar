package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edDescription: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerVew()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickItem {
            Toast.makeText(this,it.name, Toast.LENGTH_SHORT).show()

            //update record
            edName.setText(it.name)
            edDescription.setText(it.Description)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)

        }
    }

    private fun updateStudent() {
        val name = edName.text.toString()
        val Description = edDescription.text.toString()

        //check record not change
        if(name == std?.name && Description == std?.Description) {
            Toast.makeText(this, "Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }

        if(std == null) return

        val std = StudentModel(id = std!!.id, name = name, Description = Description)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1) {
            clearEditText()
            getStudents()
        } else {
            Toast.makeText(this,"Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}")

        //recycler view
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val Description = edDescription.text.toString()

        if (name.isEmpty() || Description.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, Description = Description)
            val status = sqLiteHelper.insertStudent(std)

            //Check insert success or not success
            if (status > -1) {
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteStudent(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("yes"){ dialog, _->
            sqLiteHelper.deleteStudentById(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {dialog, _->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edDescription.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerVew(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edDescription = findViewById(R.id.edDescription)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}