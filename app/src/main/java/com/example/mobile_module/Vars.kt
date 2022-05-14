package com.example.mobile_module

import android.widget.EditText

class Vars(private var comp:Compiler, private val varName:EditText, private val varVal:EditText):BlockInterface() {
    fun insertVariable(){
        comp.insertData(varName.text.toString(),varVal.text.toString())
    }
}