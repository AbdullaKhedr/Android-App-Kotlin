package com.cmps312.lab2_kotlintutorial

class Friend(var firsname: String, var lastname: String, var gender : Char){
    override fun toString(): String {
    var title = if (gender == 'M') "Mr." else  "Ms."
    return "$title $firsname $lastname"
}

}

fun main(args: Array<String>) {
    var friend1 = Friend("Abdulahi", "Hassen", 'M')
    var friend2 = Friend("Alanood", "Al-..", 'F')

    var listOfFnds = listOf(friend1, friend2)

    listOfFnds.forEach {println(it)}
    listOfFnds.forEach(::println)

}