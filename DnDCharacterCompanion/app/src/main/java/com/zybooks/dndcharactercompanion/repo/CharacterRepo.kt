package com.zybooks.dndcharactercompanion.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.zybooks.dndcharactercompanion.model.Character

class CharacterRepo private constructor(context: Context) {

    companion object {
        private var instance: CharacterRepo? = null

        fun getInstance(context: Context): CharacterRepo {
            if (instance == null) {
                instance = CharacterRepo(context)
            }
            return instance!!
        }
    }

    private val database: CharacterDatabase = Room.databaseBuilder(
        context.applicationContext,
        CharacterDatabase::class.java,
        "DND.db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    private val characterDao = database.characterDao()

    fun getCharacter(characterId: Long): LiveData<Character?> =
        characterDao.getCharacter(characterId)

    fun getCharacters(): LiveData<List<Character>> = characterDao.getCharacters()

    fun addCharacter(character: Character) {
        character.id = characterDao.addCharacter(character)
    }

    fun updateCharacter(character: Character) = characterDao.updateCharacter(character)
}
