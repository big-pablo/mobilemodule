package com.example.mobile_module

class Vars {
    private val numbersMap = mutableMapOf<String, String>()

    fun insertData(key: String, value:String ){
        numbersMap.put(key,value)
        println(numbersMap)
    }
}