package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.mainactivity.R
import com.squareup.picasso.Picasso
const val USER_SCORE = "USER_SCORE"
const val USERNAME = "USERNAME"
const val FIGHT_WINNER = "FIGHT_WINNER"
const val FIGHT_LOSER = "FIGHT_LOSER"
class FightingQuizLandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("blue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fight_quiz_start)
        val image: ImageView = this.findViewById(R.id.fight_quiz_image)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG43.png").resize(800,800).into(image)
        val button: Button = this.findViewById(R.id.button_start_fight_quiz)
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        var username = sharedPrefs.getString(getString(R.string.settings_key_username),"")
        if(!username.isNullOrBlank() and !username.isNullOrEmpty()){
            username = "${username?.trim()?.capitalize()}'s "
        }
        button.setOnClickListener {
            val intent = Intent(this, FightingQuiz::class.java).apply{
                putExtra(USER_SCORE, 0)
                putExtra(USERNAME, username)
            }
            startActivity(intent)
            true
        }
    }
}