package com.cmps312.parta

fun main() {
    val friend1 = Friend("Abdulahi", "Hassen", 'M')
    val friend2 = Friend("Fatima", "Hamza", 'F')
    val friend3 = Friend("Fiona", "Shrek", 'F')
    val friend4 = Friend("Abbas", "Ibn Fernas")

    val friends = listOf(friend1, friend2, friend3, friend4)

    friends.forEach { println(it.toString()) }
}

class Friend(val firstName: String, val lastName: String, val gender: Char = 'M') {

    override fun toString() =
        when (gender) {
            'F' -> "Ms.$firstName $lastName"
            else -> "Mr.$firstName $lastName"
        }

}