package com.example.galileo.adpter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galileo.R
import com.example.galileo.dataClass.Move_user
import com.example.galileo.dataClass.Move_user_sub1
import com.example.galileo.dataClass.Post_list_01
import com.example.galileo.db.DBHandler
import com.example.galileo.subpage.SubActivity1

class Move_UserAdapter_sub2_postlist(private val mContext: Context) :RecyclerView.Adapter<Move_UserAdapter_sub2_postlist.ViewHolder>() {


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val swipeView: LinearLayout = itemView.findViewById(R.id.swipe_viewlist)

        private val userNameText1: TextView = itemView.findViewById(R.id.status_listnum1)
        private val userNameText2: TextView = itemView.findViewById(R.id.status_listnum2)
        private val userNameText3: TextView = itemView.findViewById(R.id.status_listnum_total)

        private val removeZone: TextView = itemView.findViewById(R.id.remove_slide_list)


        fun bind(Move_user: Post_list_01) {

            swipeView.translationX = 0f

            userNameText1.text = Move_user.info1
            userNameText2.text = Move_user.info2
            userNameText3.text = Move_user.info3

            removeZone.setOnClickListener{


                val list = arrayListOf<Post_list_01>()
                list.addAll(differ.currentList)
                var db  = DBHandler(swipeView.context)
                Log.d("qauso", "${Move_user.info1} ")
                db.slicer(Move_user.info1.toLong())
                list.remove(Move_user)
                differ.submitList(list)
            }

            swipeView.setOnClickListener{
                val list = arrayListOf<Move_user>()
                val intent = Intent(swipeView.context, SubActivity1::class.java)

                intent.putExtra("key",Move_user.info1);
                swipeView.context.startActivity(intent)

            }



        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Move_UserAdapter_sub2_postlist.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_sub2_postlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount() =  differ.currentList.size

    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<Post_list_01>() {
        override fun areItemsTheSame(oldItem: Post_list_01, newItem: Post_list_01): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post_list_01, newItem: Post_list_01): Boolean {
            return oldItem == newItem
        }
    }

    ///sub 1 line
    val differ = AsyncListDiffer(this, differCallback)
}