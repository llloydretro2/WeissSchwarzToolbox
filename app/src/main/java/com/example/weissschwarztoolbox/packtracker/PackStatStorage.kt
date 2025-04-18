package com.example.weissschwarztoolbox.packtracker

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object PackStatStorage {
    private const val FILE_NAME = "pack_stat.json"

    fun save(context: Context, data: PackStat) {
        val json = Json.encodeToString(data)
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(json)
    }

    fun load(context: Context): PackStat {
        val file = File(context.filesDir, FILE_NAME)
        return if (file.exists()) {
            try {
                Json.decodeFromString(file.readText())
            } catch (e: Exception) {
                PackStat(
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
            }
        } else {
            PackStat(
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
        }
    }
}
