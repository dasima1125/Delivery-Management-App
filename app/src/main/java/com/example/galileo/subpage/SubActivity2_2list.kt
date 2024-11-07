package com.example.galileo.subpage


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.galileo.R
import com.example.galileo.adpter.Move_UserAdapter_sub2_postlist
import com.example.galileo.dataClass.Move_user
import com.example.galileo.dataClass.Post_list_01
import com.example.galileo.databinding.ActivitySub2ListBinding
import com.example.galileo.db.DBHandler
import com.example.galileo.main.ItemTouchSimpleCallback
import com.example.galileo.main.ItemTouchSimpleCallbackList

class SubActivity2_2list:AppCompatActivity() {

    private lateinit var adapter: Move_UserAdapter_sub2_postlist
    private lateinit var binding: ActivitySub2ListBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var moveuserList = mutableListOf<Post_list_01>()
    private val itemTouchSimpleCallback = ItemTouchSimpleCallbackList()
    private val itemTouchHelper = ItemTouchHelper(itemTouchSimpleCallback)

    private lateinit var addButton_backArrow  : ImageButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2_list)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub2_list)

        addButton_backArrow  = findViewById(R.id.activity_sub2_list_backArrow)
        swipeRefreshLayout   = findViewById(R.id.RefreshList)

        swipeRefreshLayout.setOnRefreshListener {
            Log.d("oiia10", "안녕: ")
            testopen()
            swipeRefreshLayout.isRefreshing = false
        }
        addButton_backArrow.setOnClickListener {
          
            finish()
        }
        

        //들어올시 리스트 업데이트
        testopen()

    }

    fun testopen(){
        moveuserList.clear()
        val db = DBHandler(this)
        Log.d("oiia10", "testopen")
        val startSet = Array(db.alpha_project_1().size) { db.alpha_project_1()[it] }

        for (insert in startSet) {
            Log.d("oiia10", "반복중 $insert")
            var arr = Array(3) {""}
            arr = db.outputdatasFront(insert.toLong())
            var base = arr[2].toString().split("|")
            val mMoveuser = Post_list_01(
                moveuserList.size+1, arr[0].toString(), base[0],base[0])
            moveuserList.add(mMoveuser)
        }


    }



    override fun onResume() {
        super.onResume()
        initRecyclerView()

    }

    private fun initRecyclerView(){
        adapter = Move_UserAdapter_sub2_postlist(this)

        binding.recyclerviewMain.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMain.adapter = adapter

        adapter.differ.submitList(moveuserList)

        itemTouchSimpleCallback.setOnItemMoveListener(object : ItemTouchSimpleCallbackList.OnItemMoveListener {
            override fun onItemMove(from: Int, to: Int) {
                // Collections.swap(userList, from, to) 처럼 from, to가 필요하다면 사용
                //Log.d("MainActivity", "from Position : $from, to Position : $to")
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewMain)
    }



}