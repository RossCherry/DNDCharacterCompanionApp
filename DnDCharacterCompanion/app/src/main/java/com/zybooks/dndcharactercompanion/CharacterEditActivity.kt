package com.zybooks.dndcharactercompanion
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
    private lateinit var profCheckBox: List<String>

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

        profCheckBox = profList()

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
        
    }

    fun profList(): List<String>
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
    }

}