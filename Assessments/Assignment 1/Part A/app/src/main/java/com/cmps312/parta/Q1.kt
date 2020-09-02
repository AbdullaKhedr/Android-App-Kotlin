package com.cmps312.parta

fun main() {
    for (num in 2..100 step 2) {
        print(" $num")

        if (num % 5 == 0) {
            println()
        }
    }
}