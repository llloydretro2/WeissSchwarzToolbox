package com.example.weissschwarztoolbox.packtracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PackStatViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _packStat = MutableStateFlow(PackStatStorage.load(context))
    val packStat: StateFlow<PackStat> = _packStat

    private fun save(stat: PackStat) {
        PackStatStorage.save(context, stat)
    }

    fun updateField(field: String, value: Int) {
        val updated = _packStat.value.copyWithField(field, value)
        _packStat.value = updated
        save(updated)
    }

}
