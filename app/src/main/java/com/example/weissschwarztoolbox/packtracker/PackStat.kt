package com.example.weissschwarztoolbox.packtracker

import kotlinx.serialization.Serializable

@Serializable
data class PackStat(
    val packs: Int,
    val cardsPerPack: Int,
    val sp: Int,
    val ssp: Int,
    val agr: Int,
    val sir: Int,
    val rr: Int,
    val r: Int,
    val rrr: Int,
    val sec: Int,
    val secPlus: Int,
    val setRarity: Int,
    val rrrPlus: Int,
    val sr: Int
)
