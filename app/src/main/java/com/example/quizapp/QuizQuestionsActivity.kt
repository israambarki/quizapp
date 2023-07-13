package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var quest : TextView
    lateinit var prog : ProgressBar
    lateinit var progtext : TextView
    lateinit var im : ImageView

    lateinit var op1 : TextView
    lateinit var  op2 : TextView
    lateinit var  op3 :TextView
    lateinit var  op4 :TextView

    lateinit var sub :Button

//to know the current position of the selected response et comme par defaut je suis dans la question num 1
    private var mcurrentposition : Int = 1
    //Default and the first question position
    // kol mara nod5el lezem nebda bil question loul

    private var mquestionliste :ArrayList<Question>? = null

    // i need to know wich option the user has selected
    // = 0 because we haven't selected anything
    private var mselectoptionposition : Int = 0 //to check the answer is correct -- because we haven't selected anything

    //make var to know haw many time you answered correctly
    private var correctAn :Int = 0

    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        username = intent.getStringExtra(constants.username)


        prog = findViewById(R.id.progressBar)
        progtext=findViewById(R.id.tv_progress)
        quest = findViewById(R.id.tv_question)
        im = findViewById(R.id.iv_image)

        op1 =findViewById(R.id.tv_option_one)
        op2=findViewById(R.id.tv_option_two)
        op3=findViewById(R.id.tv_option_three)
        op4=findViewById(R.id.tv_option_four)
        sub =findViewById(R.id.btn_submit)



        mquestionliste = constants.getquest()

        setQuestion()
        /////
        /*  nos boutons doivent avoir onclicklistener */
        op1.setOnClickListener(this)
        op2.setOnClickListener(this)
        op3.setOnClickListener(this)
        op4.setOnClickListener(this)


        sub.setOnClickListener(this)


    }

private fun setQuestion(){
// Getting the question from the list with the help of current position
   // mcurrentposition = 1 -- to test i found it verified
    //questionnv de type Question
    val questionNv = mquestionliste!!.get(mcurrentposition-1)
    Toast.makeText(
        this,"setquestion",Toast.LENGTH_SHORT).show()
    defaultOptions()

    if (mcurrentposition == mquestionliste!!.size){
        sub.text ="FINISH!!"
    }else{
        Toast.makeText(
            this,"Submit",Toast.LENGTH_SHORT).show()
        sub.text ="SUBMIT"
    }




    prog.progress = mcurrentposition

    progtext.text= "$mcurrentposition" + "/"+ prog.max
    quest.text=questionNv!!.question
    im.setImageResource(questionNv.image)
    op1.text=questionNv.optionOne
    op2.text=questionNv.optionTwo
    op3.text=questionNv.optionThree
    op4.text=questionNv.optionFour

}

    private fun defaultOptions(){
        
        val opt = ArrayList<TextView>()
        opt.add(0,op1)
        opt.add(1,op2)
        opt.add(2,op3)
        opt.add(3,op4)


        Toast.makeText(
            this,"defaultoption",Toast.LENGTH_SHORT).show()


//je vais faire une loop pour definir the default layout button
  for (o in opt ){
    o.setTextColor(Color.parseColor("#7A8089"))
    o.typeface= Typeface.DEFAULT
    o.background = ContextCompat.getDrawable(
        this,R.drawable.default_option_border_bg
    )
      
      }
    }

    //func pour la bouton selectée:
    private fun selectedOpt(t : TextView, selectedOpNb : Int){
        //chaque foie on fait la reset:
      defaultOptions()
        mselectoptionposition = selectedOpNb
        t.setTextColor(Color.parseColor(
            "#363A43"
        ))
        t.setTypeface(t.typeface,Typeface.BOLD)
        t.background = ContextCompat.getDrawable(
            this,R.drawable.selected_option_border_bg
        )

    }





    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tv_option_one ->{ selectedOpt(op1,1)
                 }
            R.id.tv_option_two -> {selectedOpt(op2,2)
                }
            R.id.tv_option_three ->{ selectedOpt(op3,3)
                }
            R.id.tv_option_four ->{ selectedOpt(op4,4)
                 }

            R.id.btn_submit -> {

                Toast.makeText(
                    this,"button press",Toast.LENGTH_LONG).show()

                          //par defaut on a pas selectionner une opt
                          //move to the next question : mcurrenet++
/* problem !! */

                if (mselectoptionposition == 0 ){
                    Toast.makeText(
                        this,"enter dans if",Toast.LENGTH_LONG).show()
                              mcurrentposition++

                    when {
                                  mcurrentposition <= mquestionliste!!.size ->{

                                      Toast.makeText(
                                          this,"you are in mselectionoptposition == 0",Toast.LENGTH_LONG).show()

                                      //set the next question
                                      setQuestion()
                                  }else->{

                        val intent = Intent(this,FnalResult::class.java)
                        intent.putExtra(constants.username,username)
                        intent.putExtra(constants.correctAnswers,correctAn)
                        intent.putExtra(constants.totalquestion,mquestionliste!!.size)
                        startActivity(intent)
                                      Toast.makeText(
                                          this,"you have finished the Quiz",Toast.LENGTH_SHORT).show()

                               finish()
                                }
                          }

                } else {
                    Toast.makeText(
                        this,"you are in else",Toast.LENGTH_LONG).show()

                              //arraylist start with 0
                              val question = mquestionliste?.get( mcurrentposition - 1 )
                              // This is to check if the answer is wrong:
                              if (question!!.correctAnswer != mselectoptionposition){
                                  //wrong answer
                                  answer(mselectoptionposition,R.drawable.wrong_option)

                              }else{
                                  correctAn++
                              }
                              //man7othech fi else 5ater 7echti fi kol mara ki nji njeweb 3ala question ta3tini elijeba el8alta wess7i7a
                                  answer(question.correctAnswer,R.drawable.correct_option)
                          if(mcurrentposition == mquestionliste!!.size){
                              sub.text = "FINISHED"
                            }else{
                                 sub.text=" Go To The Next One"

                                }
                    Toast.makeText(
                        this,"initialisation",Toast.LENGTH_LONG).show()
//we have to set the selectedposition to zero !!!  to setquestion -- go to the next question
                    mselectoptionposition = 0
                }

                     }

             }

        }

// add fct that allows us if the answer is correct or not
//asign the right color to our option
private fun answer( answer : Int , drawbalView :Int) {
    when (answer){
        1 -> {
           op1.background = ContextCompat.getDrawable(this, drawbalView)
        }

        2 -> {
            op2.background = ContextCompat.getDrawable(this, drawbalView)
        }
        3 -> {
            op3.background = ContextCompat.getDrawable(this, drawbalView)
        }
        4 -> {
            op4.background = ContextCompat.getDrawable(this, drawbalView)
           }

      }


 }



}