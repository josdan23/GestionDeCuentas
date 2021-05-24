package com.danielyapura.gestiondecuentas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        toolbar3.title = "Info"
        setSupportActionBar(toolbar3)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}