package com.example.mobile_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val bindingVarBlock by lazy {ActivityVariablesBlockBinding.inflate(layoutInflater)}
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
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_variables_block, null) as View
        blockContainer.addView(block)
        Log.d("AddVar", "Сработало ")
      /*  bindingVarBlock.varVal.addTextChangedListener{
            variables.insertData(bindingVarBlock.varName.text.toString(),bindingVarBlock.varVal.text.toString())
        } */ //Сука он работает только на один блок, надо сделать через ViewBinding
        bindingVarBlock.varVal.addTextChangedListener {
            Log.d("Listener", "Заработало")
            variables.insertData(bindingVarBlock.varName.text.toString(),bindingVarBlock.varVal.text.toString())
        } //Сука сделал через viewBinding, работать перестало вообще
    }
}