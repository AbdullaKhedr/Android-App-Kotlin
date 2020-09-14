package com.cmps312.books.model

fun main() {
    val audioBook = AudioBook("Abdulla", "Book", 2025, 5, 1, "Ali")
    println(audioBook)
}

open class Book(val name: String, val author: String, val yearOfPublication: Int) {
    override fun toString() = """
            Name: $name
            Author: $author
            Year : $yearOfPublication
        """.trimIndent()
}

class AudioBook(
    name: String,
    author: String,
    yearOfPublication: Int,
    val size: Int,
    val length: Int,
    val artistName: String
) : Book(name, author, yearOfPublication) {
    override fun toString(): String {
        return """
            ${super.toString()}
            Size: $size
            Length: $length
            Artist: $artistName
        """.trimIndent()
    }
}

