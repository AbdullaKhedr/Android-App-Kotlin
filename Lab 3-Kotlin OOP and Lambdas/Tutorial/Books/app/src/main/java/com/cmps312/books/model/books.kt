package com.cmps312.books.model

open class Book(val name : String, val author : String, val yearOfPublication : Int ){
    override fun toString(): String {
        return """

            Name : $name
            Author : $author
            Year : $yearOfPublication
        """.trimIndent()
    }
}

class AudioBook( name : String,
                 author : String,
                 yearOfPublication : Int,
                 var size : Int,
                 var length : Int,
                 var artistName : String) : Book(name , author , yearOfPublication){
    override fun toString(): String {
        return """
        ${super.toString()}
        Size : $size
        Length : $size
        Artist : $size
        """.trimIndent()
    }
}
class PaperBook( name : String,
                 author : String,
                 yearOfPublication : Int,
                 var publisher : String,
                 var isbn : String) : Book(name , author , yearOfPublication){
    override fun toString(): String {
        return """
        ${super.toString()}
        Publisher : $publisher
        ISBN : $isbn
        """.trimIndent()
    }
}

fun main(args: Array<String>) {
    var audioBook1 = AudioBook(
        "How to be successful" ,
        "Hassan Alsamra" ,
        222,
        121,
        12,
        "Ahmed Ayman")
    var audioBook2 = AudioBook(
        "How to be successful" ,
        "Hassan Alsamra" ,
        2225,
        122,
        133,
        "Ahmed Omar")

    var paperBook1 = PaperBook(
        "How to be successful" ,
        "Hassan Abdi" ,
        2025,
        "Amazon",
        "10004")

    var paperBook2 = PaperBook(
        "How to be successful" ,
        "Hassan Alsamra" ,
        2025,
        "Oriely",
        "1001")

    var books = mutableListOf(audioBook1 , paperBook1 , paperBook2)
    books.add(audioBook2)

    books.forEach{ println(it)}



}