package com.example.mobile_module

import android.widget.EditText
import androidx.core.text.isDigitsOnly

class Print(private val whatToOutput:EditText, private val comp:Compiler):BlockInterface() {
    val stringRegex = "(\"|')\\w*(\"|')".toRegex()
    fun output():String{
        val text = whatToOutput.text.toString()
        if (text.isDigitsOnly()){
            return text
        }
        if (text.matches(stringRegex)){
            var delimeter = ('"').toString()
            return  text.removeSurrounding(delimeter)
        }
        else{
            return(comp.getValue(text).toString())
        }
    }
}