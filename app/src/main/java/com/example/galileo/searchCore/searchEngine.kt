package com.example.galileo.searchCore

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



class searchEngine {
    fun alp(carrierId: String, trackId: String): Map<String, List<String>> {
        // 코루틴을 사용하여 비동기 작업 시작

        val output = HashMap<String, List<String>>()
        runBlocking {
            launch(Dispatchers.IO) {
                val API_BASE_URL = "https://apis.tracker.delivery/carriers/"
                val apiUrl2  = "$API_BASE_URL$carrierId/tracks/$trackId"

                //val apiUrl1  = "https://apis.tracker.delivery/carriers/kr.cjlogistics/tracks/680490177973"

                try {

                    val url = URL(apiUrl2)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    when (carrierId) {
                        "kr.cjlogistics" -> {

                            if (connection.responseCode == HttpURLConnection.HTTP_OK) {


                                val parser      = JSONParser()
                                val inputStream = connection.inputStream
                                val jsonObject  = parser.parse(InputStreamReader(inputStream)) as JSONObject

                                //필터 제작

                                //기본 정보
                                val basicValue1_1 = jsonObject["from"]    as JSONObject
                                val basicValue1_2  = jsonObject["to"]      as JSONObject
                                val basicValue1_3 = jsonObject["carrier"] as JSONObject

                                val name1_1 = basicValue1_1["name"].toString()
                                val name1_2 = basicValue1_2["name"].toString()
                                val name1_3 = basicValue1_3["id"]  .toString()
                                val name1_4 = basicValue1_3["name"].toString()
                                val name1_5 = basicValue1_3["tel"] .toString()

                                val values0 = listOf(name1_1, name1_2, name1_3, name1_4, name1_5)
                                output["기본 정보"] = values0


                                //현재 상태
                                val basicValue2 = jsonObject["state"] as JSONObject
                                val name2_1: String = basicValue2["text"].toString()

                                val values1 = listOf(name2_1)
                                output["현재 상태"] = values1


                                //상태 파악용 알파 리소스
                                val basicValue3 = jsonObject["progresses"] as JSONArray

                                val values2_progressesAll: MutableList<String> = ArrayList()

                                for (progressObj in basicValue3) {
                                    val progress = progressObj as JSONObject
                                    val status = progress["status"] as JSONObject
                                    val text = status["text"] as String
                                    val time = progress["time"] as String
                                    val location = progress["location"] as JSONObject
                                    val locationName = location["name"] as String
                                    val description = progress["description"] as String
                                    val progressLine = "$text | $time | $locationName | $description"
                                    values2_progressesAll.add(progressLine) // 결과를 스트링 리스트에 추가
                                }

                                val values2 = listOf(values2_progressesAll.last())
                                output["현재 정보"] = values2
                                output["과거 정보"] = values2_progressesAll


                            }
                            else{
                                //Log.d("oiia", "접속 에러: 존재하지않는 송장번호")
                            }

                        }
                        //val apiUrl1  = "https://apis.tracker.delivery/carriers/kr.epost/tracks/6099732895958"
                        "kr.epost" -> {
                            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                                Log.d("oiia", "접근성공 추출 준비")

                                val parser      = JSONParser()
                                val inputStream = connection.inputStream
                                val jsonObject  = parser.parse(InputStreamReader(inputStream)) as JSONObject

                                var test = jsonObject.toString()

                                //기본 정보
                                val basicValue1_1 = jsonObject["from"]    as JSONObject
                                val basicValue1_2  = jsonObject["to"]      as JSONObject
                                val basicValue1_3 = jsonObject["carrier"] as JSONObject

                                val name1_1 = basicValue1_1["name"].toString()
                                val name1_2 = basicValue1_2["name"].toString()
                                val name1_3 = basicValue1_3["id"]  .toString()
                                val name1_4 = basicValue1_3["name"].toString()
                                val name1_5 = basicValue1_3["tel"] .toString()

                                val values0 = listOf(name1_1, name1_2, name1_3, name1_4, name1_5)
                                output["기본 정보"] = values0


                                //현재 상태
                                val basicValue2 = jsonObject["state"] as JSONObject
                                val name2_1: String = basicValue2["text"].toString()

                                var inserter = name2_1.substring(0, name2_1.length - 1).split(" ")[0]


                                val values1 = listOf(inserter)
                                output["현재 상태"] = values1

                                //상태 파악용 알파 리소스
                                val basicValue3 = jsonObject["progresses"] as JSONArray

                                val values2_progressesAll: MutableList<String> = ArrayList()

                                for (progressObj in basicValue3) {
                                    val progress = progressObj as JSONObject
                                    val status = progress["status"] as JSONObject

                                    var filterInfo1 = " "
                                    var filterInfo2 = " "
                                    var filterInfo3 = " "

                                    //첫번째 정제 값
                                    val info1_1 = status["id"] as String
                                    val info1_2 = status["text"] as String
                                    when (info1_1) {
                                        "information_received" -> { filterInfo1 = "집화처리" }
                                        "in_transit" -> {
                                            // text가 "Case2"일 때 실행할 코드
                                            if (info1_2 == "발송")
                                                filterInfo1 = "간선상차"
                                            else if (info1_2 =="도착")
                                                filterInfo1 = "간선하차"
                                        }
                                        "out_for_delivery" -> { filterInfo1 = "배송출발" }
                                        "delivered"        -> { filterInfo1 = "배송완료" }
                                        else -> { filterInfo1 = "Error detected" }
                                    }

                                    val time = progress["time"] as String
                                    //두번째 정제 값
                                    val location = progress["location"] as JSONObject
                                    val locationName = location["name"] as String
                                    filterInfo2 =locationName.split(" ")[0]

                                    //두번째 정제 값
                                    val description = progress["description"] as String
                                    when (description.split(" ")[0]) {
                                        "접수" -> { filterInfo3 = "보내시는 고객님으로부터 상품을 인수받았습니다" }
                                        "발송" ->{
                                            filterInfo3 = "물류터미널로 상품이 이동중입니다."

                                            if (filterInfo2.contains("물류")) { filterInfo3 = "우편집중국으로 상품이 이동중입니다." }
                                            if (filterInfo2.contains("우편집중국")) { filterInfo3 = "배송지역으로 상품이 이동중입니다." }
                                        }
                                        "도착" ->{
                                            if (filterInfo2.contains("물류센터")) { filterInfo3 = "상품이 물류센터에 도착하였습니다" }
                                            if (filterInfo2.contains("우편집중국")) { filterInfo3 = "상품이 우편집중국에 도착하였습니다" }
                                            if (filterInfo2.contains("우체국")) { filterInfo3 = "고객님의 상품이 배송지에 도착하였습니다" }
                                        }
                                        "배달준비" ->{ filterInfo3 = "고객님의 상품을 배송할 예정입니다"   }
                                        "배달완료" ->{ filterInfo3 = "고객님의 상품이 배송완료 되었습니다" }
                                    }

                                    val progressLine = "$filterInfo1 | $time | $filterInfo2 | $filterInfo3"
                                    values2_progressesAll.add(progressLine) // 결과를 스트링 리스트 에 추가
                                }
                                val values2 = listOf(values2_progressesAll.last())
                                output["현재 정보"] = values2
                                output["과거 정보"] = values2_progressesAll
                            }
                            else{
                                Log.d("oiia", "에러?")
                            }

                        }
                        // 다른 택배회사에 대한 처리도 추가할 수 있습니다.
                        else -> {
                            //Log.d("oiia", "지원하지 않는 택배회사입니다: $carrierId")
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }

        return output
    }
    fun reSearch(){

    }

}