package com.cmps312.javatokotlin

// Extension method extending Int class

fun main(){
    val num = 10
    println ("Is $num even: ${num.isEven()}")
}
fun Int.isEven () = this % 2 == 0
