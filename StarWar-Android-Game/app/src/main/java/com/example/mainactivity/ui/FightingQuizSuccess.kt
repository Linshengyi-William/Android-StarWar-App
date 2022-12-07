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

class FightingQuizSuccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("blue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fight_quiz_success)
        val image = this.findViewById<ImageView>(R.id.imageview_success)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG26.png").resize(1000,600).into(image)
        val winnerText: TextView = this.findViewById(R.id.textview_winner)
        val loserText: TextView = this.findViewById(R.id.textview_loser)
        val userScore: TextView = this.findViewById(R.id.textview_score)
        val button: Button = this.findViewById(R.id.button_continue)
        var username = intent.getStringExtra(USERNAME)
        if(username.isNullOrEmpty()){
            username = ""
        }
        if(intent.hasExtra(USER_SCORE)){
            userScore.text = "${username}Score: ${intent.getIntExtra(USER_SCORE, 0)}"
        }
        if(intent.hasExtra(FIGHT_WINNER)){
            winnerText.text = "${intent.getStringExtra(FIGHT_WINNER)}"
        }
        if(intent.hasExtra(FIGHT_LOSER)){
            loserText.text = "${intent.getStringExtra(FIGHT_LOSER)}"
        }
        button.setOnClickListener {
            val intent = Intent(this, FightingQuiz::class.java).apply{
                putExtra(USER_SCORE, intent.getIntExtra(USER_SCORE, 0))
                putExtra(USERNAME, username)
            }
            startActivity(intent)
            true
        }
    }
}