package com.zybooks.dndcharactercompanion

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @NonNull
    var hp: Int,
    @NonNull
    var ac: Int,
    @NonNull
    var name: String,
    @NonNull
    var race: String,
    @NonNull
    var characterClass: String,
    @NonNull
    var level: Int,
    @NonNull
    var strength: Int,
    @NonNull
    var dexterity: Int,
    @NonNull
    var constitution: Int,
    @NonNull
    var intelligence: Int,
    @NonNull
    var wisdom: Int,
    @NonNull
    var charisma: Int,
    @NonNull
    var proficiencyList:MutableList<String>




    )
