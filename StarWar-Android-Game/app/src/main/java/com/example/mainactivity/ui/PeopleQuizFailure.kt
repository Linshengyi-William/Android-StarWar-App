package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.view.Menu

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.R
import com.squareup.picasso.Picasso
import java.lang.invoke.WrongMethodTypeException

class PeopleQuizFailure : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("red", "onCreate() in PeopleQuizFailure")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_quiz_failure)
        val image = this.findViewById<ImageView>(R.id.imageview_failure)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG5.png").resize(800,800).into(image)

        val userGetRightNum: TextView = this.findViewById(R.id.tv_get_right_num)
        val userGetWrongNum: TextView = this.findViewById(R.id.tv_get_wrong_num)

        val userCongrats: TextView = this.findViewById(R.id.tv_congrats)
        val button: Button = this.findViewById(R.id.button_retry)
        var username = intent.getStringExtra(USERNAME)
        if(username.isNullOrEmpty()){
            username = "User"
        }
        if(intent.hasExtra(RIGHT_NUM)){
            userGetRightNum.text = "Correct: ${intent.getIntExtra(RIGHT_NUM, 0)}"
        }
        if(intent.hasExtra(WRONG_NUM)){
            userGetWrongNum.text = "Wrong: ${intent.getIntExtra(WRONG_NUM, 0)}"
        }

        if(intent.hasExtra(USERNAME)){
            userCongrats.text = "Wrong! ${username}!"
        }

        button.setOnClickListener {
            val intent = Intent(this, PeopleQuizActivity::class.java).apply{
                putExtra(RIGHT_NUM, intent.getIntExtra(RIGHT_NUM, 0))
                putExtra(WRONG_NUM, intent.getIntExtra(WRONG_NUM, 0))
                putExtra(USERNAME, username)
            }
            startActivity(intent)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fighting_quiz_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareScore()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun shareScore() {
        var userGotCorrect: Int = 0
        var userGotWrong: Int = 0
        var userName: String = "User"


        userGotCorrect= intent.getIntExtra(RIGHT_NUM,0)
        userGotWrong= intent.getIntExtra(WRONG_NUM,0)

        userName = "${intent.getStringExtra(USERNAME)}"
        if( userName=="") {
            userName = "User"
        }



        val shareText = "$userName got a $userGotCorrect Questions Correct and $userGotWrong Questions Wrong in the Star Wars Character Knowledge Check"
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }
}