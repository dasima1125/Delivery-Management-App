package com.example.galileo.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.galileo.R
import com.example.galileo.dataClass.Move_user_sub1

class Move_UserAdapter_sub1 (private val mContext: Context) : RecyclerView.Adapter<Move_UserAdapter_sub1.ViewHolder>() {


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val userNameText1: TextView = itemView.findViewById(R.id.user_name_text1)
        private val userNameText2: TextView = itemView.findViewById(R.id.user_name_text2)
        private val userNameText3: TextView = itemView.findViewById(R.id.user_name_text3)



        fun bind(Move_user: Move_user_sub1) {
            userNameText1.text = Move_user.id
            userNameText2.text = Move_user.info1
            userNameText3.text = Move_user.info2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Move_UserAdapter_sub1.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount() =  differ.currentList.size

    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<Move_user_sub1>() {
        override fun areItemsTheSame(oldItem: Move_user_sub1, newItem: Move_user_sub1): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Move_user_sub1, newItem: Move_user_sub1): Boolean {
            return oldItem == newItem
        }
    }

    ///sub 1 line
    val differ = AsyncListDiffer(this, differCallback)
}