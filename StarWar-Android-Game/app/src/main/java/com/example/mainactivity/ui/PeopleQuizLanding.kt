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


const val RIGHT_NUM = "RIGHT_NUM"
const val WRONG_NUM = "WRONG_NUM"
const val PEOPLE_QUESTION_COMPONENT1 = "PEOPLE_QUESTION_COMPONENT1"
const val PEOPLE_QUESTION_COMPONENT2 = "PEOPLE_QUESTION_COMPONENT2"
const val PEOPLE_QUESTION_COMPONENT3 = "PEOPLE_QUESTION_COMPONENT3"

//const val FIGHT_LOSER = "FIGHT_LOSER"

class PeopleQuizLanding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("blue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.people_quiz_landing)
        val image: ImageView = this.findViewById(R.id.people_quiz_image)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG7.png").resize(800, 800)
            .into(image)
        val button: Button = this.findViewById(R.id.button_start_people_quiz)
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        var username = sharedPrefs.getString(getString(R.string.settings_key_username), "")
        if (!username.isNullOrBlank() and !username.isNullOrEmpty()) {
            username = "${username?.trim()?.capitalize()}"
        }
            button.setOnClickListener {
                val intent = Intent(this, PeopleQuizActivity::class.java).apply {
                    putExtra(RIGHT_NUM, 0)
                    putExtra(WRONG_NUM, 0)
                    putExtra(USERNAME, username)
                    putExtra(PEOPLE_QUESTION_COMPONENT1, "component1")
                    putExtra(PEOPLE_QUESTION_COMPONENT2, "component2")
                    putExtra(PEOPLE_QUESTION_COMPONENT3, "component3")
                }
                startActivity(intent)
                true
            }
        }
    }