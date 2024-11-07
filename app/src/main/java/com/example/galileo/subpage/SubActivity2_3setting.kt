package com.example.galileo.subpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.galileo.R
import com.example.galileo.adpter.Move_UserAdapter_sub2_postlist
import com.example.galileo.dataClass.Delivery_User3
import com.example.galileo.dataClass.Move_user
import com.example.galileo.dataClass.Post_list_01
import com.example.galileo.databinding.ActivitySub2ListBinding
import com.example.galileo.db.DBHandler
import com.example.galileo.updater.updatecontrol

class SubActivity2_3setting : AppCompatActivity() {

    private var moveuserList = mutableListOf<Post_list_01>()
    private lateinit var adapter: Move_UserAdapter_sub2_postlist
    private lateinit var binding: ActivitySub2ListBinding

    private lateinit var addButton0: ImageButton
    private lateinit var addButton1: Button
    private lateinit var addButton2: Button
    private lateinit var addButton3: Button
    private lateinit var addButton4: Button
    private lateinit var addButton5: Button
    private lateinit var addButton6: Button
    private lateinit var addButton7: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2_setting)

        addButton0 = findViewById(R.id.activity_sub2_setting_backArrow)
        addButton1 = findViewById(R.id.setting_listupdate)
        addButton2 = findViewById(R.id.setting_listblind)
        addButton3 = findViewById(R.id.setting_defaulttest)
        addButton4 = findViewById(R.id.setting_testdata)
        addButton5 = findViewById(R.id.setting_dbflush)
        addButton6 = findViewById(R.id.setting_serverupdate)
        addButton7 = findViewById(R.id.setting_bulkcreate)

        addButton0.setOnClickListener {
            finish()
        }

        addButton1.setOnClickListener {
            var updatecontrol = updatecontrol(this)
            Toast.makeText(this, "당김으로 구현할예정이라 잠굼.", Toast.LENGTH_SHORT).show()
        }
        addButton2.setOnClickListener {
            Toast.makeText(this, "테스트존 구현만 할예정.", Toast.LENGTH_SHORT).show()
        }
        addButton3.setOnClickListener {
            var db = DBHandler(this)

            try {
                val user1 = Delivery_User3(
                    680490177974,
                    "시**, 황**, kr.cjlogistics, CJ Logistics, +8215881255",
                    "배송완료 | 2023-09-13T14:57:55+09:00 | 서울문래 | 고객님의 상품이 배송완료 되었습니다.(담당사원:한창훈 010-3111-7259)",
                    ""
                )

                val user2 = Delivery_User3(
                    123456789,
                    "도**, 김**, kr.somecompany, Some Company, +8212345678",
                    "배송중 | 2023-09-15T10:30:00+09:00 | 부산해운대 | 고객님의 상품이 배송 중입니다.(담당사원:이영희 010-1234-5678)",
                    ""
                )


                val user3 = Delivery_User3(
                    987654321,
                    "구**, 이**, kr.anothercompany, Another Company, +8209876543",
                    "배송대기 | 2023-09-14T16:45:00+09:00 | 인천남동 | 고객님의 상품이 배송을 기다리고 있습니다.(담당사원:김철수 010-9876-5432)",
                    ""
                )

                val user4 = Delivery_User3(
                    555555555,
                    "강**, 박**, kr.yetanothercompany, Yet Another Company, +8255555555",
                    "배송완료 | 2023-09-16T08:15:00+09:00 | 대구수성 | 고객님의 상품이 배송완료 되었습니다.(담당사원:최영호 010-5555-5555)",
                    ""
                )

                val user5 = Delivery_User3(
                    111111111,
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
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "데이터 삽입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        addButton4.setOnClickListener {
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
                } else {  //아이디가 존재할경우
                    Toast.makeText(this, "이미 해당 아이디가 데이터베이스에 존재합니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "데이터 삽입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()

            }
            db.close()
        }
        addButton5.setOnClickListener {
            var db = DBHandler(this)
            db.flusherbeta()
            db.close()
            Toast.makeText(this, "초기화 완료", Toast.LENGTH_SHORT).show()
        }
        addButton6.setOnClickListener {
            var db = DBHandler(this)
            db.autoUpdater()
            db.close()
            Toast.makeText(this, "서버 데이터 업데이트.", Toast.LENGTH_SHORT).show()
        }
        addButton7.setOnClickListener {

            var db = DBHandler(this)
            var size = db.sizecounter()
            Log.d("bulk", "$size ")

            val idToInsert = size.toLong()
            Log.d("bulk", idToInsert.toString())

            val user1 = Delivery_User3(
                idToInsert+1,
                "bulkdata",
                "huh?",
                "huh?????????"
            )
            db.insertDataBeta2(user1)
            Toast.makeText(this, "데이터가 성공적으로 삽입되었습니다.", Toast.LENGTH_SHORT).show()

        }
    }
}