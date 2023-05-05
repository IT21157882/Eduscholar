package com.example.mycrud.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mycrud.R

class MainActivity : AppCompatActivity() {
    private lateinit var volunteerfun: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        volunteerfun = findViewById(R.id.addAd)

        volunteerfun.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            }

    }
    }