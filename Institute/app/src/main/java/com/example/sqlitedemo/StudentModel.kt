package com.example.sqlitedemo

import java.util.*


class StudentModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var Description: String = ""
){
    companion object{
        fun getAutoId():Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}