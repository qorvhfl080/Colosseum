package com.nepplus.colosseum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.my_custom_action_bar.*

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        supportActionBar?.let {
            setCustomActionBar()
        }

    }

    abstract fun setupEvents()

    abstract fun setValues()

    fun setCustomActionBar() {

        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        val myToolbar = defaultActionBar.customView.parent as androidx.appcompat.widget.Toolbar
        myToolbar.setContentInsetsAbsolute(0, 0)

        backBtn.setOnClickListener {
            finish()
        }
        
        notificationBtn.setOnClickListener {
            Toast.makeText(mContext, "알림을 보러 갑니다.", Toast.LENGTH_SHORT).show()

            val myIntent = Intent(mContext, NotificationListActivity::class.java)
            startActivity(myIntent)

        }

    }

}