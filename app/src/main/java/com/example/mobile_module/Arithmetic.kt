package com.example.mobile_module

import android.widget.EditText
import androidx.core.text.isDigitsOnly
import java.util.*
import kotlin.collections.ArrayDeque

class Arithmetic(private val expressionField: EditText, private val valueToChange:EditText, private var comp:Compiler) : BlockInterface() {
    private val opsDictionary: Map<Char,Int> = mapOf('-' to 1, '+' to 1,'*' to 2,'/' to 2,'(' to -1,')' to -1)
    private fun generatePolishString(input:String):MutableList<String>
    {
        var output: MutableList<String> = mutableListOf()
        var stack = ArrayDeque<Char>()
        var i = 0;
        while (i < input.length) {
            var str:String = ""
            if(input[i].isLetterOrDigit())
            {
                str+=input[i]
                while(i+1<input.length && input[i+1].isLetterOrDigit()){
                    i++
                    str+=input[i]
                }
                output.add(str)
            }
            else
            {
                when(input[i]) {
                    '+','-','*','/' -> {
                        while(stack.count() > 0 && opsDictionary[stack.last()]!! >= opsDictionary[input[i]]!!){
                            output.add(stack.removeLast().toString())
                        }
                        stack.addLast(input[i])
                    }
                    '(' -> {
                        stack.addLast(input[i])
                    }
                    ')' ->{
                        println(stack)
                        while (stack.count() > 0 && stack.last() != '('){
                            output.add(stack.removeLast().toString())
                        }
                        if (stack.count()>0){
                            stack.removeLast()
                        }
                    }
                }
            }
            i++
        }
        while(stack.count()>0){
            output.add(stack.removeLast().toString())
        }
        return output
    }
    fun countPolishString(){
        val regex = "\\w+".toRegex()
        val string = generatePolishString(expressionField.text.toString())
        var stack = ArrayDeque<String>()
        for (i in string)
        {
            if (i.isDigitsOnly())
            {
                stack.addLast(i)
                continue
            }
            if(i.matches(regex))
            {
                stack.addLast(comp.getValue(i).toString())
                continue
            }
            val a = stack.removeLast().toInt()
            val b = stack.removeLast().toInt()
            when(i)
            {
                "+" -> {
                    stack.addLast((a+b).toString())
                }
                "-" -> {
                    stack.addLast((b-a).toString())
                }
                "*" -> {
                    stack.addLast((b*a).toString())
                }
                "/" -> {
                    stack.addLast((b/a).toString())
                }
            }
        }
        comp.insertData(valueToChange.text.toString(),stack.last())
    }

}
