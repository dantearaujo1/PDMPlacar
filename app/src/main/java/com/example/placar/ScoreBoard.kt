package com.example.placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreBoard : AppCompatActivity() {
    var isWorking = false
    var gameTime = 0
    var homePoint = 0
    var awayPoint = 0
    var gamePausedTime: Long = 0
    var matchID: Int = 0
    var firstHalfGone: Boolean = false
    val undos = ArrayList<Int>(50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        getConfigIntentData(intent)
        val lb_half = findViewById<TextView>(R.id.label_half);
        val btn_back = findViewById<Button>(R.id.btn_back);
        val btn_home = findViewById<Button>(R.id.btn_home);
        val btn_away = findViewById<Button>(R.id.btn_away);
        val btn_undo = findViewById<Button>(R.id.btn_undo);
        val btn_startClock = findViewById<Button>(R.id.btn_time)
        val btn_2time = findViewById<Button>(R.id.btn_2time)
        val meter = findViewById<Chronometer>(R.id.c_meter);

        lb_half.text = getString(R.string.lb_half,1);

        btn_back?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@ScoreBoard,MainActivity::class.java)
                saveGame()
                startActivity(intent);
            }

        })
        btn_home?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                homePoint += 1;
                undos.add(1);
                btn_home.text = homePoint.toString();
            }

        })
        btn_away?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                awayPoint += 1;
                undos.add(2);
                btn_away.text = awayPoint.toString();
            }
        })
        btn_undo?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(undos.get(undos.lastIndex) == 1){
                    homePoint -= 1;
                    btn_home.text = homePoint.toString();
                }
                else{
                    awayPoint -= 1;
                    btn_away.text = awayPoint.toString();
                }
                undos.removeAt(undos.lastIndex);
            }
        })
        meter.setOnChronometerTickListener {
            if(SystemClock.elapsedRealtime() - meter.getBase() > gameTime * 60000 ){
                gamePausedTime = 0
                meter.stop();
                isWorking=false;
                btn_2time.visibility = View.VISIBLE;
                btn_2time.setBackgroundColor(getResources().getColor(R.color.red))
            }
        }
        btn_startClock?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if (!isWorking) {
                    if(gamePausedTime != 0.toLong()){
                        meter.setBase(meter.getBase() + SystemClock.elapsedRealtime() - gamePausedTime)
                    }
                    else{
                        meter.setBase(SystemClock.elapsedRealtime())
                    }
                    meter.start()
                    isWorking = true
                } else {
                    gamePausedTime = SystemClock.elapsedRealtime()
                    meter.stop()
                    isWorking = false
                }
            }
        })
        btn_2time.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View?){
                lb_half.text = getString(R.string.lb_half,2);
                if(!firstHalfGone){
                    if (!isWorking) {
                        if(gamePausedTime != 0.toLong()){
                            meter.setBase(meter.getBase() + SystemClock.elapsedRealtime() - gamePausedTime)
                        }
                        else{
                            meter.setBase(SystemClock.elapsedRealtime())
                        }
                        meter.start()
                        isWorking = true
                    } else {
                        gamePausedTime = SystemClock.elapsedRealtime()
                        meter.stop()
                        isWorking = false
                    }
                    firstHalfGone = true;
                    btn_2time.text = "Save & Quit"
                    btn_2time.setBackgroundColor(getResources().getColor(R.color.black))
                }
                else{
                    saveGame()
                    val intent = Intent(this@ScoreBoard,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })

    }
    fun getConfigIntentData(intent: Intent?){
        val lb_home = findViewById<TextView>(R.id.label_player1)
        val lb_away = findViewById<TextView>(R.id.label_player2)
        val meter = findViewById<Chronometer>(R.id.c_meter)

        val home = intent?.getStringExtra("PlayerHome")
        val away = intent?.getStringExtra("PlayerAway")
        val time = intent?.getIntExtra("CountTime",0)
        matchID = intent!!.getIntExtra("Match",0)
        Log.d("PDM", "Recieved MatchID is : " + matchID)

        if(home != null){
            lb_home.text = home;
        }
        if(away != null){
            lb_away.text = away;
        }
        if(time != null){
            gameTime = time;
            meter.setBase(SystemClock.elapsedRealtime() + time.toLong())
        }
    }
    fun saveGame(){
        val sharedFileName="config"
        var sp: SharedPreferences = getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString("PlayerHome"+matchID, findViewById<TextView>(R.id.label_player1).text.toString())
        editor.putString("PlayerAway"+matchID, findViewById<TextView>(R.id.label_player2).text.toString())
        editor.putInt("PointHome"+matchID, findViewById<Button>(R.id.btn_home).text.toString().toInt())
        editor.putInt("PointAway"+matchID, findViewById<Button>(R.id.btn_away).text.toString().toInt())
        editor.putInt("CountTime"+matchID, gameTime)
        editor.putInt("MatchQuantity",matchID)
        editor.putInt("Match"+matchID,matchID)
        Log.d("PDM", "Saved MatchID is : " + matchID)

        // Last Player
        editor.putString("LastPlayerHome", findViewById<TextView>(R.id.label_player1).text.toString())
        editor.putString("LastPlayerAway", findViewById<TextView>(R.id.label_player2).text.toString())
        editor.putInt("LastCounterTime", gameTime)
        editor.apply()
    }
}
