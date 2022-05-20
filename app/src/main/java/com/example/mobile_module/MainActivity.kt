package com.example.mobile_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.*
import com.example.mobile_module.databinding.*

class MainActivity : AppCompatActivity() {
    var comp: Compiler = Compiler(this)
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val popupMenuButton = findViewById<Button>(R.id.popupMenuButton)
        val popupMenu = PopupMenu(this, popupMenuButton)
        popupMenu.inflate(R.menu.menu)
        popupMenuButton.setOnClickListener {
            popupMenu.setOnMenuItemClickListener {
                when (it.title) {
                    "Работа с переменными" -> {
                        addVariableBlock()
                        true
                    }
                    "Арифметика" -> {
                        addArithmeticBlock()
                        true
                    }
                    "Вывод" -> {
                        addOutputBlock()
                        true
                    }
                    "Условие" -> {
                        addConditionBlock()
                        true
                    }
                    else -> {
                        Toast.makeText(this, "А ты куда вообще тыкнул?", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }
            popupMenu.show()
        }
        binding.startButton.setOnClickListener {
            comp.execute()
        }
    }

    private fun addVariableBlock() {
        val blockContainer = binding.blocksContainer
        val block =
            LayoutInflater.from(this).inflate(R.layout.activity_variables_block, null) as View
        blockContainer.addView(block)
        val blockBinding = ActivityVariablesBlockBinding.bind(block)
        var variable: Vars = Vars(comp, blockBinding.variable, blockBinding.expression)
        comp.addOperation(variable)
    }

    private fun addArithmeticBlock() {
        val blockContainer = binding.blocksContainer
        val block =
            LayoutInflater.from(this).inflate(R.layout.activity_arithmetic_block, null) as View
        blockContainer.addView(block)
        val blockBinding = ActivityArithmeticBlockBinding.bind(block)
        var arithm: Arithmetic = Arithmetic(blockBinding.expression, blockBinding.variable, comp)
        //blockContainer.remove(view) Вот так можно удалять блоки
        comp.addOperation(arithm)
    }

    private fun addOutputBlock() {
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_output_block, null) as View
        blockContainer.addView(block)
        val blockBinding = ActivityOutputBlockBinding.bind(block)
        var output: Print = Print(blockBinding.whatToOutput, comp)
        comp.addOperation(output)
    }

    private fun addConditionBlock() {
        val blockContainer = binding.blocksContainer
        val block = LayoutInflater.from(this).inflate(R.layout.activity_if_block, null) as View
        blockContainer.addView(block)
        val blockBinding = ActivityIfBlockBinding.bind(block)
        var condition: Condition = Condition(
            blockBinding.innerBlockContainer,
            blockBinding.conditionInput,
            comp,
            this
        )
        comp.addOperation(condition)
        val popupMenu = PopupMenu(this, blockBinding.button)
        popupMenu.inflate(R.menu.menu)
        blockBinding.button.setOnClickListener {
            popupMenu.setOnMenuItemClickListener {
                when (it.title) {
                    "Работа с переменными" -> {
                        condition.addVariableBlock()
                        true
                    }
                    "Арифметика" -> {
                        condition.addArithmeticBlock()
                        true
                    }
                    "Вывод" -> {
                        condition.addOutputBlock()
                        true
                    }
                    "Условие" -> {
                        condition.addConditionBlock()
                        true
                    }
                    else -> {
                        Toast.makeText(this, "А ты куда вообще тыкнул?", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }
            popupMenu.show()
        }
    }
}