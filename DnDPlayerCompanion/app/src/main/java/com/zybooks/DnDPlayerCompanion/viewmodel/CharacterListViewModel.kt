package com.zybooks.DnDPlayerCompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zybooks.DnDPlayerCompanion.model.Character
import com.zybooks.DnDPlayerCompanion.repo.HelperRepository

class CharacterListViewModel(application: Application) : AndroidViewModel(application) {

    private val studyRepo = HelperRepository.getInstance(application.applicationContext)

    val characterListLiveData: LiveData<List<Character>> = studyRepo.getCharacters()

    fun addSubject(subject: Character) = studyRepo.addCharacter(subject)

    fun deleteSubject(subject: Character) = studyRepo.deleteCharacter(subject)
}