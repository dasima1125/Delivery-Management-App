package com.example.galileo.sheet

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.galileo.R
import com.example.galileo.dataClass.Delivery_User3
import com.example.galileo.db.DBHandler
import com.example.galileo.main.MainActivity
import com.example.galileo.searchCore.searchEngine
import com.example.galileo.subpage.SubActivity2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class sheettest_sub1(context: Context):BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.bottom_sheet_tester,container,false)
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        var insertName: String? = null
        val update = context as SubActivity2

        view?.findViewById<Button>(R.id.button_bottom_sheet)?.setOnClickListener {

            if (insertName != null){
                val editText = view?.findViewById<EditText>(R.id.insertPostNUM)
                val insertNumText = editText?.text.toString()

                val insertNum = try {
                    insertNumText.toLong()
                } catch (e: NumberFormatException) {
                    null
                }

                if (insertNum != null) {

                    val db = DBHandler(requireContext())
                    val result = searchEngine().alp(insertName!!, insertNum.toString())

                    val insert1 = result.get("기본 정보").toString().substring(1, result["기본 정보"].toString().length - 1)
                    var insert3 =result.get("현재 정보").toString().substring(1, result["현재 정보"].toString().length - 1)
                    var insert4 =result.get("과거 정보").toString().substring(1, result["과거 정보"].toString().length - 1)

                    val existingRecord = db.getRecordById(insertNum)

                    if (existingRecord == null && insert1 != "ul") { //id가 비어있을경우
                        val user1 = Delivery_User3(insertNum,insert1,insert3,insert4)

                        db.insertDataBeta2(user1)

                        Toast.makeText(context, "데이터가 성공적으로 삽입되었습니다.", Toast.LENGTH_SHORT).show()
                        //update.updateList()
                        //여기가 나가는 부분
                        dismiss()
                    }
                    else if(insert1 == "ul") { Toast.makeText(context, "송장번호가 맞나요?.", Toast.LENGTH_SHORT).show() }

                    else{ Toast.makeText(context, "데이터가 이미 존재합니다.", Toast.LENGTH_SHORT).show() }

                } else { Toast.makeText(context, "입력된 숫자가 올바르지 않습니다.", Toast.LENGTH_SHORT).show() }
            }
            else { Toast.makeText(context, "택배사 선택을 안한 거 같은데요~", Toast.LENGTH_SHORT).show() }
        }

        view?.findViewById<Button>(R.id.taskBtn1)?.setOnClickListener {
            insertName = "kr.epost"
            Toast.makeText(context, "우체국", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Button>(R.id.taskBtn2)?.setOnClickListener {
            insertName = "kr.cjlogistics"
            Toast.makeText(context, "cj대한통운", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Button>(R.id.taskBtn3)?.setOnClickListener {
            insertName = "kr.hanjin"
            Toast.makeText(context, "한진택배", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Button>(R.id.taskBtn4)?.setOnClickListener {
            insertName = "kr.cupost"
            Toast.makeText(context, "cu편의점 택배", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Button>(R.id.taskBtn5)?.setOnClickListener {
            insertName = "gs25편의점 택배"
            Toast.makeText(context, "gs25편의점 택배", Toast.LENGTH_SHORT).show()
        }
        view?.findViewById<Button>(R.id.taskBtn6)?.setOnClickListener {
            insertName = "kr.logen"
            Toast.makeText(context, "로젠 택배", Toast.LENGTH_SHORT).show()
        }
    }

    //화면 어두워지는거
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = context as SubActivity2 // 메인 접근함
        activity.removeOverlayView()
    }


}