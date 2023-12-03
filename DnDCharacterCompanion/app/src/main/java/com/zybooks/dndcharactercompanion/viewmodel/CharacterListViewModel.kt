package com.zybooks.dndcharactercompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zybooks.dndcharactercompanion.model.Character

import com.zybooks.dndcharactercompanion.repo.CharacterRepo

class CharacterListViewModel(application: Application) :AndroidViewModel(application) {
    private val characterRepo = CharacterRepo.getInstance(application.applicationContext)

    val characterListLiveData: LiveData<List<Character>> = characterRepo.getCharacters()

    fun addCharacter(character: Character) = characterRepo.addCharacter(character)
}