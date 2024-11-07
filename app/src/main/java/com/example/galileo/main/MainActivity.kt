package com.example.galileo.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.WorkManager
import com.example.galileo.R
import com.example.galileo.adpter.Move_UserAdapter_main
import com.example.galileo.dataClass.Delivery_User3
import com.example.galileo.dataClass.Move_user
import com.example.galileo.databinding.ActivityMainBinding
import com.example.galileo.db.DBHandler
import com.example.galileo.sheet.sheettest
import com.example.galileo.subpage.SubActivity1
import com.example.galileo.subpage.SubActivity2
import com.example.galileo.testLine.PeriodicLogPrinterService
import com.example.galileo.testLine.testZone
import com.example.galileo.testLine.PeriodicLogPrinterWorkerHelper

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: Move_UserAdapter_main
    private lateinit var binding: ActivityMainBinding
    lateinit var preference_main: SharedPreferences

    val PREFERENCE_NAME = "preference_galileo_01"
    val PREFERENCE_WIDGET_ID = "widgetId_galileo_01"


    private var overlayView: View? = null
    private var moveuserList = mutableListOf<Move_user>()
    private val itemTouchSimpleCallback = ItemTouchSimpleCallback()
    private val itemTouchHelper = ItemTouchHelper(itemTouchSimpleCallback)

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var addButton  : Button
    private lateinit var addButton2 : Button
    private lateinit var addButton3 : Button
    private lateinit var addButton4 : Button
    private lateinit var addButton5 : Button
    private lateinit var addButton6 : Button
    private lateinit var addButton7 : Button
    private lateinit var addButton9 : Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //start setting

        //WorkManager.getInstance(this).cancelUniqueWork(PeriodicLogPrinterWorkerHelper.WORKER_TAG)
        //PeriodicLogPrinterWorkerHelper.startPeriodicLogPrinterWorker(this)



        preference_main    = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        addButton = findViewById(R.id.add_button)
        addButton2 = findViewById(R.id.add_button2)
        addButton3 = findViewById(R.id.add_button3)
        addButton4 = findViewById(R.id.add_button4)
        addButton5 = findViewById(R.id.add_button5)
        addButton6 = findViewById(R.id.add_button6)
        addButton7 = findViewById(R.id.add_button7)
        addButton7 = findViewById(R.id.add_button8)
        addButton9 = findViewById(R.id.add_button9)



        startupdate()

        swipeRefreshLayout.setOnRefreshListener {


            updateList()
            swipeRefreshLayout.isRefreshing = false
        }
        addButton2.setOnClickListener {

            Toast.makeText(this, "접속 성공.", Toast.LENGTH_SHORT).show()
            var db = DBHandler(this)
            db.autoUpdater()
            //testZone().test01(190)

        }
        addButton3.setOnClickListener{
            Log.d("oiia", "online")
            var db = DBHandler(this)
            try {
                val idToInsert = 680490177973
                val existingRecord = db.getRecordById(idToInsert)


                if (existingRecord == null) { //id가 비어있을경우
                    val user1 = Delivery_User3(
                        idToInsert,
                        "시**, 황**, kr.cjlogistics, CJ Logistics, +8215881255",
                        "배송?완료 | 2023-09-13T14:57:55+09:00 | 서울문래 | 고객님의 상품이 배송완료 되었습니다.(담당사원:한창훈 010-3111-7259)",
                        "집화처리 | 2023-09-12T17:21:30+09:00 | 서울용두중앙 | 보내시는 고객님으로부터 상품을 인수받았습니다, 간선상차 | 2023-09-12T18:19:02+09:00 | 동대문 | 물류터미널로 상품이 이동중입니다.코와송"
                    )
                    db.insertDataBeta2(user1)
                    Toast.makeText(this, "데이터가 성공적으로 삽입되었습니다.", Toast.LENGTH_SHORT).show()
                }

                else {  //아이디가 존재할경우
                    Toast.makeText(this, "이미 해당 아이디가 데이터베이스에 존재합니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "데이터 삽입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()

            }
            db.close()
        }
        addButton4.setOnClickListener {
            var db  =DBHandler(this)
            db.flusherbeta()
            Toast.makeText(this, "초기화", Toast.LENGTH_SHORT).show()

        }
        addButton5.setOnClickListener {
            Log.d("oiia", "passed")
            var db  =DBHandler(this)

            try {
                val user1 = Delivery_User3(680490177974,
                    "시**, 황**, kr.cjlogistics, CJ Logistics, +8215881255",
                    "배송완료 | 2023-09-13T14:57:55+09:00 | 서울문래 | 고객님의 상품이 배송완료 되었습니다.(담당사원:한창훈 010-3111-7259)",
                    ""
                )

                val user2 = Delivery_User3(123456789,
                    "도**, 김**, kr.somecompany, Some Company, +8212345678",
                    "배송중 | 2023-09-15T10:30:00+09:00 | 부산해운대 | 고객님의 상품이 배송 중입니다.(담당사원:이영희 010-1234-5678)",
                    ""
                )


                val user3 = Delivery_User3(987654321,
                    "구**, 이**, kr.anothercompany, Another Company, +8209876543",
                    "배송대기 | 2023-09-14T16:45:00+09:00 | 인천남동 | 고객님의 상품이 배송을 기다리고 있습니다.(담당사원:김철수 010-9876-5432)",
                    ""
                )

                val user4 = Delivery_User3(555555555,
                    "강**, 박**, kr.yetanothercompany, Yet Another Company, +8255555555",
                    "배송완료 | 2023-09-16T08:15:00+09:00 | 대구수성 | 고객님의 상품이 배송완료 되었습니다.(담당사원:최영호 010-5555-5555)",
                    ""
                )

                val user5 = Delivery_User3(111111111,
                    "서**, 전**, kr.someothercompany, Some Other Company, +8211111111",
                    "배송중 | 2023-09-17T14:30:00+09:00 | 경기화성 | 고객님의 상품이 배송 중입니다.(담당사원:이미란 010-1111-1111)",
                    ""
                )

                db.insertDataBeta2(user1)
                db.insertDataBeta2(user2)
                db.insertDataBeta2(user3)
                db.insertDataBeta2(user4)
                db.insertDataBeta2(user5)
                Toast.makeText(this, "데이터가 성공적으로 삽입되었습니다.", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                Toast.makeText(this, "데이터 삽입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }



        }
        addButton6.setOnClickListener {
            Log.d("oiia", "passed")

            if (overlayView == null) {
                overlayView = View(this)
                overlayView?.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                overlayView?.setBackgroundColor(Color.parseColor("#80000000"))
                val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
                rootView.addView(overlayView)
            }


            val sheet = sheettest(this)
            sheet.show(supportFragmentManager,sheet.tag)//자동반투명 어따 팔아먹은거야 시발
        }
        addButton9.setOnClickListener {
            val intent = Intent(this,SubActivity2::class.java)
            startActivity(intent)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////메인 화면 제어 구획//////////////////////////////////////////

    override fun onResume() {
        super.onResume()

        initRecyclerView()
        setupEvents()

    }

    private fun initRecyclerView() {

        adapter = Move_UserAdapter_main(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        adapter.differ.submitList(moveuserList)
        itemTouchSimpleCallback.setOnItemMoveListener(object : ItemTouchSimpleCallback.OnItemMoveListener {
            override fun onItemMove(from: Int, to: Int) {
                // Collections.swap(userList, from, to) 처럼 from, to가 필요하다면 사용
                //Log.d("MainActivity", "from Position : $from, to Position : $to")
            }
        })


        itemTouchHelper.attachToRecyclerView(binding.recyclerview)


    }

    private fun setupEvents() {
        // 아이템 추가 버튼 클릭 시 이벤트(userList에 아이템 추가)
        binding.addButton.setOnClickListener {

            // 추가할 데이터 생성
            val mMoveuser = Move_user(
                moveuserList.size+1, "added user ${moveuserList.size+1}",
                "smurf Cat ${moveuserList.size+1}")


            val newList = adapter.differ.currentList.toMutableList()
            newList.add(mMoveuser)

            adapter.differ.submitList(newList)
            moveuserList.add(mMoveuser)

            // 추가 메시지 출력
            Toast.makeText(this, "${mMoveuser.info1}이 추가되었습니다.", Toast.LENGTH_SHORT).show()

            binding.recyclerview.scrollToPosition(newList.indexOf(mMoveuser))
        }
        binding.addButton7.setOnClickListener {
            adapter.clearAllItems()
            Toast.makeText(this, "리스트 삭제.", Toast.LENGTH_SHORT).show()

        }
        binding.addButton8.setOnClickListener {
            updateList()
            Toast.makeText(this, "리스트 업데이트.", Toast.LENGTH_SHORT).show()

        }
    }



    override fun onPause() {
        super.onPause()
        removeOverlayView()
    }

    fun startupdate(){

        var db  =DBHandler(this)
        val startSet = Array(db.alpha_project_1().size) { db.alpha_project_1()[it] }

        for (insert in startSet) {
            var arr = Array(3) {""}
            arr = db.outputdatasFront(insert.toLong())
            var base = arr[2].toString().split("|")
            val mMoveuser = Move_user(
                moveuserList.size+1, arr[0].toString(), base[0])
            moveuserList.add(mMoveuser)
        }
    }

    fun updateList() {
        var db = DBHandler(this)
        val startSet = Array(db.alpha_project_1().size) { db.alpha_project_1()[it] }

        moveuserList.clear()

        for (insert in startSet) {
            var arr = Array(3) { "" }
            arr = db.outputdatasFront(insert.toLong())
            var base = arr[2].toString().split("|")
            val mMoveuser = Move_user(
                moveuserList.size + 1, arr[0].toString(), base[0]
            )
            moveuserList.add(mMoveuser)
        }

        // 수정된 데이터로 어댑터에 업데이트
        adapter.differ.submitList(moveuserList)
        adapter.notifyDataSetChanged()

        Toast.makeText(this, "리스트 업데이트.", Toast.LENGTH_SHORT).show()
    }

    fun removeOverlayView() {
        overlayView?.let {
            val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            rootView.removeView(it)
            overlayView = null
        }
    }
}