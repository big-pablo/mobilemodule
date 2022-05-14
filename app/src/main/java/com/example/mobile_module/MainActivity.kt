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
import com.example.mobile_module.databinding.ActivityArithmeticBlockBinding
import com.example.mobile_module.databinding.ActivityMainBinding
import com.example.mobile_module.databinding.ActivityOutputBlockBinding
import com.example.mobile_module.databinding.ActivityVariablesBlockBinding

class MainActivity : AppCompatActivity() {
    var comp:Compiler = Compiler(this)
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
                    "Арифметика" -> {
                        addArithmeticBlock()
                        true
                    }
                    "Вывод" -> {
                        addOutputBlock()
                    true}
                    else -> {
                        Toast.makeText(this, "А ты куда вообще тыкнул, ебанутый?", Toast.LENGTH_SHORT).show()
                    true}
                }
            }
            popupMenu.show()
        }
        binding.startButton.setOnClickListener {
            comp.execute()
        }
    }
    fun addVariableBlock()
    {
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_variables_block, null) as View
        blockContainer.addView(block)
        val varBlockBinding = ActivityVariablesBlockBinding.bind(block)
        var variable:Vars = Vars(comp,varBlockBinding.variable,varBlockBinding.expression)
        comp.addOperation(variable)
    }
    fun addArithmeticBlock()
    {
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_arithmetic_block, null) as View
        blockContainer.addView(block)
        val varBlockBinding = ActivityArithmeticBlockBinding.bind(block)
        var arithm:Arithmetic = Arithmetic(varBlockBinding.expression,varBlockBinding.variable, comp)
        //blockContainer.remove(view) Вот так можно удалять блоки
        comp.addOperation(arithm)
    }
    fun addOutputBlock()
    {
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_output_block, null) as View
        blockContainer.addView(block)
        val varBlockBinding = ActivityOutputBlockBinding.bind(block)
        var output:Print = Print(varBlockBinding.whatToOutput, comp)
        comp.addOperation(output)
    }
}