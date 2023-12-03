package com.zybooks.dndcharactercompanion.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,


    var hp: String = "",

    var ac: String = "",

    var name: String = "",

    var race: String = "",

    var characterClass: String = "",

    var level: String = "",

    var strength: String = "",

    var dexterity: String = "",

    var constitution: String = "",

    var intelligence: String = "",

    var wisdom: String = "",

    var charisma: String = "",

    //var proficiencyList:MutableList<String> = mutableListOf<String>()




    )
