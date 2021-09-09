package com.nepplus.colosseum

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.nepplus.colosseum.utils.ContextUtil
import com.nepplus.colosseum.utils.GlobalData
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        logoutBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, position ->

                ContextUtil.setToken(mContext, "")

                GlobalData.loginUser = null

                val myIntent = Intent(mContext, SignInActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)


            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

        quitBtn.setOnClickListener {



        }

    }

    override fun setValues() {

    }
}