package com.example.mycrud.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrud.models.EmployeeModel
import com.example.mycrud.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpCourceName: EditText
    private lateinit var etEmpDuration: EditText
    private lateinit var etEmpDonatePrice: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpCourceName = findViewById(R.id.etEmpCourseName)
        etEmpDuration = findViewById(R.id.etEmpDuration)
        etEmpDonatePrice = findViewById(R.id.etEmpDonatePrice)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empCourseName = etEmpCourceName.text.toString()
        val empDuration = etEmpDuration.text.toString()
        val empDonatePrice = etEmpDonatePrice.text.toString()

        if (empCourseName.isEmpty()) {
            etEmpCourceName.error = "Please enter Course Name"
            return
        }
        if (empDuration.isEmpty()) {
            etEmpDuration.error = "Please enter Course Duration"
            return
        }
        if (empDonatePrice.isEmpty()) {
            etEmpDonatePrice.error = "Please enter Donate Price"
            return
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empCourseName, empDuration, empDonatePrice)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Added new Advertisement", Toast.LENGTH_LONG).show()

                etEmpCourceName.text.clear()
                etEmpDuration.text.clear()
                etEmpDonatePrice.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}