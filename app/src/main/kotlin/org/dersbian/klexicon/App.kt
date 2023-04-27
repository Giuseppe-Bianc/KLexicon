package org.dersbian.klexicon

fun main() {
    val lexer = Lexer(INPUTSSSS)
    val (tokens, json) = lexer.lexToJson()
    val sb = StringBuilder()
    tokens.forEach { sb.appendLine(it) }
    sb.appendLine(json)

    println(sb.toString())
}
