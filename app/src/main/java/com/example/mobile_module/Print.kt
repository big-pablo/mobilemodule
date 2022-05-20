package com.example.mobile_module

import android.widget.EditText

class Print(private val whatToOutput: EditText, private val comp: Compiler) : BlockInterface() {
    private val stringRegex = "(\"|')\\w*(\"|')".toRegex()
    fun output(): String {
        val text = whatToOutput.text.toString()
        try {
            return (comp.getValue(text).toString())
        } catch (e: Exception) {
            return (text)
        }
    }
}