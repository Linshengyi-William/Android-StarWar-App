package com.example.mainactivity.ui
import android.annotation.SuppressLint
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
import com.example.mainactivity.data.Film
import com.example.mainactivity.data.LoadingStatus
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class FilmQuiz : AppCompatActivity(){
    private var filmForQuestion: Film? = null
    private val filmViewModel: FilmViewModel by viewModels()

    lateinit var errorState: TextView
    lateinit var loading: CircularProgressIndicator
    lateinit var mainView: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("filmQuiz", "onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.film_quiz_page)

        var filmId = (1..7).random()
        var questionId = (1..3).random()


        errorState=findViewById(R.id.tv_search_error)
        loading=findViewById(R.id.loading_indicator)
        mainView=findViewById(R.id.main_film_page)

        Log.d("filmQuiz", "filmId: ${filmId}")

        filmViewModel.loadFilmResults(filmId)
        filmViewModel.searchResults.observe(this) {filmQuery ->
            filmForQuestion = filmQuery

            if(filmQuery != null) {
                Log.d("filmQuiz", "Observed: $filmQuery")
                Log.d("filmQuiz", "filmTitle ${filmForQuestion?.title}")
                Log.d("filmQuiz", "filmEps ${filmForQuestion?.episode_id}")

                loadFilmQuiz()

            }
        }
        filmViewModel.loadingStatus.observe(this) { loadingStatus ->
            when(loadingStatus) {
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
                LoadingStatus.SUCCESS -> {
                    loading.visibility = View.INVISIBLE
                    mainView.visibility = View.VISIBLE
                    errorState.visibility = View.INVISIBLE
                }
            }
        }


        val imageDeathStar: ImageView = this.findViewById(R.id.imageview_death_star)
        Picasso.get().load("https://pngimg.com/uploads/starwars/starwars_PNG56.png").resize(600,600).into(imageDeathStar)


    }

    @SuppressLint("StringFormatMatches")
    fun loadFilmQuiz() {
        val trueButton: Button = this.findViewById(R.id.button_true)
        val falseButton: Button = this.findViewById(R.id.button_false)

        val quizNumber: TextView = this.findViewById(R.id.textview_question_num)


        val userScore: TextView = this.findViewById(R.id.textview_score_film_quiz)

        val currentScore = intent.getIntExtra(USERS_SCORE, 0)
        val currentUserName = intent.getStringExtra(USERS_NAME)
        val currentQuestionNumber = intent.getIntExtra(QNUM, 1)
        quizNumber.text = getString(R.string.question_num, currentQuestionNumber)

        val questionType = (1..2).random()
        Log.d("FilmQuiz", "questionType: ${questionType}")

        if(questionType == 1) {
            val questionText: TextView = this.findViewById(R.id.textview_question_part_one)
            val questionTitle = filmForQuestion?.title ?: "N/A"
            val questionEps = decideTrueOrFalseOne()
            val answer = filmForQuestion?.episode_id ?: 0
            questionText.text = getString(R.string.filmQuestionPart1, questionTitle, questionEps)
            Log.d("FilmQuiz", "questionEps: ${questionEps} and answer: ${answer}")
            if(intent.hasExtra(USERS_SCORE)) {
                userScore.text = "${currentUserName}Score: ${currentScore}"
            }

            if(currentQuestionNumber < 7) {
                val intentAddOne = Intent(this, FilmQuizContinue::class.java).apply {
                    putExtra(USERS_SCORE, currentScore + 1)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)
                }
                val intentContinue = Intent(this, FilmQuizContinue::class.java).apply {
                    putExtra(USERS_SCORE, currentScore)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }

                trueButton.setOnClickListener {
                    startActivity(if (questionEps == answer) intentAddOne else intentContinue)
                    true
                }
                falseButton.setOnClickListener {
                    startActivity(if (questionEps != answer) intentAddOne else intentContinue)
                    true
                }
            } else {

                val intentFinishAddOne = Intent(this, FilmQuizFinish::class.java).apply {
                    putExtra(USERS_SCORE, currentScore + 1)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }
                val intentFinish = Intent(this, FilmQuizFinish::class.java).apply {
                    putExtra(USERS_SCORE, currentScore)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }
                trueButton.setOnClickListener {
                    startActivity(if (questionEps == answer) intentFinishAddOne else intentFinish)
                    true
                }
                falseButton.setOnClickListener {
                    startActivity(if (questionEps != answer) intentFinishAddOne else intentFinish)
                    true
                }
            }
        } else {
            val questionText2: TextView = this.findViewById(R.id.textview_question_part_one)
            val questionTitle = filmForQuestion?.title ?: "N/A"
            val questionEps = decideTrueOrFalseTwo()
            val answer = filmForQuestion?.release_date ?: "N/A"
            questionText2.text = getString(R.string.filmQuestionPart3, questionTitle, questionEps)
            Log.d("FilmQuiz", "questionEps: ${questionEps} and answer: ${answer}")
            if(intent.hasExtra(USERS_SCORE)) {
                userScore.text = "${currentUserName}Score: ${currentScore}"
            }

            if(currentQuestionNumber < 7) {
                val intentAddOne = Intent(this, FilmQuizContinue::class.java).apply {
                    putExtra(USERS_SCORE, currentScore + 1)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)
                }
                val intentContinue = Intent(this, FilmQuizContinue::class.java).apply {
                    putExtra(USERS_SCORE, currentScore)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }

                trueButton.setOnClickListener {
                    startActivity(if (questionEps == answer) intentAddOne else intentContinue)
                    true
                }
                falseButton.setOnClickListener {
                    startActivity(if (questionEps != answer) intentAddOne else intentContinue)
                    true
                }
            } else {

                val intentFinishAddOne = Intent(this, FilmQuizFinish::class.java).apply {
                    putExtra(USERS_SCORE, currentScore + 1)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }
                val intentFinish = Intent(this, FilmQuizFinish::class.java).apply {
                    putExtra(USERS_SCORE, currentScore)
                    putExtra(USERS_NAME, currentUserName)
                    putExtra(QNUM, currentQuestionNumber + 1)

                }
                trueButton.setOnClickListener {
                    startActivity(if (questionEps == answer) intentFinishAddOne else intentFinish)
                    true
                }
                falseButton.setOnClickListener {
                    startActivity(if (questionEps != answer) intentFinishAddOne else intentFinish)
                    true
                }
            }
        }
//
    }


    fun decideTrueOrFalseOne(): Int{
        val questionEps = filmForQuestion?.episode_id ?: 0
        val ifTrueOrFalse = (1..2).random()
            if(ifTrueOrFalse == 2) {
                var valueCheck = (1..7).random()
                while(questionEps == valueCheck) {
                    valueCheck = (1..7).random()
                }
                return valueCheck
            } else {
                return questionEps
            }
    }

    fun decideTrueOrFalseTwo(): String {
        val questionReleaseDate = filmForQuestion?.release_date ?: ""
        val ifTrueOrFalse = (1..2).random()
        if(ifTrueOrFalse == 2) {
            val indexArray = (0..4).random()
            var falseAnswers = linkedSetOf("1976-09-22","1982-05-17", "1986-03-25", "2001-11-05", "2005-7-11")
            return falseAnswers.elementAt(indexArray)
        } else {
            return questionReleaseDate
        }
    }
}