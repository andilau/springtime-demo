package de.herrlau.demo.kx

import java.util.*

fun main() {
    println("hello".uuid())

    applyArgument("hello", "bye") { s: String -> println(s.uuid()) }
}

fun applyArgument(vararg s: String, function: (String) -> Unit) {
    s.forEach(function)
}

fun String.uuid(): String =
    UUID.nameUUIDFromBytes(this.encodeToByteArray()).toString()