package com.zybooks.dndcharactercompanion.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zybooks.dndcharactercompanion.model.Character

@Database(entities = [Character::class], version = 1)
abstract class CharacterDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}