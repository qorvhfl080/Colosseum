package com.nepplus.colosseum.fcm

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("push", "수신이벤트")
        
        val title = p0.notification!!.title
        val body = p0.notification!!.body

        val myHandler = Handler(Looper.getMainLooper())
        myHandler.post {
            Toast.makeText(applicationContext, "${body}", Toast.LENGTH_SHORT).show()
        }
        
    }

}