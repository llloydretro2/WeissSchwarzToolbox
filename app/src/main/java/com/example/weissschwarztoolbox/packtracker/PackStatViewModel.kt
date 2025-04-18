package com.example.weissschwarztoolbox.packtracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PackStatViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val _packStat = MutableStateFlow(PackStatStorage.load(context))
    val packStat: StateFlow<PackStat> = _packStat


    private fun save(stat: PackStat) {
        Log.d("PackStatViewModel", "Save: $stat")
        PackStatStorage.save(context, stat)
        Log.d("PackStatViewModel", "Saved: $stat")
    }

    internal fun reset() {
        _packStat.value = PackStat(
            packs = 0,
            cardsPerPack = 0,
            sp = 0,
            ssp = 0,
            agr = 0,
            sir = 0,
            rr = 0,
            r = 0,
            rrr = 0,
            sec = 0,
            secPlus = 0,
            setRarity = 0,
            rrrPlus = 0,
            sr = 0
        )
        save(_packStat.value)
    }


    fun updatePacks(value: Int) {
        Log.d("PackStatViewModel", "updatePacks: $value")
        _packStat.update { it.copy(packs = value) }
        Log.d("PackStatViewModel", "updatePacks: ${_packStat.value}")
        save(_packStat.value)
    }

    fun updateCardsPerPack(value: Int) {
        _packStat.update { it.copy(cardsPerPack = value) }
        save(_packStat.value)
    }

    fun updateSp(value: Int) {
        _packStat.update { it.copy(sp = value) }
        save(_packStat.value)
    }

    fun updateSsp(value: Int) {
        _packStat.update { it.copy(ssp = value) }
        save(_packStat.value)
    }

    fun updateAgr(value: Int) {
        _packStat.update { it.copy(agr = value) }
        save(_packStat.value)
    }

    fun updateSir(value: Int) {
        _packStat.update { it.copy(sir = value) }
        save(_packStat.value)
    }

    fun updateRr(value: Int) {
        _packStat.update { it.copy(rr = value) }
        save(_packStat.value)
    }

    fun updateR(value: Int) {
        _packStat.update { it.copy(r = value) }
        save(_packStat.value)
    }

    fun updateRrr(value: Int) {
        _packStat.update { it.copy(rrr = value) }
        save(_packStat.value)
    }

    fun updateSec(value: Int) {
        _packStat.update { it.copy(sec = value) }
        save(_packStat.value)
    }

    fun updateSecPlus(value: Int) {
        _packStat.update { it.copy(secPlus = value) }
        save(_packStat.value)
    }

    fun updateSetRarity(value: Int) {
        _packStat.update { it.copy(setRarity = value) }
        save(_packStat.value)
    }

    fun updateRrrPlus(value: Int) {
        _packStat.update { it.copy(rrrPlus = value) }
        save(_packStat.value)
    }

    fun updateSr(value: Int) {
        _packStat.update { it.copy(sr = value) }
        save(_packStat.value)
    }


}
