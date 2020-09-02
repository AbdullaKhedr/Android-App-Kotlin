package com.cmps312.parta

fun main() {
    println(getLetterGrade(80))
}

fun getLetterGrade(score: Int): String = when (score) {
    in 90..100 -> "A"
    in 85..89 -> "B+"
    in 80..84 -> "B"
    in 75..79 -> "C+"
    in 70..74 -> "C"
    in 65..69 -> "D+"
    in 60..64 -> "D"
    else -> "F"
}