package org.dersbian.klexicon

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

import kotlin.test.*

class ParserTest {
    @Test
    fun testParseNumber() {
        val parser = Parser("123")
        val expr = parser.parse()
        assertEquals(Num(123.0), expr)
    }

    @Test
    fun testParseAddition() {
        val parser = Parser("1 + 2")
        val expr = parser.parse()
        assertEquals(BinOp(Num(1.0), Token(TokType.PLUS, 2, 2, 1), Num(2.0)), expr)
    }

    @Test
    fun testParseMultiplication() {
        val parser = Parser("3 * 4")
        val expr = parser.parse()
        assertEquals(BinOp(Num(3.0), Token(TokType.MULTIPLY, 2, 2, 1), Num(4.0)), expr)
    }

    @Test
    fun testParseParentheses() {
        val parser = Parser("(1 + 2) * 3")
        val expr = parser.parse()
        val expected =
            BinOp(BinOp(Num(1.0), Token(TokType.PLUS, 3, 3, 1), Num(2.0)), Token(TokType.MULTIPLY, 8, 8, 1), Num(3.0))
        assertEquals(expected, expr)
    }

    @Test
    fun testParseInvalidExpression() {
        val parser = Parser("1 +")
        assertFailsWith<Parser.ParseException> {
            parser.parse()
        }
    }

}
