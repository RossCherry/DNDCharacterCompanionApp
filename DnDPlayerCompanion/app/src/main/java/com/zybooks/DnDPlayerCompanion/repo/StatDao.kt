package com.zybooks.DnDPlayerCompanion.repo

import androidx.room.*
import com.zybooks.DnDPlayerCompanion.model.Stats
import androidx.lifecycle.LiveData

@Dao
interface StatDao {
    @Query("SELECT * FROM Stats WHERE id = :id")
    fun getStat(id: Long): LiveData<Stats?>

    @Query("SELECT * FROM Stats WHERE character_id = :characterId ORDER BY id")
    fun getStats(characterId: Long): LiveData<Stats?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStat(stats: Stats): Long

    @Update
    fun updateStat(stats: Stats)

    @Delete
    fun deleteStat(stats: Stats)
}