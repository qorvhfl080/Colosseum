package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        emailEdt.addTextChangedListener {

//            it 변수 활용 : 입력된 내용 파악
            Log.d("server", it.toString())

            checkEmailResultTxt.text = "중복 검사를 해주세요"

        }

        checkEmailBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplCheck("EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler {

                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")



                    if (code == 200) {

                        runOnUiThread {
                            checkEmailResultTxt.text = "사용해도 좋은은 이메일입니다."
                       }

                    } else {

                        runOnUiThread {
                            checkEmailResultTxt.text = "중복된 이메일입니다."
                        }

                    }

                }
            })

        }

        signUpBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()
            val inputNickname = nicknameEdt.text.toString()

            ServerUtil.putRequestSingUp(
                inputEmail,
                inputPw,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        val code = jsonObj.getInt("code")

                        if (code == 200) {

                            runOnUiThread {
                                Toast.makeText(mContext, "${jsonObj.getString("message")}", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                        } else {

                            runOnUiThread {
                                Toast.makeText(mContext, "${jsonObj.getString("message")}", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                })

        }

    }

    override fun setValues() {

    }
}