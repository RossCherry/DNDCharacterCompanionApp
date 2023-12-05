package com.zybooks.DnDPlayerCompanion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = Character::class,
        parentColumns = ["id"],
        childColumns = ["character_id"],
        onDelete = CASCADE)
])

data class Stats(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var DNDClass: String = "",
    var race: String = "",
    var level: String = "",
    var heath: String = "",
    var ac: String = "",
    var strength: String = "",
    var dexterity: String = "",
    var constitution: String = "",
    var intelligence: String = "",
    var wisdom: String = "",
    var charisma: String = "",
    @ColumnInfo(name = "character_id")
    var characterId: Long = 0) {
}