package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.nepplus.colosseum.databinding.ActivityMainBinding
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {

        signInBtn.setOnClickListener {

            val inputId = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

            ServerUtil.postRequestSignIn(inputId, inputPw)

        }

        signUpBtn.setOnClickListener {



        }

    }

    override fun setValues() {

    }
}