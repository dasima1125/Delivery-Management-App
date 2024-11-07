package com.example.galileo.subpage


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.galileo.R
import com.example.galileo.adpter.Move_UserAdapter_sub2_postlist
import com.example.galileo.alarm.NotificationHelper
import com.example.galileo.databinding.ActivitySub2Binding
import com.example.galileo.main.MainActivity
import com.example.galileo.sheet.sheettest_sub1
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class SubActivity2: AppCompatActivity() {

    private lateinit var adapter: Move_UserAdapter_sub2_postlist
    private var overlayView: View? = null

    private lateinit var addButton_searchlist  : Button
    private lateinit var addButton_backArrow  : Button
    private lateinit var addButton_listHome : Button
    private lateinit var addButton_setting : Button
    private lateinit var addButton_widget : Button
    private lateinit var addButton_notice: ImageButton
    private lateinit var binding: ActivitySub2Binding

    lateinit var notificationHelper: NotificationHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub2)

        addButton_searchlist = findViewById(R.id.search_bar_btn)
        addButton_listHome   = findViewById(R.id.list_bar_btn)
        addButton_backArrow  = findViewById(R.id.backspace_totest_main)
        addButton_notice     = findViewById(R.id.main_notification_btn)
        addButton_setting    = findViewById(R.id.setting_btn)
        addButton_widget     = findViewById(R.id.wigget_btn)

        notificationHelper = NotificationHelper(this)
        notificationHelper.createNotifChannel()




        addButton_notice.setOnClickListener {
            Log.d("oiia11", "푸시알람: ")
            notificationHelper.showNotification("hello","blyat")


        }
        addButton_searchlist.setOnClickListener {
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
            val sheet = sheettest_sub1(this)
            sheet.show(supportFragmentManager,sheet.tag)
        }
        addButton_listHome.setOnClickListener {
            val intent = Intent(this,SubActivity2_2list::class.java)
            startActivity(intent)

        }
        addButton_backArrow.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        addButton_setting.setOnClickListener {
            val intent = Intent(this,SubActivity2_3setting::class.java)
            startActivity(intent)
        }
        addButton_widget.setOnClickListener {
            val intent = Intent(this,SubActivity2_4widget::class.java)
            startActivity(intent)

        }

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()

    }

    override fun onPause() {
        super.onPause()
        removeOverlayView()
    }

    fun removeOverlayView() {
        overlayView?.let {
            val rootView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            rootView.removeView(it)
            overlayView = null
        }
    }

    private fun initRecyclerView(){
        adapter = Move_UserAdapter_sub2_postlist(this)
    }



}