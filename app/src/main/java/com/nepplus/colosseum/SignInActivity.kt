package com.nepplus.colosseum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nepplus.colosseum.utils.ContextUtil
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject

class SignInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {
        
//        자동로그인 체크박스의 값이 바뀔때마다
        autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

            Log.d("check", isChecked.toString())

            ContextUtil.setAutoLogin(mContext, isChecked)

        }

        signInBtn.setOnClickListener {

            val inputId = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

            ServerUtil.postRequestSignIn(inputId, inputPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")
                    Log.d("server", code.toString())

                    if (code == 200) {

//                        val dataObj = jsonObj.getJSONObject("data")
//                        val userObj = dataObj.getJSONObject("user")
//                        val nickname = userObj.getString("nick_name")
//
//                        runOnUiThread {
//                            Toast.makeText(mContext, "${nickname}님 ${jsonObj.getString("message")}", Toast.LENGTH_SHORT).show()
//                        }

//                        서버가 내려주는 토큰값을 기기에 저장 (ContextUtil 사용)
                        val dataObj = jsonObj.getJSONObject("data")
                        val token = dataObj.getString("token")

                        ContextUtil.setToken(mContext, token)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()

                    } else {

                        runOnUiThread {
                            Toast.makeText(mContext, "${jsonObj.getString("message")}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            })

        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

//        저장된 자동로그인 여부를 받아내서 체크박스에 반영
        autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }
}