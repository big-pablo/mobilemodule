package com.example.mobile_module

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.core.text.isDigitsOnly

import com.example.mobile_module.databinding.*

class Condition(
    private val innerBlockContainer: LinearLayout,
    private val conditionField: EditText,
    private val comp: Compiler,
    private val context: Context
) : BlockInterface() {
    private var firstPart: String = ""
    private var operator: String = ""
    private var secondPart: String = ""
    private var innerOpsArray: Array<BlockInterface> = arrayOf()
    fun addVariableBlock() {
        val block =
            LayoutInflater.from(context).inflate(R.layout.activity_variables_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityVariablesBlockBinding.bind(block)
        var variable: Vars = Vars(comp, varBlockBinding.variable, varBlockBinding.expression)
        innerOpsArray = innerOpsArray.plus(variable)
    }

    fun addArithmeticBlock() {
        val block =
            LayoutInflater.from(context).inflate(R.layout.activity_arithmetic_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityArithmeticBlockBinding.bind(block)
        var arithm: Arithmetic =
            Arithmetic(varBlockBinding.expression, varBlockBinding.variable, comp)
        innerOpsArray = innerOpsArray.plus(arithm)
    }

    fun addOutputBlock() {
        val block =
            LayoutInflater.from(context).inflate(R.layout.activity_output_block, null) as View
        innerBlockContainer.addView(block)
        val varBlockBinding = ActivityOutputBlockBinding.bind(block)
        var output: Print = Print(varBlockBinding.whatToOutput, comp)
        innerOpsArray = innerOpsArray.plus(output)
    }

    fun addConditionBlock() {
        val block = LayoutInflater.from(context).inflate(R.layout.activity_if_block, null) as View
        innerBlockContainer.addView(block)
        val blockBinding = ActivityIfBlockBinding.bind(block)
        var condition: Condition = Condition(
            blockBinding.innerBlockContainer,
            blockBinding.conditionInput,
            comp,
            context
        )
        innerOpsArray = innerOpsArray.plus(condition)
        val popupMenu = PopupMenu(context, blockBinding.button)
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
                        true
                    }
                }
            }
            popupMenu.show()
        }
    }

    fun splitExpression() {
        val expression = conditionField.text.toString()
        Log.i("Cond", expression)
        var firstPartPassed = false
        for (i in expression) {
            when (i) {
                '>', '<', '=', '!' -> {
                    operator += i
                    firstPartPassed = true
                }
                else -> {
                    if (firstPartPassed) {
                        secondPart += i
                    } else {
                        firstPart += i
                    }
                }
            }
        }
    }

    fun checkIf(): Boolean {
        splitExpression()
        val left = countPolishString(firstPart)
        val right = countPolishString(secondPart)
        when (operator) {
            ">" -> {
                return left > right
            }
            "<" -> {
                return left < right
            }
            ">=" -> {
                return left >= right
            }
            "<=" -> {
                return left <= right
            }
            "==" -> {
                Log.i("Cond", "Зашло")
                return left == right
            }
            "!=" -> {
                return left != right
            }
            else -> {
                return false
            }
        }
    }

    fun runCondition() {
        if (checkIf()) {
            for (i in innerOpsArray) {
                when (i) {
                    is Vars -> {
                        i.insertVariable()
                    }
                    is Arithmetic -> {
                        i.countPolishString()
                    }
                    is Print -> {
                        comp.outputString = comp.outputString + "\n" + i.output()
                    }
                    is Condition -> {
                        i.runCondition()
                    }
                }
            }
        }
        firstPart = ""
        secondPart = ""
        operator = ""

    }

    //Дальше будет страшно, я просто скопировал арифметику из класса Arithmetic ибо не знал, как вызвать его, не превращая Arithmetic в объект
    private fun generatePolishString(input: String): MutableList<String> {
        val opsDictionary: Map<Char, Int> =
            mapOf('-' to 1, '+' to 1, '*' to 2, '/' to 2, '(' to -1, ')' to -1)
        var output: MutableList<String> = mutableListOf()
        var stack = ArrayDeque<Char>()
        var i = 0
        while (i < input.length) {
            var str: String = ""
            if (input[i].isLetterOrDigit()) {
                str += input[i]
                while (i + 1 < input.length && input[i + 1].isLetterOrDigit()) {
                    i++
                    str += input[i]
                }
                output.add(str)
            } else {
                when (input[i]) {
                    '+', '-', '*', '/' -> {
                        while (stack.count() > 0 && opsDictionary[stack.last()]!! >= opsDictionary[input[i]]!!) {
                            output.add(stack.removeLast().toString())
                        }
                        stack.addLast(input[i])
                    }
                    '(' -> {
                        stack.addLast(input[i])
                    }
                    ')' -> {
                        println(stack)
                        while (stack.count() > 0 && stack.last() != '(') {
                            output.add(stack.removeLast().toString())
                        }
                        if (stack.count() > 0) {
                            stack.removeLast()
                        }
                    }
                }
            }
            i++
        }
        while (stack.count() > 0) {
            output.add(stack.removeLast().toString())
        }
        return output
    }

    fun countPolishString(input: String): String {
        val regex = "\\w+".toRegex()
        val string = generatePolishString(input)
        var stack = ArrayDeque<String>()
        for (i in string) {
            if (i.isDigitsOnly()) {
                stack.addLast(i)
                continue
            }
            if (i.matches(regex)) {
                stack.addLast(comp.getValue(i).toString())
                continue
            }
            val a = stack.removeLast().toInt()
            val b = stack.removeLast().toInt()
            when (i) {
                "+" -> {
                    stack.addLast((a + b).toString())
                }
                "-" -> {
                    stack.addLast((b - a).toString())
                }
                "*" -> {
                    stack.addLast((b * a).toString())
                }
                "/" -> {
                    stack.addLast((b / a).toString())
                }
            }
        }
        return stack.last()
    }
}