package com.btp.smartvest

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.Nullable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_monitor.*
import java.util.*

class MonitorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)

        /*
        btn_anim.setOnClickListener {
            tvThermometer.setValueAndStartAnim(getRandomValue())
        }
        btn_operate.setOnClickListener {
            tvThermometer.setCurValue(getRandomValue())
        }
        */

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("sensor/temp")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Float::class.java)
                Log.d("Sensor Temp", "Value is: $value")

                if (value != null) {
                    val temp = value.toFloat()
                        val valueCorrected = (-0.0024 * Math.pow(temp.toDouble(), 3.0) + 0.1896 * Math.pow(
                            temp.toDouble(),
                            2.0
                        ) - 2.3227 * temp - 21.6311).toFloat() -2;
                    tvThermometer.setValueAndStartAnim(valueCorrected)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Sensor Temp", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.monitor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_timeline) {
            val intent = Intent(this, TimelineActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getRandomValue(): Float {
        val value = Random().nextFloat() * 7 + 35
        Log.i("MainActivity", "current value: $value")
        return value
    }
}
