package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.R
import com.squareup.picasso.Picasso

class FilmQuizContinue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("FilmQuizContinue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_quiz_continue)
        val continueButton: Button = findViewById(R.id.b_continue)
        val continueImage = this.findViewById<ImageView>(R.id.iv_continue)
        Picasso.get().load("https://pngimg.com/uploads/star_wars_logo/star_wars_logo_PNG3.png").resize(600, 600).into(continueImage)
        val usersScore: TextView = this.findViewById(R.id.tv_score)
        val currentQNum = intent.getIntExtra(QNUM, 0)
        var usersName = intent.getStringExtra(USERS_NAME)
        val usersCurrScore = intent.getIntExtra(USERS_SCORE, 0)
        if(usersName.isNullOrEmpty()){
            usersName =""
        }
        if(intent.hasExtra(USERS_SCORE)) {
            usersScore.text = "${usersName}Score: $usersCurrScore"
        }

        continueButton.setOnClickListener {
            val intent = Intent(this, FilmQuiz::class.java).apply {
                putExtra(USERS_SCORE, usersCurrScore)
                putExtra(USERS_NAME, usersName)
                putExtra(QNUM, currentQNum)
            }
            startActivity(intent)
            true
        }
    }
}