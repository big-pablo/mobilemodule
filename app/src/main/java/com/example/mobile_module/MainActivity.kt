package com.example.mobile_module

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.newBlockBtn).setOnClickListener {
            val openBlockMenu = Intent(this, BlockMenu::class.java)
            startActivity(openBlockMenu)
        }
    }

    val vars:Vars = Vars()
    fun addVariable(view: View){
        val v = vars
        val editNameVar = findViewById<EditText>(R.id.varName)
        val varName = editNameVar.text.toString()
        val editAmountVar = findViewById<EditText>(R.id.varVal)
        val varAmount = editAmountVar.text.toString()
        v.insertData(varName, varAmount)
        editNameVar.setText("")
        editAmountVar.setText("")
    }

}