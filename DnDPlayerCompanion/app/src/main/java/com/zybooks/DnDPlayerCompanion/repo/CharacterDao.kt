package com.zybooks.DnDPlayerCompanion.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zybooks.DnDPlayerCompanion.model.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM Character WHERE id = :id")
    fun getCharacter(id: Long): LiveData<Character?>

    @Query("SELECT * FROM Character ORDER BY text COLLATE NOCASE")
    fun getCharacters(): LiveData<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacter(character: Character): Long

    @Update
    fun updateCharacter(character: Character)

    @Delete
    fun deleteCharacter(character: Character)
}