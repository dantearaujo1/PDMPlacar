package com.example.placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity(){
    var matchQuantity: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        createHistory()
        val btn_clear = findViewById<Button>(R.id.btn_clear)
        btn_clear?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val pref : SharedPreferences = getSharedPreferences("config",Context.MODE_PRIVATE)
                if(pref!=null){
                    pref.edit().clear().commit()
                }
                createHistory()
            }

        })
    }
    fun createHistory(){
        val configsFile = "config"
        var sp:SharedPreferences = getSharedPreferences(configsFile, Context.MODE_PRIVATE)
        if(sp!=null){
            matchQuantity = sp.getInt("MatchQuantity",0);
        }
        val recyclerView = findViewById<RecyclerView>(R.id.r_view)
        recyclerView.layoutManager = LinearLayoutManager(this);

        val data = ArrayList<ItemViewModel>()

        for (i in 0..matchQuantity){
            val id = i
            if(id==0)continue
            data.add(ItemViewModel(sp.getInt("PointHome"+id,0),
                sp.getInt("PointAway"+id, 0),
                sp.getString("PlayerHome"+id, "Team One").toString(),
                sp.getString("PlayerAway"+id,"Team Two").toString(),
                sp.getString("Lat"+id,"").toString(),
                sp.getString("Long"+id,"").toString()))
        }

        val adapter = CustomAdapter(data)

        recyclerView.adapter = adapter;
    }



}