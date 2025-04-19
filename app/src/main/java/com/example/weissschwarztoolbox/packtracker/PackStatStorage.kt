package com.example.weissschwarztoolbox.packtracker

import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json
import java.io.File

object PackStatStorage {
	private const val FILE_NAME = "pack_stat.json"

	fun save(
		context: Context,
		data: PackStat,
	) {
		try {
			Log.d("PackStatStorage", "Entered save $data")
			val json = Json.encodeToString(data)
			Log.d("PackStatStorage", "Encoded JSON: $json")
			val file = File(context.filesDir, FILE_NAME)
			file.writeText(json)
			Log.d("PackStatStorage", "file saved successfully")
		} catch (e: Exception) {
			Log.e("PackStatStorage", "Error saving: ${e.message}", e)
		}
	}

	fun load(context: Context): PackStat {
		val file = File(context.filesDir, FILE_NAME)
		return if (file.exists()) {
			try {
				Json.decodeFromString(file.readText())
			} catch (_: Exception) {
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
					sr = 0,
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
				sr = 0,
			)
		}
	}
}
