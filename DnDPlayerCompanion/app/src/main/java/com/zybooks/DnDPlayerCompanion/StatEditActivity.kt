
package com.zybooks.DnDPlayerCompanion

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zybooks.DnDPlayerCompanion.model.Stats
import androidx.lifecycle.ViewModelProvider
import com.zybooks.DnDPlayerCompanion.viewmodel.StatDetailViewModel

class StatEditActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var classEditText: EditText
    private lateinit var raceEditText: EditText
    private lateinit var hpEditText: EditText
    private lateinit var acEditText: EditText
    private lateinit var levelEditText: EditText
    private lateinit var strEditText: EditText
    private lateinit var dexEditText: EditText
    private lateinit var conEditText: EditText
    private lateinit var intEditText: EditText
    private lateinit var wisEditText: EditText
    private lateinit var chaEditText: EditText
    private var statId = 0L
    private lateinit var stats: Stats
    private val statDetailViewModel: StatDetailViewModel by lazy {
        ViewModelProvider(this).get(StatDetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_STAT_ID = "com.zybooks.studyhelper.stat_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_edit)

        nameEditText = findViewById(R.id.characterName)
        classEditText = findViewById(R.id.characterClass)
        raceEditText = findViewById(R.id.characterRace)
        hpEditText = findViewById(R.id.characterHealth)
        acEditText = findViewById(R.id.characterAC)
        levelEditText = findViewById(R.id.characterLevel)
        strEditText = findViewById(R.id.characterSTR)
        dexEditText = findViewById(R.id.characterDEX)
        conEditText = findViewById(R.id.characterCON)
        intEditText = findViewById(R.id.characterINT)
        wisEditText = findViewById(R.id.characterWIS)
        chaEditText = findViewById(R.id.characterCHA)

        findViewById<FloatingActionButton>(R.id.save_button).setOnClickListener { saveButtonClick() }

        // Get the id for the stat tied to a character
        statId = intent.getLongExtra(EXTRA_STAT_ID, -1L)

        if (statId == -1L) {
            // Create new stat block for a new character
            stats = Stats()
            stats.characterId = intent.getLongExtra(StatActivity.EXTRA_CHARACTER_ID, 0)
            stats.name = intent.getStringExtra(StatActivity.EXTRA_CHARACTER_TEXT)!!
            nameEditText.setText(stats.name)
            setTitle(R.string.add_stat)
        } else {
            // If stats exist display them
            statDetailViewModel.loadStat(statId)
            statDetailViewModel.statsLiveData.observe(this,
                { stat ->
                    if (stat != null) {
                        this.stats = stat
                    }
                    updateUI()
                })

            setTitle(R.string.edit_stat)
        }
    }

    private fun updateUI() {
        nameEditText.setText(stats.name)
        classEditText.setText(stats.DNDClass)
        raceEditText.setText(stats.race)
        hpEditText.setText(stats.heath)
        acEditText.setText(stats.ac)
        levelEditText.setText(stats.level)
        strEditText.setText(stats.strength)
        dexEditText.setText(stats.dexterity)
        conEditText.setText(stats.constitution)
        intEditText.setText(stats.intelligence)
        wisEditText.setText(stats.wisdom)
        chaEditText.setText(stats.charisma)
    }

    private fun saveButtonClick() {
        stats.name = nameEditText.text.toString()
        stats.DNDClass = classEditText.text.toString()
        stats.race = raceEditText.text.toString()
        stats.heath = hpEditText.text.toString()
        stats.ac = acEditText.text.toString()
        stats.level = levelEditText.text.toString()
        stats.strength = strEditText.text.toString()
        stats.dexterity = dexEditText.text.toString()
        stats.constitution = conEditText.text.toString()
        stats.intelligence = intEditText.text.toString()
        stats.wisdom = wisEditText.text.toString()
        stats.charisma = chaEditText.text.toString()

        if (statId == -1L) {
            statDetailViewModel.addStat(stats)
        } else {
            statDetailViewModel.updateStat(stats)
        }

        // Return ok
        setResult(RESULT_OK)
        finish()
    }
}
