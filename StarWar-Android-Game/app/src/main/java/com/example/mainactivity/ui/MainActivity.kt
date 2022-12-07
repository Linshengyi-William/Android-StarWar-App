package com.example.mainactivity.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.mainactivity.R
import com.example.mainactivity.SettingActivity
import com.example.mainactivity.databinding.ActivityMainBinding

import androidx.activity.viewModels
import com.example.mainactivity.data.People


class MainActivity : AppCompatActivity() {

    private val viewModel: PeopleViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)



        val fightClick = findViewById<Button>(R.id.fight_button)
        fightClick.setOnClickListener{
            val intent = Intent(this, FightingQuizLandingPage::class.java)
            startActivity(intent)
            true
        }

        val peopleQuizClick = findViewById<Button>(R.id.people_button)
        peopleQuizClick.setOnClickListener{
            val intent = Intent(this, PeopleQuizLanding::class.java)
            startActivity(intent)
            true
        }
        viewModel.loadPeopleResults(1)


        val filmClick = findViewById<Button>(R.id.film_button)
        filmClick.setOnClickListener {
            val intent = Intent(this, FilmQuizLandingPage::class.java)
            startActivity(intent)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_media -> {
                val intent = Intent(this, MediaActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //override fun onSupportNavigateUp(): Boolean {
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        //return navController.navigateUp(appBarConfiguration)
                //|| super.onSupportNavigateUp()
    //}

    private fun onPeopleQuizClick(peopleQuiz: People) {
        val intent = Intent(this, PeopleQuizActivity::class.java).apply {
            //putExtra(EXTRA_PEOPLE_QUIZ, peopleQuiz)
        }
//        intent.putExtra(EXTRA_GITHUB_REPO, repo)
        startActivity(intent)
    }
}