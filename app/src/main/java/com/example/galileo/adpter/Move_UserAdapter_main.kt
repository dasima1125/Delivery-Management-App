package com.example.galileo.adpter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galileo.R
import com.example.galileo.dataClass.Move_user
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.galileo.db.DBHandler
import com.example.galileo.subpage.SubActivity1


class Move_UserAdapter_main(private val mContext: Context) : RecyclerView.Adapter<Move_UserAdapter_main.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val swipeView: LinearLayout = itemView.findViewById(R.id.swipe_view)
        private val userImage: ImageView = itemView.findViewById(R.id.user_image)
        private val userNameText: TextView = itemView.findViewById(R.id.user_name_text)
        private val userNameText2: TextView = itemView.findViewById(R.id.user_name_text2)
        private val removeTextView: TextView = itemView.findViewById(R.id.remove_text_view)
        //이부분이 핵심
        fun bind(moveuser: Move_user) {

            // 재사용 시 Swipe가 되어있다면 Swipe 원상복구
            swipeView.translationX = 0f

            userNameText.text = moveuser.info1
            userNameText2.text = moveuser.info2

            removeTextView.setOnClickListener {
                val list = arrayListOf<Move_user>()
                list.addAll(differ.currentList)
                var db  =DBHandler(swipeView.context)
                Log.d("qauso", "${moveuser.info1} ")
                db.slicer(moveuser.info1.toLong())
                list.remove(moveuser)
                differ.submitList(list)
            }


            swipeView.setOnClickListener{
                val list = arrayListOf<Move_user>()
                val intent = Intent(swipeView.context, SubActivity1::class.java)

                intent.putExtra("key",moveuser.info1);
                swipeView.context.startActivity(intent)

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 수정 후
        val user = differ.currentList[position]
        holder.bind(user)
    }
    override fun getItemCount() =  differ.currentList.size

    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<Move_user>() {
        override fun areItemsTheSame(oldItem: Move_user, newItem: Move_user): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Move_user, newItem: Move_user): Boolean {
            return oldItem == newItem
        }
    }

    fun clearAllItems() {
        val list = mutableListOf<Move_user>()
        differ.submitList(list)
    }
    val differ       = AsyncListDiffer(this, differCallback)
}