package qu.cmps312.lingosnacks.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import qu.cmps312.lingosnacks.model.Word

class WordListConverter {
    // Convert from the learningPackage's Words List to a json string to be stored in SQLite Database
    @TypeConverter
    fun fromWordsList(wordsList: MutableList<Word>): String = Json.encodeToString(wordsList)

    @TypeConverter
    fun toWordsList(wordsListString: String): MutableList<Word> = Json.decodeFromString(wordsListString)
}