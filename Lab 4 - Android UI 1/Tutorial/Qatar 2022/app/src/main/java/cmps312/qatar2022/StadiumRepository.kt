package cmps312.qatar2022

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object StadiumRepository {

    var stadiums = listOf<Stadium>()

    fun initStadiums(context: Context): List<Stadium> {
        if (stadiums.isEmpty()) {
            val stadiumsJson = File("").readText() // way 1
            val stadiumsJson1 = context.assets
                .open("")
                .bufferedReader()
                .use { it.readText() } // way 2

            //val json = Json { }
            stadiums = Json.decodeFromString(stadiumsJson) //Json.decodeFromString(stadiumsJson)
        }
        return stadiums
    }
}
