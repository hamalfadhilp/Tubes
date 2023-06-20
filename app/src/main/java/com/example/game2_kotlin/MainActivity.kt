package com.example.game2_kotlin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.game2_kotlin.lib.ExitDialog
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var progressBar: ProgressBar? = null

    private var progressText: TextView? = null
    private var txtLevel: TextView? = null
    private var txtScore: TextView? = null
    private var txtColor: TextView? = null
    private var btnRight: Button? = null
    private var btnWrong: Button? = null
    //
    private val arrColors = arrayOf("#FFFFFF", "#0000FF", "#FF0000", "#00FF00", "#808080", "#FFFF00")
//    //    private val arrColorsNameEN = arrayOf("WHITE", "BLUE", "RED", "GREEN", "GRAY", "YELLOW")
    private val arrColorsNameID = arrayOf("PUTIH", "BIRU", "MERAH", "HIJAU", "ABU-ABU", "KUNING")
    private var indexColor: Int = 0
    private var indexName: Int = 0
    private var level: Int = 1
    private var score: Int = 0
    private var loop: Int = 0
    private var second: Int = 5
    private var playerIsAnswering: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressText = findViewById<TextView>(R.id.progressText)
        btnRight = findViewById<Button>(R.id.btnRight)
        btnWrong = findViewById<Button>(R.id.btnWrong)
        txtColor = findViewById<TextView>(R.id.txtColor)
        txtLevel = findViewById<TextView>(R.id.txtLevel)
        txtScore = findViewById<TextView>(R.id.txtScore)

        Start()
        btnRight!!.setOnClickListener{
            CheckAnswer(1)
        }
        btnWrong!!.setOnClickListener{
            CheckAnswer(0)
        }
    }

    private fun Start() {
        playerIsAnswering = false
        level = 1
        score = 0
        txtLevel!!.setText("Level : $level")
        txtScore!!.setText("Score : $score")
        GenerateColors()
    }

    private fun GenerateColors() {
        val random = Random()
        indexColor = random.nextInt(arrColors.size)
        indexName = random.nextInt(arrColors.size)
        txtColor!!.setTextColor(Color.parseColor(arrColors[indexColor]))
        txtColor!!.text = arrColorsNameID[indexName]
        StartCountDown()
    }

    private fun Next() {
        level++
        score += 2
        txtLevel!!.setText("Level : $level")
        txtScore!!.setText("Score : $score")
        playerIsAnswering = false
        GenerateColors()
    }

    private fun StartCountDown() {
        StopCountDown()
        loop = 1
        progressBar!!.setProgress(0)
        progressText!!.setText("$second")
        countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (loop == 5) {
                    GameOver()
                }
                progressText!!.setText(""+(second-loop))
                progressBar!!.setProgress(loop * 100 / (5000 / 1000))
                if (playerIsAnswering) countDownTimer?.onFinish()
                loop++
            }

            override fun onFinish() {
                loop = 5
                progressBar!!.setProgress(100)
            }
        }.start()
    }

    private fun StopCountDown() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    private fun GameOver() {
//        Toast.makeText(getApplicationContext(), "Last Skor : " + skor, Toast.LENGTH_SHORT).show();
        StopCountDown()
        val intent = Intent(this, GameOver::class.java)
        intent.putExtra("level", "$level")
        intent.putExtra("score", "$score")
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        this.finish()
    }

    private fun CheckAnswer(answer: Int) {
        playerIsAnswering = true
        try {
            when (answer) {
                0 -> {
                    if (indexColor != indexName) Next() else GameOver()
                }
                1 -> {
                    if (indexColor == indexName) Next() else GameOver()
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun onBackPressed() {
        val exit = ExitDialog()
        exit.isCancelable = false
        exit.show(supportFragmentManager, null)
    }
}