package com.zybooks.DnDPlayerCompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.zybooks.DnDPlayerCompanion.model.Stats
import com.zybooks.DnDPlayerCompanion.repo.HelperRepository

class StatDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val helperRepo = HelperRepository.getInstance(application)

    private val statIdLiveData = MutableLiveData<Long>()

    val statsLiveData: LiveData<Stats?> =
        statIdLiveData.switchMap { statId ->
            helperRepo.getStat(statId)
        }

    fun loadStat(statId: Long) {
        statIdLiveData.value = statId
    }

    fun addStat(stats: Stats) = helperRepo.addStat(stats)

    fun updateStat(stats: Stats) = helperRepo.updateStat(stats)
}