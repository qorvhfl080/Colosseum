package com.nepplus.colosseum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nepplus.colosseum.utils.ContextUtil

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        var myIntent: Intent
        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({

            //        자동 로그인 여부 판단
            if (ContextUtil.getAutoLogin(mContext) && ContextUtil.getToken(mContext) != "") {
                myIntent = Intent(mContext, MainActivity::class.java)
            } else {
                myIntent = Intent(mContext, SignInActivity::class.java)
            }

            startActivity(myIntent)
            finish()

        }, 2500)




    }
}