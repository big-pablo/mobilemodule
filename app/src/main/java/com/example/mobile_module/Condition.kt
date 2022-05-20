package com.example.mobile_module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.*
import com.example.mobile_module.databinding.*

class Condition(private val innerBlockContainer:LinearLayout, addButton:Button,conditionField:EditText, private val comp:Compiler, private val context: Context) {
    private var innerOpsArray:Array<BlockInterface> = arrayOf()
    //private val binding by lazy { ActivityIfBlockBinding.inflate(layoutInflater)}
    fun addVariableBlock()
    {
        val block = LayoutInflater.from(context).inflate(R.layout.activity_variables_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityVariablesBlockBinding.bind(block)
        var variable:Vars = Vars(comp,varBlockBinding.variable,varBlockBinding.expression)
        comp.addOperation(variable)
    }
    fun addArithmeticBlock()
    {
        val block = LayoutInflater.from(context).inflate(R.layout.activity_arithmetic_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityArithmeticBlockBinding.bind(block)
        var arithm:Arithmetic = Arithmetic(varBlockBinding.expression,varBlockBinding.variable, comp)
        //blockContainer.remove(view) Вот так можно удалять блоки
        comp.addOperation(arithm)
    }
    fun addOutputBlock()
    {
        val block = LayoutInflater.from(context).inflate(R.layout.activity_output_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityOutputBlockBinding.bind(block)
        var output:Print = Print(varBlockBinding.whatToOutput, comp)
        comp.addOperation(output)
    }
    fun addConditionBlock()
    {
        val block = LayoutInflater.from(context).inflate(R.layout.activity_if_block, null) as View
        innerBlockContainer.addView(block)
        val blockBinding = ActivityIfBlockBinding.bind(block)
        var condition:Condition = Condition(blockBinding.innerBlockContainer,blockBinding.button,blockBinding.conditionInput,comp,context)
        val popupMenu = PopupMenu(context, blockBinding.button)
        popupMenu.inflate(R.menu.menu)
        blockBinding.button.setOnClickListener{
            popupMenu.setOnMenuItemClickListener {
                when (it.title)
                {
                    "Работа с переменными" -> {condition.addVariableBlock()
                        true}
                    "Арифметика" -> {
                        condition.addArithmeticBlock()
                        true
                    }
                    "Вывод" -> {
                        condition.addOutputBlock()
                        true}
                    "Условие" -> {
                        condition.addConditionBlock()
                        true}
                    else -> {
                        true}
                }
            }
            popupMenu.show()
        }
    }
}