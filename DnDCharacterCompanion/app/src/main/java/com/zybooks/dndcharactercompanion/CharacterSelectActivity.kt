package com.zybooks.dndcharactercompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterSelectActivity : AppCompatActivity() {
    private lateinit var characterRecyclerView: RecyclerView
    private lateinit var characterColors: IntArray
    private var characterAdapter = CharacterAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_select)

        characterColors = resources.getIntArray(R.array.characterColors)

        characterRecyclerView = findViewById(R.id.character_recycler_view)
        characterRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)

        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.select_menu, menu)
        return true
    }

    private fun updateUI() {
        characterAdapter = CharacterAdapter()
        characterRecyclerView.adapter = characterAdapter
    }

    private inner class CharacterHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items, parent, false))/*,
        View.OnClickListener*/ {

        //private var character: Subject? = null
        private val characterTextView: TextView

        init {
            //itemView.setOnClickListener(this)
            characterTextView = itemView.findViewById(R.id.character_text_view)
        }

        fun bind(/*subject: Subject, position: Int*/) {
            //this.subject = subject
            characterTextView.text = "Sir Lancelot \n Level: 420 \n Class: Fighter"

            // Make the background color dependent on the length of the subject string
            val colorIndex = characterTextView.text.length % characterColors.size
            characterTextView.setBackgroundColor(characterColors[colorIndex])
        }
    }

    private inner class CharacterAdapter() :
        RecyclerView.Adapter<CharacterHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val layoutInflater = LayoutInflater.from(applicationContext)
            return CharacterHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
            holder.bind()
        }

        override fun getItemCount(): Int {
            return 1
        }
    }

}