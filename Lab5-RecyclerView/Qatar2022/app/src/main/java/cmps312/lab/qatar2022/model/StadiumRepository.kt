package cmps312.lab.qatar2022.model

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object StadiumRepository {

    var stadiums = listOf<Stadium>()

    fun initStadiums(context: Context) {
        //val stadiumsJson = File("").readText() // if it was a normal file
        val stadiumsJson = context.assets.open("stadiums.json")
            .bufferedReader().use { it.readText() } // the file is in assets
        stadiums = Json.decodeFromString(stadiumsJson) //Json.decodeFromString(stadiumsJson)
    }
}
