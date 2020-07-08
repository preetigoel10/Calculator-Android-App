package com.project.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric:Boolean = false //check if containing last number value
    var lastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view: View){
        if(!tvInput.text.contains("INFINITY")) {
            tvInput.append((view as Button).text)
            lastNumeric = true
        }
    }
    fun onClear(view: View)
    {
        tvInput.text = "";
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view:View)
    {
        if(lastNumeric && !lastDot)
            tvInput.append(".")
        lastNumeric = false
        lastDot = true
    }
    private fun isOperatorAdded(value:String):Boolean{
    return if (value.startsWith("-"))
        false
        else
        value.contains("/") ||  value.contains("*") ||  value.contains("+") ||  value.contains("-")
    }
    fun onOperator(view:View)
    {
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString()) && !tvInput.equals("INFINITY"))
            tvInput.append((view as Button).text)
        lastNumeric = false
        lastDot = false
    }
    fun onEqual(view: View)
    {
        if(lastNumeric)
        {
            var tvValue = tvInput.text.toString()
            var prefix =""
            try{
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty())
                    {
                        one = prefix + one
                    }
                    sub(one,two)
                }

               else if(tvValue.contains("+"))
                {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    add(one,two)
                }

                else if(tvValue.contains("*"))
                {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    mul(one,two)
                }
                else if(tvValue.contains("/"))
                {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    div(one,two)
                }

            }
            catch(e:ArithmeticException)
            {
                e.printStackTrace()
            }

        }
    }
    private fun removeZeroAfterDot(result:String):String{
        if(result.contains(".0"))
        return result.substring(0,result.length-2)

        return result
    }
    private fun add(one:String , two:String){
        tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
    }
    private fun sub(one:String , two:String){
        tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
    }
    private fun mul(one:String , two:String){
        tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
    }
    private fun div(one:String , two:String){
        if(two.toInt() == 0) {
            tvInput.text = "INFINITY"
            lastNumeric = false
            lastDot = false
        }
        else
        tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
    }
}
