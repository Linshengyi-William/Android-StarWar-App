package com.example.mainactivity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mainactivity.R
import com.example.mainactivity.data.LoadingStatus
import com.example.mainactivity.data.People
import com.google.android.material.progressindicator.CircularProgressIndicator

class PeopleQuizActivity : AppCompatActivity() {
    private val peopleViewModel: PeopleViewModel by viewModels()
    private var peopleInQuestion: People? = null
    lateinit var errorState: TextView
    lateinit var loading: CircularProgressIndicator
    lateinit var mainView: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_quiz)

        errorState=findViewById(R.id.tv_search_error)
        loading=findViewById(R.id.loading_indicator)
        mainView=findViewById(R.id.main_people_quiz_view)

        val skinColorArray = arrayOf<String>("skin color","green","brown","white, red","light","n/a","fair","pale")
        val eyeColorArray = arrayOf<String>("eye color","blue","red","orange","white","green","gold","yellow","brown","black")
        val hairColorArray = arrayOf<String>("hair color","blue","red","orange","white","green","gold","yellow","brown","black","grey")
        val skinColorArrLength = skinColorArray.size -1
        val eyeColorArrLength = eyeColorArray.size -1
        val hairColorArrLength = hairColorArray.size -1
        var randomIndexSkinColorArr = (1..skinColorArrLength).random()
        var randomIndexEyeColorArr = (1..eyeColorArrLength).random()
        var randomIndexHairColorArr = (1..hairColorArrLength).random()

        var peopleId = (1..86).random()
        while(peopleId == 17) {peopleId=(1..86).random()}
        //var mode = (1..3).random()
        peopleViewModel.loadPeopleResults(peopleId)
        peopleViewModel.searchResults.observe(this) { tempPeopleInQuestion ->
            //peopleViewModel.loadPeopleResults(peopleId)
            Log.d("red", "View Model Observe tempPeopleInQuestion $tempPeopleInQuestion")
            peopleInQuestion = tempPeopleInQuestion

            var mode = (1..3).random()
            when (mode) {
                1 -> intializeQuiz(eyeColorArray,randomIndexEyeColorArr)
                2 -> intializeQuiz(skinColorArray,randomIndexSkinColorArr)
                3 -> intializeQuiz(hairColorArray,randomIndexHairColorArr)
            }
        }


        peopleViewModel.loadingStatus.observe(this) { loadingStatus ->
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
    }

    fun intializeQuiz(arr:Array<String>,arrIdx: Int){

        val buttonTrue: Button = this.findViewById(R.id.button_choice_right)
        val buttonFalse: Button = this.findViewById(R.id.button_choice_wrong)
        var currentUsername = intent.getStringExtra(USERNAME)
        val userGetRightNum: TextView = this.findViewById(R.id.tv_get_right_num)
        val userGetWrongNum: TextView = this.findViewById(R.id.tv_get_wrong_num)
        val currentRightNum = intent.getIntExtra(RIGHT_NUM, 0)
        val currentWrongNum = intent.getIntExtra(WRONG_NUM, 0)

        val mode2 = (1..2).random()

        val questionText: TextView = this.findViewById(R.id.tv_question_content)

        if(arr[0] == "eye color") {
            // mode2 = 1 guaranties that this answer should be true
            if(mode2 == 1) {
                questionText.text =
                    "Is ${peopleInQuestion?.eye_color} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
            else{
                questionText.text =
                    "Is ${arr[arrIdx]} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
        }
        if(arr[0] == "skin color") {
            if(mode2 == 1) {
                questionText.text =
                    "Is ${peopleInQuestion?.skin_color} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
            else{
                questionText.text =
                    "Is ${arr[arrIdx]} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
        }
        if(arr[0] == "hair color"){
            if(mode2 == 1) {
                questionText.text =
                    "Is ${peopleInQuestion?.hair_color} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
            else{
                questionText.text =
                    "Is ${arr[arrIdx]} the ${arr[0]} of ${peopleInQuestion?.name}?"
            }
        }

        if(intent.hasExtra(RIGHT_NUM)){
            userGetRightNum.text = "Correct: ${currentRightNum}"
        }
        if(intent.hasExtra(WRONG_NUM)){
            userGetWrongNum.text = "Wrong: ${currentWrongNum}"
        }

        val intentSuccess = Intent(this, PeopleQuizSuccess::class.java).apply{
            putExtra(RIGHT_NUM, currentRightNum + 1)
            putExtra(WRONG_NUM,currentWrongNum)
            putExtra(USERNAME, currentUsername)
        }
        val intentFailure = Intent(this, PeopleQuizFailure::class.java).apply{
            putExtra(WRONG_NUM, currentWrongNum + 1)
            putExtra(RIGHT_NUM,currentRightNum)
            putExtra(USERNAME, currentUsername)
        }

        buttonTrue.setOnClickListener {
            var trueOrFalse = answer_correct_or_wrong(arr,arrIdx,mode2)
            startActivity(if(trueOrFalse == 1) intentSuccess else intentFailure)
            true
        }
        buttonFalse.setOnClickListener {
            var trueOrFalse = answer_correct_or_wrong(arr,arrIdx,mode2)
            startActivity(if(trueOrFalse == 2) intentSuccess else intentFailure)
            true
        }

    }
    fun answer_correct_or_wrong(arr: Array<String>, arrIdx: Int, mode: Int): Int{


        if(mode == 1)
            return 1;
        if(arr[0] == "eye color" ) {

            if (arr[arrIdx] == peopleInQuestion?.eye_color) {
                return 1;
            }
            return 2;
        }
        else if(arr[0] == "hair color" ) {
            if (arr[arrIdx] == peopleInQuestion?.hair_color) {
                return 1;
            }
            return 2;
        }
        else {
            if (arr[arrIdx] == peopleInQuestion?.skin_color) {
                return 1;
            }
            return 2;
        }
    }
}