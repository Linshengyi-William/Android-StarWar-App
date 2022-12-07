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

const val QNUM = "QNUM"
const val USERS_NAME = "USERS_NAME"
const val USERS_SCORE = "USERS_SCORE"
class FilmQuizLandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        Log.d("red", "film onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_quiz_start)
        val filmQuizImg: ImageView = this.findViewById(R.id.film_quiz_image)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG37.png").resize(800,800).into(filmQuizImg)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        var userName = sharedPrefs.getString(getString(R.string.settings_key_username),"")
        if(!userName.isNullOrBlank() and !userName.isNullOrEmpty()){
            userName = "${userName?.trim()?.capitalize()}'s "
        }

        val startButton: Button = this.findViewById(R.id.button_start_film_quiz)
        startButton.setOnClickListener {
            val intent = Intent(this, FilmQuiz::class.java).apply{
                putExtra(USERS_NAME, userName)
                putExtra(USERS_SCORE, 0)
                putExtra(QNUM,1)
            }
            startActivity(intent)
            true
        }
    }
}
