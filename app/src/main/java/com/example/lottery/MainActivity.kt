package com.example.lottery

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private var didRun = false
    private val pickNumSet = hashSetOf<Int>()
    private var numberTextViewList = listOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberTextViewList = listOf(txt1,txt2,txt3,txt4,txt5,txt6)

        numberpicker.minValue = 1
        numberpicker.maxValue = 45

        initRun()
        initAdd()
        initClear()

    }

    private fun initRun() {

        run_btn.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index: Int, value: Int ->

                val textView = numberTextViewList[index]

                textView.text = value.toString()
                textView.isVisible = true

               setBackGround(value,textView)

                }

            }

        }


    private fun getRandomNumber(): List<Int> {

        val numList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumSet.contains(i)){
                        continue
                    }
                    this.add(i)
                }
            }

        numList.shuffle()
        val newList = pickNumSet.toList() + numList.subList(0, 6 - pickNumSet.size)



        return  newList.sorted()
    }


    private fun initAdd() {

        add_btn.setOnClickListener {

            if(didRun){
                Toast.makeText(this,"초기화 후에 시도해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumSet.size >= 5){
                Toast.makeText(this,"번호는 5개까지 선택할 수 있습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumSet.contains(numberpicker.value)){
                Toast.makeText(this,"이미 선택한 번호입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val textView = numberTextViewList[pickNumSet.size]

            textView.isVisible = true
            textView.text = numberpicker.value.toString()

            setBackGround(numberpicker.value,textView)



            pickNumSet.add(numberpicker.value)

        }
    }

    private fun initClear() {

        clear_btn.setOnClickListener {
            pickNumSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false

            }

            didRun = false
        }
    }

    private fun setBackGround(value : Int, textView:TextView){
        when(value){

            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_grey)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)

        }
    }



}