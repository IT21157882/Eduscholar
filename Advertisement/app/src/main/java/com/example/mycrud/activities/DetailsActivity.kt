package com.example.mycrud.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mycrud.R
import com.example.mycrud.models.EmployeeModel
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {



    private lateinit var tvEmpCourseName: TextView
    private lateinit var tvEmpDuration: TextView
    private lateinit var tvEmpDonatePrice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empCourseName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
//        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpCourseName = findViewById(R.id.tvEmpCourseName)
        tvEmpDuration = findViewById(R.id.tvEmpDuration)
        tvEmpDonatePrice = findViewById(R.id.tvEmpDonatePrice)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
//        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpCourseName.text = intent.getStringExtra("empCourseName")
        tvEmpDuration.text = intent.getStringExtra("empDuration")
        tvEmpDonatePrice.text = intent.getStringExtra("empDonatePrice")

    }

    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Advertisement deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empCourseName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpCourseName = mDialogView.findViewById<EditText>(R.id.etEmpCourseName)
        val etEmpDuration = mDialogView.findViewById<EditText>(R.id.etEmpDuration)
        val etEmpDonatePrice = mDialogView.findViewById<EditText>(R.id.etEmpDonatePrice)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpCourseName.setText(intent.getStringExtra("empCourseName").toString())
        etEmpDuration.setText(intent.getStringExtra("empDuration").toString())
        etEmpDonatePrice.setText(intent.getStringExtra("empDonatePrice").toString())

        mDialog.setTitle("Updating $empCourseName Advertisement")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpCourseName.text.toString(),
                etEmpDuration.text.toString(),
                etEmpDonatePrice.text.toString()
            )

            Toast.makeText(applicationContext, "Advertisement Updated", Toast.LENGTH_LONG)
                .show()

            //we are setting updated data to our textviews
            tvEmpCourseName.text = etEmpCourseName.text.toString()
            tvEmpDuration.text = etEmpDuration.text.toString()
            tvEmpDonatePrice.text = etEmpDonatePrice.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        duration: String,
        donateprice: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, duration, donateprice)
        dbRef.setValue(empInfo)
    }

}
