package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.R
import com.squareup.picasso.Picasso

class FightingQuizFailure : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("blue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fight_quiz_failure)
        val image = this.findViewById<ImageView>(R.id.imageview_failure)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG31.png").resize(1000,850).into(image)
        val winnerText: TextView = this.findViewById(R.id.textview_winner)
        val loserText: TextView = this.findViewById(R.id.textview_loser)
        val userScore: TextView = this.findViewById(R.id.textview_score)
        var username = intent.getStringExtra(USERNAME)
        if(username.isNullOrEmpty()){
            username = "User"
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

        val button: Button = this.findViewById(R.id.button_retry)
        button.setOnClickListener {
            val intent = Intent(this, FightingQuizLandingPage::class.java)
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
        var userScore: String = "0"

        if(intent.hasExtra(USER_SCORE)){
            userScore= "${intent.getIntExtra(USER_SCORE, 0)}"
        }
        val shareText = "This user has gotten a score of $userScore on the Star Wars Fighting Arena Game"
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }
}