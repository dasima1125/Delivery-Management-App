package com.example.galileo.subpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.galileo.R
import com.example.galileo.dataClass.widget_test
import com.example.galileo.db.DBHandler

class SubActivity2_4widget : AppCompatActivity() {

    private lateinit var addButton_backArrow  : ImageButton
    private lateinit var addButton_testcheck  : Button
    private lateinit var addText_Num          : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_activity24widget)

        addButton_backArrow  = findViewById(R.id.activity_sub2_widdget_backArrow)
        addButton_testcheck  = findViewById(R.id.widgettest1)
        addText_Num          = findViewById(R.id.widgettestNum)

        addButton_testcheck.setOnClickListener {
            var db = DBHandler(this)

            var insertNum = addText_Num.text.toString()
            Log.d("oiia10", "$insertNum")
            if(insertNum != ""){
                var insert = widget_test(insertNum.toLong())
                db.insertDatawidget(insert)
            }
            else{
                Log.d("oiia10", "번호없음")
                
            }
            
        }

        addButton_backArrow.setOnClickListener {
            finish()
        }
    }
}