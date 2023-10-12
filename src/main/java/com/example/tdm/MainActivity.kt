package com.example.tdm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("pref",Context.MODE_PRIVATE) ?: return

        val connected = pref.getString("status","not connected")
        if (connected =="connected"){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmenthost,LoginFragment())
            commit()
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}