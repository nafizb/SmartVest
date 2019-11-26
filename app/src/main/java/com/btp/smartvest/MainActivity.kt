package com.btp.smartvest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onStartClick(view: View) {
        val intent = Intent(this, MonitorActivity::class.java)
        Log.e("clicked", "startIntent")
        startActivity(intent)
        finish()
    }
}
