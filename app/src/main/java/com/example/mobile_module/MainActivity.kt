package com.example.mobile_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.*
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.mobile_module.databinding.ActivityMainBinding
import com.example.mobile_module.databinding.ActivityVariablesBlockBinding

class MainActivity : AppCompatActivity() {
    var variables:Vars = Vars()
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val popupMenuButton = findViewById<Button>(R.id.popupMenuButton)
        val popupMenu = PopupMenu(this, popupMenuButton)
        popupMenu.inflate(R.menu.menu)
        popupMenuButton.setOnClickListener {
            popupMenu.setOnMenuItemClickListener {
                when (it.title)
                {
                    "Работа с переменными" -> {addVariableBlock()
                    true}
                    else -> {
                        Toast.makeText(this, it.itemId, Toast.LENGTH_SHORT).show()
                    true}
                }
            }
            popupMenu.show()
        }
    }
    fun addVariableBlock()
    {
        val string = "1+13*10"
        println(Arithmetic.countPolishString(string,variables))
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_variables_block, null) as View
        blockContainer.addView(block)
        val varBlockBinding = ActivityVariablesBlockBinding.bind(block)
       /* varBlockBinding.varVal.addTextChangedListener {
            Log.d("Listener", "Заработало")
            variables.insertData(varBlockBinding.varName.text.toString(),Arithmetic.countPolishString(varBlockBinding.varVal.text.toString(),variables))
        }*/
        varBlockBinding.varVal.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                variables.insertData(varBlockBinding.varName.text.toString(),Arithmetic.countPolishString(varBlockBinding.varVal.text.toString(),variables))
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

    }
}