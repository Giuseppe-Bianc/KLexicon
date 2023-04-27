package org.dersbian.klexicon

enum class TokType(inline val text: String = "") {
    EOF,
    INVALID,
    NUMBER,
    IDENTIFIER,
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    AND("&"),
    OR("|"),
    EQUAL("="),
    LPAREN("("),
    RPAREN(")"),
    GREATER(">"),
    LESS("<"),
    NOT("!")
}