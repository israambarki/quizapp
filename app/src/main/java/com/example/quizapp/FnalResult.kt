package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FnalResult : AppCompatActivity() {
    lateinit var name : TextView
    lateinit var total : TextView
    lateinit var butt :  Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fnal_result)
        name = findViewById(R.id.tv_name)
        total = findViewById(R.id.tv_score)
        butt = findViewById(R.id.btn_finish)

    val username = intent.getStringExtra(constants.username)
        name.text=username

        val score = intent.getIntExtra(constants.totalquestion,0)
        val score2= intent.getIntExtra(constants.correctAnswers,0)
        total.text = "Your score is $score2 out of $score"

        butt.setOnClickListener{
       startActivity(Intent(this,MainActivity::class.java))
   //close this activity :
            finish()


        }




    }
}