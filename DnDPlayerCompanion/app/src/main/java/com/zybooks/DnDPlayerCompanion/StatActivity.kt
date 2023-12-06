package com.zybooks.DnDPlayerCompanion

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zybooks.DnDPlayerCompanion.model.Stats
import com.zybooks.DnDPlayerCompanion.model.Character
import com.zybooks.DnDPlayerCompanion.viewmodel.StatListViewModel
import androidx.lifecycle.ViewModelProvider
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class StatActivity : AppCompatActivity() {

    private val statListViewModel: StatListViewModel by lazy {
        ViewModelProvider(this).get(StatListViewModel::class.java)
    }
    private lateinit var character: Character
    private lateinit var statsList: Stats

    private lateinit var nameTextView: TextView
    private lateinit var  classTextView: TextView
    private lateinit var raceTextView: TextView
    private lateinit var levelTextView: TextView
    private lateinit var hpTextView: TextView
    private lateinit var acTextView: TextView
    private lateinit var strButton: Button
    private lateinit var dexButton: Button
    private lateinit var conButton: Button
    private lateinit var intButton: Button
    private lateinit var wisButton: Button
    private lateinit var chaButton: Button

    private var currentQuestionIndex = 0
    private val addStatResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            // toast when stats are upadated

            Toast.makeText(this, R.string.stat_updated, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_CHARACTER_ID = "com.zybooks.studyhelper.character_id"
        const val EXTRA_CHARACTER_TEXT = "com.zybooks.studyhelper.character_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        nameTextView = findViewById(R.id.characterName)
        classTextView = findViewById(R.id.characterClass)
        raceTextView = findViewById(R.id.characterRace)
        hpTextView = findViewById(R.id.characterHealth)
        acTextView = findViewById(R.id.characterAC)
        levelTextView = findViewById(R.id.characterLevel)
        strButton = findViewById(R.id.strengthButton)
        dexButton = findViewById(R.id.dexterityButton)
        conButton = findViewById(R.id.constitutionButton)
        intButton = findViewById(R.id.intelligenceButton)
        wisButton = findViewById(R.id.wisdomButton)
        chaButton = findViewById(R.id.charismaButton)


        // Button Callbacks
        strButton.setOnClickListener { rollDice(statsList.strength.toInt())}
        dexButton.setOnClickListener { rollDice(statsList.dexterity.toInt())}
        conButton.setOnClickListener { rollDice(statsList.constitution.toInt())}
        intButton.setOnClickListener { rollDice(statsList.intelligence.toInt())}
        wisButton.setOnClickListener { rollDice(statsList.wisdom.toInt())}
        chaButton.setOnClickListener { rollDice(statsList.charisma.toInt())}


        // Getting the Id from Character
        val characterId = intent.getLongExtra(EXTRA_CHARACTER_ID, 0)
        val characterText = intent.getStringExtra(EXTRA_CHARACTER_TEXT)
        character = Character(characterId, characterText!!)

        // Get Stats for the character
        statsList = Stats()
        statListViewModel.loadStats(characterId)
        statListViewModel.statsListLiveData.observe(
            this, { statsList ->
                if (statsList != null) {
                    this.statsList = statsList
                }
                if(statsList == null)
                {
                    addStat()
                }
                updateUI()
            })

        // Display question
    }

    private fun updateUI() {
            nameTextView.setText(statsList.name)
            classTextView.setText(statsList.DNDClass)
            raceTextView.setText(statsList.race)
            hpTextView.setText(statsList.heath)
            acTextView.setText(statsList.ac)
            levelTextView.setText(statsList.level)
            strButton.setText(statsList.strength)
            dexButton.setText(statsList.dexterity)
            conButton.setText(statsList.constitution)
            intButton.setText(statsList.intelligence)
            wisButton.setText(statsList.wisdom)
            chaButton.setText(statsList.charisma)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.stat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.edit -> {
                editStat()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun rollDice(att: Int)
    {
        val intent = Intent(this, DiceActivity::class.java)
        intent.putExtra("EXTRA_CHARACTER_SKILL", att)
        intent.putExtra("EXTRA_CHARACTER_LEVEL", statsList.level.toInt())
        startActivity(intent)
    }
    private fun addStat() {
        val intent = Intent(this, StatEditActivity::class.java)
        intent.putExtra(EXTRA_CHARACTER_ID, character.id)
        intent.putExtra(EXTRA_CHARACTER_TEXT, character.text)
        addStatResultLauncher.launch(intent)
    }
    private val editStatResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, R.string.stat_updated, Toast.LENGTH_SHORT).show()
        }
    }
    private fun editStat() {
        if (currentQuestionIndex >= 0) {
            val intent = Intent(this, StatEditActivity::class.java)
            intent.putExtra(StatEditActivity.EXTRA_STAT_ID, statsList.id)
            editStatResultLauncher.launch(intent)
        }
    }

}