package com.cmps312.lab2_kotlintutorial

fun main(args: Array<String>) {

    var numbers = listOf(1,2,3,4,5) //you can not add
    var numbersM = mutableListOf(1,2,3,4,5)  // you can add


    var names = listOf("abdulahi", "Mohamed", "Hassen")

    numbersM.add(20)

}

fun getLetterGrade(score : Int) : Char{

    return 'A'
}