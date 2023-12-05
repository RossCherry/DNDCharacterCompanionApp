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

    val EXTRA_CHARACTER_SKILL = 0;
    val EXTRA_CHARCTER_LEVEL = 0;
    private lateinit var deletedStats: Stats
    private var currentQuestionIndex = 0
    private val addStatResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            // Display the added question, which will appear at end of list
            //currentQuestionIndex = statsList

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


        // Add click callbacks
        strButton.setOnClickListener { rollDice(statsList.strength.toInt())}
        dexButton.setOnClickListener { rollDice(statsList.dexterity.toInt())}
        conButton.setOnClickListener { rollDice(statsList.constitution.toInt())}
        intButton.setOnClickListener { rollDice(statsList.intelligence.toInt())}
        wisButton.setOnClickListener { rollDice(statsList.wisdom.toInt())}
        chaButton.setOnClickListener { rollDice(statsList.charisma.toInt())}
        

        // SubjectActivity should provide the subject ID and text
        val characterId = intent.getLongExtra(EXTRA_CHARACTER_ID, 0)
        val characterText = intent.getStringExtra(EXTRA_CHARACTER_TEXT)
        character = Character(characterId, characterText!!)

        // Get all questions for this subject
        statsList = Stats()
        //statsList.characterId = characterId
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
        //showQuestion(currentQuestionIndex)
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

        //  Determine which app bar item was chosen
        return when (item.itemId) {
/*            R.id.previous -> {
                showQuestion(currentQuestionIndex - 1)
                true
            }
            R.id.next -> {
                showQuestion(currentQuestionIndex + 1)
                true
            }
            R.id.add -> {
                addQuestion()
                true
            }*/
            R.id.edit -> {
                editStat()
                true
            }
/*            R.id.delete -> {
                deleteQuestion()
                true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
    }

/*    private fun displayQuestion(display: Boolean) {
        if (display) {
            showQuestionLayout.visibility = View.VISIBLE
            noQuestionLayout.visibility = View.GONE
        } else {
            showQuestionLayout.visibility = View.GONE
            noQuestionLayout.visibility = View.VISIBLE
        }
    }*/

/*    private fun updateAppBarTitle() {

        // Display subject and number of questions in app bar
        val title = resources.getString(R.string.question_number, character.text,
            currentQuestionIndex + 1, statsList.size)
        setTitle(title)
    }*/

    private fun rollDice(att: Int)
    {
        val intent = Intent(this, DiceActivity::class.java)
            intent.putExtra("EXTRA_CHARACTER_SKILL", att)
            intent.putExtra("EXTRA_CHARCTER_LEVEL", statsList.level)
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

    private fun deleteQuestion() {
        if (currentQuestionIndex >= 0) {
            val stat = statsList
            statListViewModel.deleteStat(stat)

            // Save question in case user wants to undo delete
            deletedStats = stat

            // Show delete message with Undo button
            /*val snackbar = Snackbar.make(
                findViewById(R.id.coordinator_layout),
                R.string.stat_deleted, Snackbar.LENGTH_LONG
            )*/
            /*snackbar.setAction(R.string.undo) {
                // Add question back
                statListViewModel.addQuestion(deletedStats)
            }
            snackbar.show()*/
        }
    }

   /* private fun showQuestion(questionIndex: Int) {

        // Show question at the given index
        if (statsList.isNotEmpty()) {
            var newQuestionIndex = questionIndex

            if (questionIndex < 0) {
                newQuestionIndex = statsList.size - 1
            } else if (questionIndex >= statsList.size) {
                newQuestionIndex = 0
            }

            currentQuestionIndex = newQuestionIndex
            updateAppBarTitle()

            val stat = statsList[currentQuestionIndex]
            questionTextView.text = stat.name
            answerTextView.text = stat.DNDClass
        } else {
            // No questions yet
            currentQuestionIndex = -1
        }
    }*/

    /*private fun toggleAnswerVisibility() {
        if (answerTextView.visibility == View.VISIBLE) {
            answerButton.setText(R.string.show_answer)
            answerTextView.visibility = View.INVISIBLE
            answerLabelTextView.visibility = View.INVISIBLE
        } else {
            answerButton.setText(R.string.hide_answer)
            answerTextView.visibility = View.VISIBLE
            answerLabelTextView.visibility = View.VISIBLE
        }
    }*/
}