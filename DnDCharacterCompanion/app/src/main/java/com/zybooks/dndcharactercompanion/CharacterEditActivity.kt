package com.zybooks.dndcharactercompanion
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.zybooks.dndcharactercompanion.model.Character

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
    companion object{
        const val EXTRA_CHARACTER_ID = "com.zybooks.dndcharactercompanion.character_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_edit)
    }


}