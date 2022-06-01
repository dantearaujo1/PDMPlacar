package com.example.placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var nextGameID = 0;
    var matchQuantity = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_start = findViewById<Button>(R.id.btn_start);
        val btn_config = findViewById<Button>(R.id.btn_config);
        val btn_history = findViewById<Button>(R.id.btn_history);
        btn_start?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity,ScoreBoard::class.java)
                loadLastConfigAndCreate(intent)
                startActivity(intent);

            }

        })
        btn_history?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity,HistoryActivity::class.java)
                startActivity(intent);
            }

        })
        btn_config?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity,ConfigurationActivity::class.java)
                startActivity(intent);
            }

        })
    }

    fun loadLastConfigAndCreate(intent:Intent){
        val sharedFilename = "config"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        if(sp!=null) {
            matchQuantity = sp.getInt("MatchQuantity",0);
            nextGameID = matchQuantity + 1;
            intent.putExtra("PlayerHome",sp.getString("LastPlayerHome","Team One"))
            intent.putExtra("PlayerAway",sp.getString("LastPlayerAway","Team Two"))
            intent.putExtra("CountTime",sp.getInt("LastCounterTime",1))
            intent.putExtra("Match",nextGameID)
            Log.d("PDM","NextGameID is :" + nextGameID.toString())
        }
    }




}