package com.example.galileo.db


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.galileo.alarm.NotificationHelper
import com.example.galileo.dataClass.Delivery_User
import com.example.galileo.dataClass.Delivery_User2
import com.example.galileo.dataClass.Delivery_User3
import com.example.galileo.dataClass.widget_test
import com.example.galileo.searchCore.searchEngine


val DATABASE_NAME ="DemoDB"
val TABLE_NAME = "User"
val COL_ID    = "id"
val COL_DEMO1 = "demo1"
val COL_DEMO2 = "demo2"
val COL_DEMO3 = "demo3"




class DBHandler(var context: Context) :SQLiteOpenHelper(context ,DATABASE_NAME,null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DEMO1 + " VARCHAR(256), " +
                COL_DEMO2 + " VARCHAR(256), " +
                COL_DEMO3 + " VARCHAR(256));"


        val createAlphaTable = "CREATE TABLE alpha (" +
                "id INTEGER PRIMARY KEY, " +
                "baseInfo VARCHAR(255), " +
                "nowInfo VARCHAR(255)," +
                "totalInfo VARCHAR(255));"

        val createAlphawigdetTable = "CREATE TABLE alphawidget (" +
                "id INTEGER PRIMARY KEY, " +
                "baseInfo VARCHAR(255));"

        db?.execSQL(createAlphaTable)
        db?.execSQL(createAlphawigdetTable)
        db?.execSQL(createTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(user: Delivery_User) {
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_DEMO1, user.demo1)
        cv.put(COL_DEMO2, user.demo2)
        cv.put(COL_DEMO3, user.demo3)
        var result = db.insert(TABLE_NAME, null, cv)

    }

    fun insertDataBeta2(user: Delivery_User3) {
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put("id", user.id)
        cv.put("baseInfo", user.baseInfo)
        cv.put("nowInfo", user.nowInfo)
        cv.put("totalInfo", user.totalInfo)


        var result = db.insert("alpha", null, cv)
    }
    fun insertDatawidget(user:widget_test){
        var db = this.writableDatabase
        var cv =ContentValues()
        cv.put("id",user.id)

        db.insert("alphawidget",null,cv)
    }
    fun readDatawidget(){

    }


    @SuppressLint("Range")
    fun soloReader(i: Long):String {
        val db = this.readableDatabase
        val query = "SELECT * FROM alpha where id = $i"
        val result = db.rawQuery(query, null)
        var resultout = ""

        if (result.moveToFirst()) {
            //Log.i("oiia", result.getLong(result.getColumnIndex("id")).toString())
            //Log.i("oiia", result.getString(result.getColumnIndex("baseInfo")).toString())
            //Log.i("oiia", result.getString(result.getColumnIndex("nowInfo")).toString())
            //Log.i("oiia", result.getString(result.getColumnIndex("totalInfo")).toString())

            resultout = result.getString(result.getColumnIndex("totalInfo")).toString()
        } else {
            Log.i("oiia", "해당 ID의 레코드를 찾을 수 없습니다.")
        }
        result.close()
        db.close()

        return resultout
    }

    fun flusherbeta() {
        val db = writableDatabase
        db.execSQL("DELETE FROM alpha") // 테이블 내의 모든 데이터 삭제
        db.close()

    }

    fun overwriter(user: Delivery_User3) {
        //db id 에 할당된 행의 모든값을 가져옴
        val db = writableDatabase
        val user1 = Delivery_User3(
            680490177974,
            "시**, 황**, kr.cjlogistics, CJ Logistics, +8215881255 일줄알았지?",
            "배송완료 | 2023-09-13T14:57:55+09:00 | 서울문래 | 고객님의 상품이 배송완료 되었습니다.(담당사원:한창훈 010-3111-7259) 일줄알았지??",
            "[집화처리 | 2023-09-12T17:21:30+09:00 | 서울용두중앙 | 보내시는 고객님으로부터 상품을 인수받았습니다, 간선상차 | 2023-09-12T18:19:02+09:00 | 동대문 | 물류터미널로 상품이 이동중입니다., 간선상차 | 2023-09-12T21:05:02+09:00 | 곤지암Hub | 배송지역으로 상품이 이동중입니다., 간선하차 | 2023-09-13T07:11:12+09:00 | 영등포A | 고객님의 상품이 배송지에 도착하였습니다.(배송예정:한창훈 010-3111-7259), 배송출발 | 2023-09-13T12:46:05+09:00 | 서울문래 | 고객님의 상품을 배송할 예정입니다.(14~16시)(배송담당:한창훈 010-3111-7259), 배송완료 | 2023-09-13T14:57:55+09:00 | 서울문래 | 고객님의 상품이 배송완료 되었습니다.(담당사원:한창훈 010-3111-7259)]"
        )
        Log.d("oiia", user1.id.toString() + "에베ㅔ베베")
        slicer(user1.id)
        insertDataBeta2(user1)
    }

    //이거 왜만든거지?
    @SuppressLint("Range")
    fun getRecordById(id: Long): Delivery_User3? {
        val db = this.readableDatabase
        val query = "SELECT * FROM alpha WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        var user: Delivery_User3? = null

        if (cursor.moveToFirst()) {
            val alphaId = cursor.getLong(cursor.getColumnIndex("id"))
            val baseInfo = cursor.getString(cursor.getColumnIndex("baseInfo"))
            val nowInfo = cursor.getString(cursor.getColumnIndex("nowInfo"))
            val totalInfo = cursor.getString(cursor.getColumnIndex("totalInfo"))

            user = Delivery_User3(alphaId, baseInfo, nowInfo, totalInfo)
        }

        cursor.close()
        db.close()

        return user
    }

    fun slicer(i: Long) {
        val db = this.writableDatabase

        // 데이터 삭제 쿼리 실행
        val tableName = "alpha" // 테이블 이름을 여기에 입력하세요.
        val whereClause = "id = ?" // id 필드에 대한 조건을 여기에 입력하세요.
        val whereArgs = arrayOf(i.toString()) // 삭제할 아이디를 문자열로 변환하여 입력하세요.

        val deletedRows = db.delete(tableName, whereClause, whereArgs)

        if (deletedRows > 0) {
            Log.d("oiia", "slicer: success ")

        } else {
            Log.d("oiia", "slicer: fall ")

        }
        db.close()
    }
    fun sizecounter(): Int {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM alpha"
        val cursor = db.rawQuery(query, null)
        var count = 0
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
            cursor.close()
        }
        db.close()

        return count
    }

    @SuppressLint("Range")
    fun outputdatasFront(i: Long): Array<String> {
        val db = this.readableDatabase

        val query = "SELECT * FROM alpha where id =" + i.toString()
        val cursor = db.rawQuery(query, null)

        val alphaId: String
        val baseInfo: String
        val nowInfo: String
        val totalInfo: String

        if (cursor.moveToFirst()) {
            alphaId = cursor.getLong(cursor.getColumnIndex("id")).toString()
            baseInfo = cursor.getString(cursor.getColumnIndex("baseInfo"))
            nowInfo = cursor.getString(cursor.getColumnIndex("nowInfo"))
            totalInfo = cursor.getString(cursor.getColumnIndex("totalInfo"))
        } else {
            alphaId = "데이터 없음"
            baseInfo = "데이터 없음"
            nowInfo = "데이터 없음"
            totalInfo = "데이터 없음"
        }

        cursor.close()
        db.close()

        val output = arrayOf(alphaId, baseInfo, nowInfo, totalInfo)
        return output
    }

    @SuppressLint("Range")
    fun alpha_project_1(): Array<String> {
        val db = readableDatabase
        val query = "SELECT * FROM alpha"
        val cursor = db.rawQuery(query, null)

        val result = mutableListOf<String>()

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex("id"))
            result.add(id)
        }

        cursor.close()
        db.close()

        return result.toTypedArray()
    }

    @SuppressLint("Range")
    fun widgetBroadcast(): Array<String> {
        val result = mutableListOf<String>()
        val db = readableDatabase

        // 쿼리 실행 (LIMIT 3을 사용하여 상위 3개만 가져오기)
        val query = "SELECT * FROM alpha"
        val cursor = db.rawQuery(query, null)

        try {
            // cursor에서 데이터를 읽어 로그로 출력
            var counter = 1
            while (cursor.moveToNext()) {
                val alphaId = cursor.getLong(cursor.getColumnIndex("id")).toString()
                val baseInfo = cursor.getString(cursor.getColumnIndex("baseInfo"))
                val nowInfo = cursor.getString(cursor.getColumnIndex("nowInfo"))
                if (nowInfo.contains("배송완료") && counter < 4) {

                    val insert = "$alphaId^$baseInfo^$nowInfo"
                    result.add(insert)
                    counter += 1
                }
            }
            while (counter < 4) {
                val insert = "null"
                result.add(insert)
                counter += 1
            }
        } finally {
            // cursor와 데이터베이스를 닫아줌
            cursor.close()
            db.close()
        }
        return result.toTypedArray()
    }

    @SuppressLint("Range")
    fun autoUpdater(){
        val db = readableDatabase
        val query = "SELECT * FROM alpha"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val search1 = cursor.getString(cursor.getColumnIndex("id"))
            val search2 = cursor.getString(cursor.getColumnIndex("baseInfo"))

            try {
                val changeoutput = searchEngine().alp(search2.split(",")[2].trim(), search1)
                val handsUp = changeoutput

                if (!changeoutput.values.flatten().isEmpty()) {
                    Log.d("oiia", "대상 값 감지 ")
                    if (soloReader(search1.toLong()) == changeoutput["과거 정보"].toString().replace("[", "").replace("]", "")) {
                        Log.d("oiia", "값 동일 ")
                        Log.d("oiia", "바꿀 필요가 없음")
                    } else if (soloReader(search1.toLong()) != changeoutput["과거 정보"].toString().replace("[", "").replace("]", "")) {
                        Log.d("oiia", "전환 실행")
                        swapUpdate(search1.toLong(), handsUp)
                    }
                }
                else{
                    Log.d("oiia", "테스트 데이터")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("oiia", "벌크 데이터")
            }

        }

    }
    fun swapUpdate(id: Long, updateValues:Map<String, List<String>>){

        var notificationHelper: NotificationHelper = NotificationHelper(context)
        notificationHelper.createNotifChannel()
        
        val db = writableDatabase

        //showRecordById(id, db)

        val updateQuery = "UPDATE alpha SET baseInfo = ?, nowInfo = ?, totalInfo = ? WHERE id = ?"

        val baseInfo = updateValues["기본 정보"].toString().substring(1, updateValues["기본 정보"].toString().length - 1)
        val nowInfo = updateValues["현재 정보"].toString().substring(1, updateValues["현재 정보"].toString().length - 1)
        val totalInfo = updateValues["과거 정보"].toString().substring(1, updateValues["과거 정보"].toString().length - 1)
        Log.d("showRecordById", nowInfo)
        if (nowInfo.split("|")[0].trim() =="배송완료"){
            notificationHelper.showNotification("배송 완료!","고객님의 상품이 도착했어요")
        }

        if (nowInfo.split("|")[0].trim() =="배송출발"){
            notificationHelper.showNotification("배송 출발!","고객님의 곧 도착해요")
        }

        if (nowInfo.split("|")[0].trim() =="도착" || nowInfo.split("|")[0].trim().contains("배송지")){
            notificationHelper.showNotification("우체국 도착!","택배가 배송지에 도착했습니다")
        }

        db.execSQL(updateQuery, arrayOf(baseInfo, nowInfo, totalInfo, id))

        showRecordById(id, db)

    }
    fun showRecordById(id: Long, db: SQLiteDatabase) {
        val query = "SELECT * FROM alpha WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            val columns = cursor.columnNames
            do {
                for (column in columns) {
                    val columnIndex = cursor.getColumnIndex(column)
                    val columnValue = cursor.getString(columnIndex)
                    Log.i("showRecordById", "2:  $column: $columnValue")

                }

            } while (cursor.moveToNext())
        } else {
            Log.i("showRecordById", "해당 ID의 레코드를 찾을 수 없습니다.")
        }

        cursor.close()
    }
}