package com.jefftorcato.saudadeadmin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jefftorcato.saudadeadmin.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startAppIntent = Intent(this,MainActivity::class.java)
        Log.i("Message","Here")
        startActivity(startAppIntent)
        finish()
    }
}