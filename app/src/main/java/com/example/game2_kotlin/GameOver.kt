package com.example.game2_kotlin

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.Notification
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.game2_kotlin.lib.ExitDialog


class GameOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val extras = intent.extras
        var level: String? = null
        var score: String? = null
        if (extras != null){
            level = extras.getString("level")
            score = extras.getString("score")
        }

        val txtLevel = findViewById<TextView>(R.id.txtLevel_gameOver)
        val txtScore = findViewById<TextView>(R.id.txtScore_gameOver)

        txtLevel.setText("Highest Level : $level")
        txtScore.setText("Highest Score : $score")

        val btnRePlay = findViewById<Button>(R.id.btnRePlay)
        btnRePlay.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, Launcer::class.java)
        startActivity(intent)
        this.finish()
    }
}