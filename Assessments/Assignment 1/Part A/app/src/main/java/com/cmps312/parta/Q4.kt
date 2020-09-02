package com.cmps312.parta

fun main() {
    val cities = mutableListOf("Doha", "Tokyo", "Delhi")

    println("---------------Cities---------------")
    display(cities)
    println("---------After adding Dhaka---------")
    cities.add("Dhaka")
    display(cities)
    println("--------After adding Beijing--------")
    cities.add("Beijing")
    display(cities)
    println("--------Sorted Cities by alphabetic--------")
    cities.sort()
    display(cities)
    println("--------Sorted Cities by alphabetic in reverse--------")
    cities.sortDescending()
    display(cities)
    println("--------Cities after removing Beijing--------")
    cities.remove("Beijing")
    display(cities)
}

fun display(list: List<String>) {
    list.forEach { println(it) }
}