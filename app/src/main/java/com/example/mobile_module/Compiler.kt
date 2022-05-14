package com.example.mobile_module

import android.app.AlertDialog
import android.content.Context
import android.util.Log

class Compiler(private val context:Context) {
    private var compArray:Array<BlockInterface> = arrayOf()
    private var numbersMap = mutableMapOf<String, String>()
    private var outputArray:Array<String> = arrayOf()
    fun addOperation(toAdd:BlockInterface)
    {
        compArray = compArray.plus(toAdd)
        print("Должно добавиться, щас выведет")
        Log.i("Comp", "Added")
        for (i in compArray)
        {
            Log.i("Comp",i.toString())
        }
    }
    fun getValue(key: String): Int
    {
        val value = (numbersMap.getValue(key)).toInt()
        return(value)
    }
    fun insertData(key: String, value:String ){
        numbersMap.put(key,value)
        println(numbersMap)
    }
    fun execute(){
        for(i in compArray)
        {
            when(i){
                is Vars -> {
                    i.insertVariable()
                }
                is Arithmetic -> {
                    i.countPolishString()
                }
                is Print ->{
                    outputArray.plus(i.output())
                }
            }
        }
        val outputDialog = AlertDialog.Builder(context).setTitle("Ваш вывод")
            .setMessage("*Cюда вставить вывод*")
        outputDialog.show()
    }
}