package org.dersbian.klexicon

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun testLex() {
        val lexer = Lexer("2 + 2 * (5 - 3)")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0,0, 1, 2.0),
            Token(TokType.PLUS, "+", 2,2, 1),
            Token(TokType.NUMBER, "2", 4,4, 1, 2.0),
            Token(TokType.MULTIPLY, "*", 6,6, 1),
            Token(TokType.LPAREN, "(", 8, 8, 1),
            Token(TokType.NUMBER, "5", 9, 9,1, 5.0),
            Token(TokType.MINUS, "-", 11, 11, 1),
            Token(TokType.NUMBER, "3", 13, 13,1, 3.0),
            Token(TokType.RPAREN, ")", 14,14, 1),
            Token(TokType.EOF, 15,15, 1)
        )
        val actualTokens = lexer.lex()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexInvalidCharacter() {
        val lexer = Lexer("2 + £")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0,0, 1, 2.0),
            Token(TokType.PLUS, 2,2, 1),
            Token(TokType.INVALID, "£", 4,4, 1),
            Token(TokType.EOF, 5,5, 1)
        )
        val actualTokens = lexer.lex()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexIdentifier() {
        val lexer = Lexer("foo bar_123")
        val expectedTokens = listOf(
            Token(TokType.IDENTIFIER, "foo", 0,0, 1),
            Token(TokType.IDENTIFIER, "bar_123", 4,4, 1),
            Token(TokType.EOF, 11,11, 1)
        )
        val actualTokens = lexer.lex()
        assertEquals(expectedTokens, actualTokens)
    }
}