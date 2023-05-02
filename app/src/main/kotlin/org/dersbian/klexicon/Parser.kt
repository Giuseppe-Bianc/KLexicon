package org.dersbian.klexicon
sealed class Expr
data class Num(val value: Double) : Expr()
data class BinOp(val left: Expr, val op: Token, val right: Expr) : Expr()
data class UnaryOp(val operator: Token, val operand: Expr) : Expr()

class Parser(input: String) {
    private val tokens: List<Token> = Lexer(input).lex().toList()
    private var current = 0
    fun parse(): Expr = expression()
    private fun expression(): Expr {
        var expr = term()

        while (match(TokType.PLUS, TokType.MINUS)) {
            val operator = previous()
            val right = term()
            expr = BinOp(expr, operator, right)
        }

        return expr
    }

    private fun term(): Expr {
        var expr = factor()

        while (match(TokType.MULTIPLY, TokType.DIVIDE)) {
            val operator = previous()
            val right = factor()
            expr = BinOp(expr, operator, right)
        }

        return expr
    }

    private fun factor(): Expr {
        return when {
            match(TokType.NUMBER) -> Num(previous().value as Double)
            match(TokType.LPAREN) -> {
                val expr = expression()
                consume(TokType.RPAREN, "Expected ')' after expression.")
                expr
            }

            match(TokType.PLUS, TokType.MINUS) -> {
                val operator = previous()
                val operand = factor()
                UnaryOp(operator, operand)
            }

            else -> error("Unexpected token ${peek().type}")
        }
    }

    private fun match(vararg types: TokType): Boolean {
        return when (peek().type) {
            in types -> {
                advance()
                true
            }
            else -> false
        }
    }

    private fun consume(type: TokType, message: String): Token {
        if (check(type)) return advance()

        error(message)
    }

    private fun check(type: TokType): Boolean {
        if (isAtEnd()) return false
        return peek().type == type
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean = peek().type == TokType.EOF

    private fun peek(): Token = tokens[current]

    private fun previous(): Token = tokens[current - 1]

    private fun error(message: String): Nothing {
        throw ParseException(message)
    }

    class ParseException(message: String) : RuntimeException(message)
}