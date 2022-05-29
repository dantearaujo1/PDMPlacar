package com.example.placar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_start = findViewById<Button>(R.id.btn_start);
        btn_start?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity,ScoreBoard::class.java)
                intent.putExtra("Valores", "HAHAHA");
                startActivity(intent);
            }

        })
    }


}