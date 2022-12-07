package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mainactivity.R
import com.example.mainactivity.data.LoadingStatus
import com.example.mainactivity.data.People
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso

class FightingQuiz : AppCompatActivity(){
    private val fighterViewModel: PeopleViewModel by viewModels()
    private var fighterOne: People? = null
    private var fighterTwo: People? = null
    lateinit var errorState: TextView
    lateinit var loading: CircularProgressIndicator
    lateinit var mainView: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("blue", "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fight_quiz_duel)
        errorState=findViewById(R.id.tv_search_error)
        loading=findViewById(R.id.loading_indicator)
        mainView=findViewById(R.id.main_fight_view)
        var fighterOneId = (1..86).random()
        var fighterTwoId = (1..86).random()
        if(fighterOneId == 17){
            fighterOneId = 18
        }
        if(fighterTwoId == 17){
            fighterTwoId = 16
        }
        while(fighterOneId == fighterTwoId){
            fighterTwoId = (1..86).random()
            if(fighterTwoId == 17){
                fighterTwoId = 16
            }
        }
        Log.d("blue","fighterOneId: $fighterOneId, fighterTwoId: $fighterTwoId")
        fighterViewModel.loadFighterResults(fighterOneId, fighterTwoId)
        fighterViewModel.fighterOneResults.observe(this) { tempFighterOne ->
            Log.d("blue", "View Model Observe tempFighterOne $tempFighterOne")
            fighterOne = tempFighterOne
            intializeQuiz()
        }
        fighterViewModel.fighterTwoResults.observe(this) { tempFighterTwo ->
            Log.d("blue", "View Model Observe tempFighterTwo $tempFighterTwo")
            fighterTwo = tempFighterTwo
            intializeQuiz()
        }
        fighterViewModel.loadingStatus.observe(this) { loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    loading.visibility = View.VISIBLE
                    mainView.visibility = View.INVISIBLE
                    errorState.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    loading.visibility = View.INVISIBLE
                    mainView.visibility = View.INVISIBLE
                    errorState.visibility = View.VISIBLE
                }
                else -> {
                    loading.visibility = View.INVISIBLE
                    mainView.visibility = View.VISIBLE
                    errorState.visibility = View.INVISIBLE
                }
            }
        }
        val imageFighterOne: ImageView = this.findViewById(R.id.imageview_fighter_one)
        val imageFighterTwo: ImageView = this.findViewById(R.id.imageview_fighter_two)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG62.png").resize(400,400).into(imageFighterOne)
        Picasso.get().load("https://pngimg.com/uploads/darth_vader/darth_vader_PNG34.png").resize(400,400).into(imageFighterTwo)
    }
    fun intializeQuiz(){
        val buttonOne: Button = this.findViewById(R.id.button_fighter_one)
        val buttonTwo: Button = this.findViewById(R.id.button_fighter_two)
        val fighterOneText: TextView = this.findViewById(R.id.textview_fighter_one)
        val fighterTwoText: TextView = this.findViewById(R.id.textview_fighter_two)
        val userScore: TextView = this.findViewById(R.id.textview_score)
        val currentUserScore = intent.getIntExtra(USER_SCORE, 0)
        var currentUsername = intent.getStringExtra(USERNAME)
        val fighterOneName = fighterOne?.name ?: "Fighter One"
        val fighterTwoName = fighterTwo?.name ?: "Fighter Two"
        buttonOne.text = if(fighterOneName.length > 15) fighterOneName.substring(0, Math.min(fighterOneName.length, 12)) + "..." else fighterOneName
        buttonTwo.text = if(fighterTwoName.length > 15) fighterTwoName.substring(0, Math.min(fighterTwoName.length, 12)) + "..." else fighterTwoName
        fighterOneText.text = fighterOne?.name
        fighterTwoText.text = fighterTwo?.name

        var winner = find_winner()
        if(intent.hasExtra(USER_SCORE)){
            userScore.text = "${currentUsername}Score: ${currentUserScore}"
        }
        val intentSuccess = Intent(this, FightingQuizSuccess::class.java).apply{
            putExtra(USER_SCORE, currentUserScore + 1)
            putExtra(FIGHT_WINNER, if(winner == 1)fighterOne?.name ?: "Unknown" else fighterTwo?.name ?: "Unknown")
            putExtra(FIGHT_LOSER, if(winner == 1)fighterTwo?.name ?: "Unknown" else fighterOne?.name ?: "Unknown")
            putExtra(USERNAME, currentUsername)
        }
        val intentFailure = Intent(this, FightingQuizFailure::class.java).apply{
            putExtra(USER_SCORE, currentUserScore)
            putExtra(FIGHT_WINNER, if(winner == 2)fighterTwo?.name ?: "Unknown" else fighterOne?.name ?: "Unknown")
            putExtra(FIGHT_LOSER, if(winner == 2)fighterOne?.name ?: "Unknown" else fighterTwo?.name ?: "Unknown")
            putExtra(USERNAME, currentUsername)
        }

        buttonOne.setOnClickListener {
            startActivity(if(winner == 1) intentSuccess else intentFailure)
            true
        }
        buttonTwo.setOnClickListener {
            startActivity(if(winner == 2) intentSuccess else intentFailure)
            true
        }
    }
    fun find_winner(): Int{
        var fighterOneValue = if(fighterOne?.height != "unknown") fighterOne?.height?.substringBefore(".")?.replace(",","")?.substringBefore(".")?.toInt() ?: 0 else{(1..86).random()} + if(fighterOne?.mass != "unknown") fighterOne?.mass?.substringBefore(".")?.replace(",","")?.toInt() ?: 0 else{(1..86).random()}
        var fighterTwoValue = if(fighterTwo?.height != "unknown") fighterTwo?.height?.substringBefore(".")?.replace(",","")?.substringBefore(".")?.toInt() ?: 0 else{(1..86).random()} + if(fighterTwo?.mass != "unknown") fighterTwo?.mass?.substringBefore(".")?.replace(",","")?.toInt() ?: 0 else{(1..86).random()}
//        if(fighterOne?.name?.length ?: 0 > fighterTwo?.name?.length ?: 0){
        if(fighterOneValue  > fighterTwoValue){
            return 1
        } else {
            return 2
        }
    }
}