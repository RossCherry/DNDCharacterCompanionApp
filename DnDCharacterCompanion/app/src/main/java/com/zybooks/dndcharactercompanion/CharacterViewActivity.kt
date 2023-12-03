package com.zybooks.dndcharactercompanion

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zybooks.dndcharactercompanion.model.Character
import com.zybooks.dndcharactercompanion.viewmodel.CharacterInfoViewModel

class CharacterViewActivity : AppCompatActivity() {
    private val characterInfoViewModel: CharacterInfoViewModel by lazy {
        ViewModelProvider(this).get(CharacterInfoViewModel::class.java)
    }

    private lateinit var character: Character
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

    companion object{
        const val EXTRA_CHARACTER_ID = "com.zybooks.dndcharactercompanion.character_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_view)

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

        val characterId = intent.getLongExtra(EXTRA_CHARACTER_ID, 0)

        characterInfoViewModel.loadCharacter(characterId)
        characterInfoViewModel.characterLiveData.observe(
            this, {character ->
                if (character != null) {
                    this.character = character
                }
                updateUI()
            }
        )
    }

    private fun updateUI(){
        nameTextView.setText(character.name)
        classTextView.setText(character.characterClass)
        raceTextView.setText(character.race)
        hpTextView.setText(character.hp)
        acTextView.setText(character.ac)
        levelTextView.setText(character.level)
        strButton.setText(character.strength)
        dexButton.setText(character.dexterity)
        conButton.setText(character.constitution)
        intButton.setText(character.intelligence)
        wisButton.setText(character.wisdom)
        chaButton.setText(character.charisma)
    }
}