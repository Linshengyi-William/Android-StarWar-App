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

class FilmQuizFinish : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("FilmQuizContinue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_quiz_finish)
        val continueButton: Button = findViewById(R.id.b_replay)
        val continueImage = this.findViewById<ImageView>(R.id.iv_final)
        Picasso.get().load("https://pngimg.com/uploads/darth_vader/darth_vader_PNG21.png").resize(600, 600).into(continueImage)
        val usersScore: TextView = this.findViewById(R.id.tv_score)
        val currentQNum = intent.getIntExtra(QNUM, 0)
        var usersName = intent.getStringExtra(USERS_NAME)
        val usersCurrScore = intent.getIntExtra(USERS_SCORE, 0)
        if(usersName.isNullOrEmpty()){
            usersName =""
        }
        if(intent.hasExtra(USERS_SCORE)) {
            usersScore.text = "${usersName}Final Score: $usersCurrScore"
        }

        continueButton.setOnClickListener {
            val intent = Intent(this, FilmQuizLandingPage::class.java)
            startActivity(intent)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.film_quiz_menu, menu)
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
        if(intent.hasExtra(USERS_SCORE)){
            userScore= "${intent.getIntExtra(USERS_SCORE, 0)}"
        }
        val shareText = "This user has gotten a score of $userScore on the Star Wars Film Quiz Game"
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }


}