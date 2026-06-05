package com.example.kotlinbasics.ui.compose

import kotlin.math.sqrt

fun sumFirstAndLastDigit(input: String): Int? {
    val text = input.trim()
    if (text.isEmpty() || text.any { !it.isDigit() }) return null
    return text.first().digitToInt() + text.last().digitToInt()
}

fun firstPrimes(count: Int): List<Int> {
    val primes = mutableListOf<Int>()
    var number = 2
    while (primes.size < count) {
        if (isPrime(number)) primes.add(number)
        number++
    }
    return primes
}

private fun isPrime(number: Int): Boolean {
    if (number < 2) return false
    for (divider in 2 until number) {
        if (number % divider == 0) return false
    }
    return true
}

fun parseIntArray(input: String): IntArray = input
    .split(" ", ",", ";")
    .mapNotNull { it.trim().toIntOrNull() }
    .toIntArray()

fun biggerThanNeighboursFor(array: IntArray): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 1 until array.lastIndex) {
        if (array[i] > array[i - 1] && array[i] > array[i + 1]) result.add(array[i])
    }
    return result
}

fun biggerThanNeighboursWhile(array: IntArray): List<Int> {
    val result = mutableListOf<Int>()
    var i = 1
    while (i < array.lastIndex) {
        if (array[i] > array[i - 1] && array[i] > array[i + 1]) result.add(array[i])
        i++
    }
    return result
}

fun biggerThanNeighboursForEach(array: IntArray): List<Int> {
    val result = mutableListOf<Int>()
    array.forEachIndexed { index, value ->
        if (index != 0 && index != array.lastIndex && value > array[index - 1] && value > array[index + 1]) {
            result.add(value)
        }
    }
    return result
}

fun productFor(array: IntArray): Int {
    var product = 1
    for (item in array) product *= item
    return product
}

fun productWhile(array: IntArray): Int {
    var product = 1
    var i = 0
    while (i < array.size) {
        product *= array[i]
        i++
    }
    return product
}

fun productForEach(array: IntArray): Int {
    var product = 1
    array.forEach { product *= it }
    return product
}

fun productReduce(array: IntArray): Int = if (array.isEmpty()) 0 else array.reduce { acc, item -> acc * item }

fun sqr(n: Double): Double = n * n

fun discriminant(a: Double, b: Double, c: Double): Double = sqr(b) - 4 * a * c

fun rootsNumber(a: Double, b: Double, c: Double): Int {
    val d = discriminant(a, b, c)
    return when {
        d > 0 -> 2
        d == 0.0 -> 1
        else -> 0
    }
}

fun quadraticRoot(a: Double, b: Double, c: Double): String {
    if (a == 0.0) return "Коэффициент a не должен быть равен 0"
    val d = discriminant(a, b, c)
    return when (rootsNumber(a, b, c)) {
        2 -> {
            val x1 = (-b + sqrt(d)) / (2 * a)
            val x2 = (-b - sqrt(d)) / (2 * a)
            "Два корня: x1 = $x1, x2 = $x2"
        }
        1 -> {
            val x = -b / (2 * a)
            "Один корень: x = $x"
        }
        else -> "Корней нет"
    }
}

class NumberArray(private val numbers: IntArray) {
    fun positiveSum(): Int = numbers.filter { it > 0 }.sum()
    fun product(): Int = if (numbers.isEmpty()) 0 else numbers.reduce { acc, item -> acc * item }
    fun average(): Double = if (numbers.isEmpty()) 0.0 else numbers.average()
}

class Vector(val x: Double, val y: Double, val z: Double) {
    fun length(): Double = sqrt(x * x + y * y + z * z)
    fun dot(other: Vector): Double = x * other.x + y * other.y + z * other.z
    infix fun dotInfix(other: Vector): Double = dot(other)
    operator fun times(other: Vector): Double = dot(other)
}

fun dotProduct(first: Vector, second: Vector): Double = first.dot(second)

open class Vehicle(open val speed: Int = 0, open val title: String = "Транспорт") {
    open fun start(): String = "$title начал движение со скоростью $speed км/ч"
    open fun stop(): String = "$title остановился"
}

class Boat : Vehicle(35, "Лодка")
class Plane : Vehicle(850, "Самолет")
class Tank : Vehicle(60, "Танк")
