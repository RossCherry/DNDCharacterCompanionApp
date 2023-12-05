package com.zybooks.DnDPlayerCompanion.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zybooks.DnDPlayerCompanion.model.Character
import com.zybooks.DnDPlayerCompanion.model.Stats

@Database(entities = [Stats::class, Character::class], version = 1)
abstract class HelperDatabase : RoomDatabase() {

    abstract fun statDao(): StatDao
    abstract fun characterDao(): CharacterDao
}