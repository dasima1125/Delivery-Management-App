package com.example.galileo.subpage

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.galileo.R
import com.example.galileo.db.DBHandler
import com.example.galileo.adpter.Move_UserAdapter_sub1
import com.example.galileo.dataClass.Move_user_sub1
import com.example.galileo.databinding.ActivitySub1Binding
import com.example.galileo.main.ItemTouchSimpleCallback


class SubActivity1 : AppCompatActivity(){
    private lateinit var adapter: Move_UserAdapter_sub1

    private lateinit var binding: ActivitySub1Binding

    private var moveuserList2 = mutableListOf<Move_user_sub1>()

    private val itemTouchSimpleCallback = ItemTouchSimpleCallback()
    private val itemTouchHelper = ItemTouchHelper(itemTouchSimpleCallback)

    private lateinit var nextButton : Button
    private lateinit var viewTotal1: TextView
    private lateinit var viewTotal2: TextView
    private lateinit var viewTotal3: TextView
    private lateinit var viewTotal4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub1)

        nextButton = findViewById(R.id.btnClose)

        viewTotal1 = findViewById(R.id.viewTotal1)
        viewTotal2 = findViewById(R.id.viewTotal2)
        viewTotal3 = findViewById(R.id.viewTotal3)
        viewTotal4 = findViewById(R.id.viewTotal4)

        val intent = intent
        val receivedData = intent.getStringExtra("key")


        val db = DBHandler(this)
        var a1: Long

        receivedData?.let { data ->
            Log.d("oiia", "receivedData: ready ")
            val cleanData = data.trim()
            try {
                a1 = cleanData.toLong()
                var a2 = db.outputdatasFront(a1)

                var a3 = a2[2].toString().split("|")

                viewTotal1.text = a3[0].trim()
                var a1_1 = a3[1].split("T")
                var a1_2 = a1_1[1].split(":")
                if (a1_2[0].toInt() < 12) {
                    //보안원칙 좀 위배하긴함
                    if (a1_2[0][0] == '0') {
                        viewTotal2.text = "오전 " + a1_2[0][1] + " 시 " + a1_2[1] + " 분"
                    } else {
                        viewTotal2.text = "오전 " + a1_2[0] + " 시 " + a1_2[1] + " 분"
                    }
                } else {
                    viewTotal2.text =
                        "오후 " + (a1_2[0].toInt() - 12).toString() + " 시 " + a1_2[1] + " 분"
                }
                viewTotal3.text = a3[2]
                viewTotal4.text = a3[3]

                var a2_1 =a2[3].split(",")


                try {
                    for (i in a2_1.indices) {
                        var filter = a2_1[i].split("|")
                        val mUser = Move_user_sub1(filter[0], filter[2], filter[3])
                        moveuserList2.add(mUser)
                    }
                } catch (e: Exception) {
                    for (i in 1 until 11){
                        val mUser =Move_user_sub1("안녕$i",":"," ")
                        moveuserList2.add(mUser)
                    }
                }

            } catch (e: NumberFormatException) {
                Log.d("oiia", "blyat")
            }
        }
        nextButton.setOnClickListener {
            finish()
        }


    }


    override fun onResume() {
        super.onResume()
        initRecyclerView()

    }



    private fun initRecyclerView() {

        adapter = Move_UserAdapter_sub1(this)
        binding.recyclerviewSub1.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSub1.adapter = adapter

        adapter.differ.submitList(moveuserList2)


    }




}