package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        checkEmailBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplCheck("EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler)

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