package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.constants.username

class MainActivity : AppCompatActivity() {
    lateinit var btn :Button
    lateinit var te :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* get rid of states bar */
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btn =findViewById(R.id.btn_start)
        te = findViewById(R.id.et_name)


        btn.setOnClickListener{

            if (te.text.toString().isEmpty()){
                Toast.makeText(this,"please enter your name!!",Toast.LENGTH_SHORT).show()

            }else{
               var intent = Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra(constants.username,te.text.toString() )


                startActivity(intent)
                //close current activity
                finish()
            }



        }




    }
}