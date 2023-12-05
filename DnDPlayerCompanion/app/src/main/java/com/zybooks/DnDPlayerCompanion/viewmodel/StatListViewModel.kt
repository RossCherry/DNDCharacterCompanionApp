package com.zybooks.DnDPlayerCompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.zybooks.DnDPlayerCompanion.model.Stats
import com.zybooks.DnDPlayerCompanion.repo.HelperRepository

class StatListViewModel(application: Application) : AndroidViewModel(application) {

    private val helperRepo = HelperRepository.getInstance(application)

    private val characterIdLiveData = MutableLiveData<Long>()

    val statsListLiveData: LiveData<Stats?> =
        characterIdLiveData.switchMap { characterId ->
            helperRepo.getStats(characterId)
        }

    fun loadStats(characterId: Long) {
        characterIdLiveData.value = characterId
    }

    fun addStat(stats: Stats) = helperRepo.addStat(stats)

    fun deleteStat(stats: Stats) = helperRepo.deleteStat(stats)
}