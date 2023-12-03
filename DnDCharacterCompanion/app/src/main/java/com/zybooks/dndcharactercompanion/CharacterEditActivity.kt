package com.zybooks.dndcharactercompanion
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zybooks.dndcharactercompanion.model.Character
import com.zybooks.dndcharactercompanion.viewmodel.CharacterDetailViewModel

class CharacterEditActivity : AppCompatActivity() {
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
    //private lateinit var profCheckBox: List<String>

    private var characterId = 0L
    private lateinit var character: Character

    private val characterDetailViewModel: CharacterDetailViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
    }
    companion object{
        const val EXTRA_CHARACTER_ID = "com.zybooks.dndcharactercompanion.character_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_edit)

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

        //profCheckBox = profList()

        characterId = intent.getLongExtra(EXTRA_CHARACTER_ID, -1L)

        if(characterId == -1L)
        {
            character = Character()
            title = "Create a Character"
        }
        else
        {
            characterDetailViewModel.loadCharacter(characterId)
            characterDetailViewModel.characterLiveData.observe(this,
                { character ->
                    if(character != null){
                        this.character = character
                    }
                    updateUI()
                })
            setTitle("Edit Character")
        }
    }

    private fun updateUI(){
        nameEditText.setText(character.name)
        classEditText.setText(character.characterClass)
        raceEditText.setText(character.race)
        hpEditText.setText(character.hp)
        acEditText.setText(character.ac)
        levelEditText.setText(character.level)
        strEditText.setText(character.strength)
        dexEditText.setText(character.dexterity)
        conEditText.setText(character.constitution)
        intEditText.setText(character.intelligence)
        wisEditText.setText(character.wisdom)
        chaEditText.setText(character.charisma)
        //updateCheckBox()
    }

    /*fun updateCheckBox(){
        for(prof in profCheckBox)
        {
            if(prof == "acrobatics")
            {
                findViewById<CheckBox>(R.id.characterAcrobatics).isChecked = true
            }
            else if(prof == "athletics")
            {
                findViewById<CheckBox>(R.id.characterAthletics).isChecked = true
            }
            else if(prof == "animal handling")
            {
                findViewById<CheckBox>(R.id.characterAnHand).isChecked = true
            }
            else if(prof == "arcana")
            {
                findViewById<CheckBox>(R.id.characterArcana).isChecked = true
            }
            else if(prof == "deception")
            {
                findViewById<CheckBox>(R.id.characterDeception).isChecked = true
            }
            else if(prof == "history")
            {
                findViewById<CheckBox>(R.id.characterHistory).isChecked = true
            }
            else if(prof == "insight")
            {
                findViewById<CheckBox>(R.id.characterInsight).isChecked = true
            }
            else if(prof == "intimidation")
            {
                findViewById<CheckBox>(R.id.characterIntimidation).isChecked = true
            }
            else if(prof == "investigation")
            {
                findViewById<CheckBox>(R.id.characterInvestigation).isChecked = true
            }
            else if(prof == "medicine")
            {
                findViewById<CheckBox>(R.id.characterMedicine).isChecked = true
            }
            else if(prof == "nature")
            {
                findViewById<CheckBox>(R.id.characterNature).isChecked = true
            }
            else if(prof == "perception")
            {
                findViewById<CheckBox>(R.id.characterPerception).isChecked = true
            }
            else if(prof == "persuasion")
            {
                findViewById<CheckBox>(R.id.characterPersuasion).isChecked = true
            }
            else if(prof == "religion")
            {
                findViewById<CheckBox>(R.id.characterReligion).isChecked = true
            }
            else if(prof == "slight of hand")
            {
                findViewById<CheckBox>(R.id.characterSoH).isChecked = true
            }
            else if(prof == "stealth")
            {
                findViewById<CheckBox>(R.id.characterStealth).isChecked = true
            }
            else if(prof == "survival")
            {
                findViewById<CheckBox>(R.id.characterSurvival).isChecked = true
            }

        }
    }*/
    /*fun profList(): MutableList<String>
    {
        var skillList = mutableListOf<String>()
        if(findViewById<CheckBox>(R.id.characterAcrobatics).isChecked)
        {
            skillList.add("acrobatics")
        }
        if(findViewById<CheckBox>(R.id.characterAthletics).isChecked)
        {
            skillList.add("athletics")
        }
        if(findViewById<CheckBox>(R.id.characterAnHand).isChecked)
        {
            skillList.add("animal handling")
        }
        if(findViewById<CheckBox>(R.id.characterArcana).isChecked)
        {
            skillList.add("arcana")
        }
        if(findViewById<CheckBox>(R.id.characterDeception).isChecked)
        {
            skillList.add("deception")
        }
        if(findViewById<CheckBox>(R.id.characterHistory).isChecked)
        {
            skillList.add("history")
        }
        if(findViewById<CheckBox>(R.id.characterInsight).isChecked)
        {
            skillList.add("insight")
        }
        if(findViewById<CheckBox>(R.id.characterIntimidation).isChecked)
        {
            skillList.add("intimidation")
        }
        if(findViewById<CheckBox>(R.id.characterInvestigation).isChecked)
        {
            skillList.add("investigation")
        }
        if(findViewById<CheckBox>(R.id.characterMedicine).isChecked)
        {
            skillList.add("medicine")
        }
        if(findViewById<CheckBox>(R.id.characterNature).isChecked)
        {
            skillList.add("nature")
        }
        if(findViewById<CheckBox>(R.id.characterPerception).isChecked)
        {
            skillList.add("perception")
        }
        if(findViewById<CheckBox>(R.id.characterPerformance).isChecked)
        {
            skillList.add("performance")
        }
        if(findViewById<CheckBox>(R.id.characterPersuasion).isChecked)
        {
            skillList.add("persuasion")
        }
        if(findViewById<CheckBox>(R.id.characterReligion).isChecked)
        {
            skillList.add("religion")
        }
        if(findViewById<CheckBox>(R.id.characterSoH).isChecked)
        {
            skillList.add("slight of hand")
        }
        if(findViewById<CheckBox>(R.id.characterStealth).isChecked)
        {
            skillList.add("stealth")
        }
        if(findViewById<CheckBox>(R.id.characterSurvival).isChecked)
        {
            skillList.add("survival")
        }

        return skillList
    }*/

    private fun saveButtonClick(){
        character.name = nameEditText.text.toString()
        character.characterClass = classEditText.text.toString()
        character.race = raceEditText.text.toString()
        character.hp = hpEditText.text.toString()
        character.ac = acEditText.text.toString()
        character.level = levelEditText.text.toString()
        character.strength = strEditText.text.toString()
        character.dexterity = dexEditText.text.toString()
        character.constitution = conEditText.text.toString()
        character.intelligence = intEditText.text.toString()
        character.wisdom = wisEditText.text.toString()
        character.charisma = chaEditText.text.toString()
       //character.proficiencyList = profList()

        if(characterId == -1L){
            characterDetailViewModel.addCharacter(character)
        } else {
            characterDetailViewModel.updateCharacter(character)
        }

        setResult(RESULT_OK)
        finish()

    }

}