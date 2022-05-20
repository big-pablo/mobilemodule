package com.example.mobile_module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.mobile_module.databinding.*

class Condition(addButton:Button,conditionField:EditText, comp:Compiler, context:Context) {
    private var innerOpsArray:Array<BlockInterface> = arrayOf()
    private val binding by lazy { ActivityIfBlockBinding.inflate(layoutInflater)}
    fun addVariableBlock()
    {
        val blockContainer = binding.
        val block = LayoutInflater.from(context).inflate(R.layout.activity_variables_block, null) as View
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