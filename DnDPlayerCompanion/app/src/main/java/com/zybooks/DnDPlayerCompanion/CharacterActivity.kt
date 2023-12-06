package com.zybooks.DnDPlayerCompanion

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zybooks.DnDPlayerCompanion.model.Character
import com.zybooks.DnDPlayerCompanion.viewmodel.CharacterListViewModel
import androidx.lifecycle.ViewModelProvider
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.graphics.Color



class CharacterActivity : AppCompatActivity(),
    CharacterDialogFragment.OnSubjectEnteredListener {

    private var subjectAdapter = SubjectAdapter(mutableListOf())
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var characterColors: IntArray
    private lateinit var selectedCharacter: Character
    private var selectedCharacterPosition = RecyclerView.NO_POSITION
    private var actionMode: ActionMode? = null
    private var loadCharacterList = true
    private val characterListViewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this).get(CharacterListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        characterListViewModel.characterListLiveData.observe(
            this, { characterList ->
                if (loadCharacterList) {
                    updateUI(characterList)
                }
            })

        characterColors = resources.getIntArray(R.array.characterColors)

        findViewById<FloatingActionButton>(R.id.add_character_button).setOnClickListener {
            addCharacterClick() }

        characterRecyclerView = findViewById(R.id.character_recycler_view)
        characterRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.about -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder
                    .setMessage("This app allows the user to create and save a DND 5e character, " +
                            "and roll a D20 based on their character's stats. \n \n Created by: "
                    + "Ross Cherry and Jack Johnson")
                    .setTitle("About")

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun updateUI(subjectList: List<Character>) {
        subjectAdapter = SubjectAdapter(subjectList as MutableList<Character>)
        characterRecyclerView.adapter = subjectAdapter
    }

    override fun onSubjectEntered(characterText: String) {
        if (characterText.isNotEmpty()) {
            val character = Character(0, characterText)
            // Stop updateUI() from being called
            loadCharacterList = false
            characterListViewModel.addSubject(character)
            // Add subject to RecyclerView
            subjectAdapter.addCharacter(character)
            Toast.makeText(this, "Added $characterText", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addCharacterClick() {
        val dialog = CharacterDialogFragment()
        dialog.show(supportFragmentManager, "characterDialog")
    }

    private inner class CharacterHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items, parent, false)),
        View.OnLongClickListener,
        View.OnClickListener {

        private var character: Character? = null
        private val characterTextView: TextView

        init {
            itemView.setOnClickListener(this)
            characterTextView = itemView.findViewById(R.id.subject_text_view)
            itemView.setOnLongClickListener(this)
        }

        fun bind(character: Character, position: Int) {
            this.character = character
            characterTextView.text = character.text
            if (selectedCharacterPosition == position) {
                // change color of selected character
                characterTextView.setBackgroundColor(Color.RED)
            }
            else {
                // change background color based on name length
                val colorIndex = character.text.length % characterColors.size
                characterTextView.setBackgroundColor(characterColors[colorIndex])
            }
        }
        override fun onLongClick(view: View): Boolean {
            if (actionMode != null) {
                return false
            }

            selectedCharacter = character!!
            selectedCharacterPosition = absoluteAdapterPosition

            // Re-bind the selected item
            subjectAdapter.notifyItemChanged(selectedCharacterPosition)

            // CAB
            actionMode = this@CharacterActivity.startActionMode(actionModeCallback)
            return true
        }
        override fun onClick(view: View) {
            // Open stats
            val intent = Intent(this@CharacterActivity, StatActivity::class.java)
            intent.putExtra(StatActivity.EXTRA_CHARACTER_ID, character!!.id)
            intent.putExtra(StatActivity.EXTRA_CHARACTER_TEXT, character!!.text)

            startActivity(intent)
        }
    }
    private val actionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // CAB context
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            if (item.itemId == R.id.delete) {
                loadCharacterList = false

                // Delete form VM
                characterListViewModel.deleteSubject(selectedCharacter)

                // delete from RM
                subjectAdapter.removeCharacter(selectedCharacter)

                // Close CAB
                mode.finish()
                return true
            }

            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null

            // deselect when cab closed
            subjectAdapter.notifyItemChanged(selectedCharacterPosition)
            selectedCharacterPosition = RecyclerView.NO_POSITION
        }
    }
    private inner class SubjectAdapter(private val characterList: MutableList<Character>) :
        RecyclerView.Adapter<CharacterHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val layoutInflater = LayoutInflater.from(applicationContext)
            return CharacterHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
            holder.bind(characterList[position], position)
        }

        override fun getItemCount(): Int {
            return characterList.size
        }
        fun addCharacter(character: Character) {

            // new character goes to top of the list
            characterList.add(0, character)

            // notify that the character is added
            notifyItemInserted(0)

            // move to top
            characterRecyclerView.scrollToPosition(0)
        }
        fun removeCharacter(character: Character) {

            // locate character
            val index = characterList.indexOf(character)
            if (index >= 0) {

                // delete character
                characterList.removeAt(index)

                // notify of delete
                notifyItemRemoved(index)
            }
        }
    }
}