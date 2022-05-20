package com.example.mobile_module

import android.widget.EditText
import androidx.core.text.isDigitsOnly

class Print(private val whatToOutput:EditText, private val comp:Compiler):BlockInterface() {
    private val stringRegex = "(\"|')\\w*(\"|')".toRegex()
    fun output():String{
        val text = whatToOutput.text.toString()
        if (text.isDigitsOnly()){
            return text
        }
        try{
            return (comp.getValue(text).toString())
        }
        catch (e:Exception)
        {
            return(text)
        }
    }
}