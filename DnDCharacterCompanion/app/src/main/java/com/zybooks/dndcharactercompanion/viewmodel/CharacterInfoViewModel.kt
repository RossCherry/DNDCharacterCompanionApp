package com.zybooks.dndcharactercompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zybooks.dndcharactercompanion.model.Character
import com.zybooks.dndcharactercompanion.repo.CharacterRepo

class CharacterInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val characterRepo = CharacterRepo.getInstance(application)

    private val characterIdLiveData = MutableLiveData<Long>()

    val characterLiveData: LiveData<Character?> =
        Transformations.switchMap(characterIdLiveData) {characterId ->
            characterRepo.getCharacter(characterId)
        }

    fun loadCharacter(characterId: Long) {
        characterIdLiveData.value = characterId
    }

    fun addCharacter(character: Character) = characterRepo.addCharacter(character)
}