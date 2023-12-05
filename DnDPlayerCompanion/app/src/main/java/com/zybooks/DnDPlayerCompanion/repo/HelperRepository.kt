package com.zybooks.DnDPlayerCompanion.repo

import androidx.lifecycle.LiveData
import android.content.Context
import androidx.room.Room
import com.zybooks.DnDPlayerCompanion.model.Stats
import com.zybooks.DnDPlayerCompanion.model.Character

class HelperRepository private constructor(context: Context) {

    companion object {
        private var instance: HelperRepository? = null

        fun getInstance(context: Context): HelperRepository {
            if (instance == null) {
                instance = HelperRepository(context)
            }
            return instance!!
        }
    }

    private val database : HelperDatabase = Room.databaseBuilder(
        context.applicationContext,
        HelperDatabase::class.java,
        "DNDhelper.db"
    )
        .allowMainThreadQueries()
        .build()

    private val characterDao = database.characterDao()
    private val statDao = database.statDao()



    fun getCharacters(): LiveData<List<Character>> = characterDao.getCharacters()


    fun addCharacter(character: Character) {
        character.id = characterDao.addCharacter(character)
    }

    fun deleteCharacter(character: Character) = characterDao.deleteCharacter(character)

    fun getStat(statId: Long): LiveData<Stats?> = statDao.getStat(statId)

    fun getStats(characterId: Long): LiveData<Stats?> = statDao.getStats(characterId)

    fun addStat(stats: Stats) {
        stats.id = statDao.addStat(stats)
    }

    fun updateStat(stats: Stats) = statDao.updateStat(stats)

    fun deleteStat(stats: Stats) = statDao.deleteStat(stats)

}