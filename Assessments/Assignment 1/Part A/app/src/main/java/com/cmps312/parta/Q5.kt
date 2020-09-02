package com.cmps312.parta

import kotlin.math.pow

fun main() {
    val nums = 5..50
    nums.forEach { println(it) }
    println()

    println("Minimum: ")
    println(min(nums))

    println("Maximum: ")
    println(max(nums))

    println("Sum: ")
    println(sum(nums))

    println("Average: ")
    println(average(nums))
    println()

    val cubicNums = nums.map { it * it * it }
    // other way to do using pow() fun
    //val cubicNums = nums.map { it.toDouble().pow(3).toInt() }
    cubicNums.forEach { println(it) }
}

fun min(range: IntRange) = range.min()

fun max(range: IntRange) = range.max()

fun sum(range: IntRange) = range.reduce { sum, e -> sum + e }

fun average(range: IntRange) = sum(range).div(range.count())
