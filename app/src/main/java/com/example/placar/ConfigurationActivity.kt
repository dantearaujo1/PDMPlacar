package com.example.placar

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker

class ConfigurationActivity : AppCompatActivity() {
    lateinit var previewSelectedTimeTextView: TextView
    var selectedHour: Int = 0
    var selectedMinutes: Int = 0
    var selectedResult: Int = 0
    var matchID: Int = 0
    var matchQuantity: Int = 0
    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                // logic to properly handle
                // the picked timings by user
                selectedHour = "${hourOfDay}".toInt()
                selectedMinutes = "${minute}".toInt()
                selectedResult = selectedHour * 60 + selectedMinutes;

                previewSelectedTimeTextView.text = selectedResult.toString() + " " + getString(R.string.minutes);
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        val btn_tempo = findViewById<Button>(R.id.btn_tempo)
        val btn_back = findViewById<Button>(R.id.btn_back_menu);
        val btn_create = findViewById<Button>(R.id.btn_create);
        previewSelectedTimeTextView = findViewById<TextView>(R.id.lb_previewTempo)
        previewSelectedTimeTextView.text = getString(R.string.countDown) + " " + getString(R.string.minutes)
        btn_tempo.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                // pass the Context
                this,
                // listener to perform task
                // when time is picked
                timePickerDialogListener,
                // default hour when the time picker
                // dialog is opened
                0,
                // default minute when the time picker
                // dialog is opened
                10,
                // 24 hours time picker is
                // false (varies according to the region)
                true
            )

            // then after building the timepicker
            // dialog show the dialog to user
            timePicker.show()
        }
        btn_back?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@ConfigurationActivity,MainActivity::class.java)
                saveConfig()
                startActivity(intent);
            }

        })
        btn_create?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                matchID++
                matchQuantity++
                val intent = Intent(this@ConfigurationActivity,ScoreBoard::class.java)
                intent.putExtra("PlayerHome", findViewById<TextView>(R.id.et_pHome).text.toString());
                intent.putExtra("PlayerAway",findViewById<TextView>(R.id.et_pAway).text.toString() );
                intent.putExtra("Match",matchID);
                intent.putExtra("CountTime",selectedResult );
                createGame()
                startActivity(intent);
            }

        })

        loadLastConfig()
        loadGames()
    }

    fun saveConfig(){
        val sharedFilename = "config"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var edShared = sp.edit()
        edShared.putString("LastPlayerHome",findViewById<EditText>(R.id.et_pHome).text.toString())
        edShared.putString("LastPlayerAway",findViewById<EditText>(R.id.et_pAway).text.toString())
        edShared.putInt("LastCounterTime",selectedResult)
        edShared.commit()
    }
    fun createGame(){
        saveConfig()
        val sharedFilename = "config"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var edShared = sp.edit()
        edShared.putInt("Match"+matchID,matchID)
        edShared.putInt("MatchQuantity",matchQuantity)
        edShared.putString("PlayerHome"+matchID,findViewById<EditText>(R.id.et_pHome).text.toString())
        edShared.putString("PlayerAway"+matchID,findViewById<EditText>(R.id.et_pAway).text.toString())
        edShared.commit()
    }

    fun loadLastConfig(){
        val sharedFilename = "config"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        if(sp!=null) {
            val pHome = findViewById<EditText>(R.id.et_pHome)
            val pAway = findViewById<EditText>(R.id.et_pAway)
            val lb_time = findViewById<TextView>(R.id.lb_previewTempo)
            pHome.setText(sp.getString("LastPlayerHome","Team One"))
            pAway.setText(sp.getString("LastPlayerAway","Team Two"))
            lb_time.setText(sp.getInt("LastCounterTime",10).toString() + " " + getString(R.string.minutes))
        }

    }
    fun loadGames(){
        val sharedFilename = "config"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        if(sp!=null) {
            matchQuantity = sp.getInt("MatchQuantity",0);
            matchID = matchQuantity;
        }

    }



}