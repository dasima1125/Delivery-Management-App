package com.example.galileo.updater

import android.content.Context
import com.example.galileo.dataClass.Move_user
import com.example.galileo.db.DBHandler

class updatecontrol(private val context: Context) {

    private var moveuserList = mutableListOf<Move_user>()

    fun updateList() {
        var db = DBHandler(context)
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
    }
}
