package org.dersbian.klexicon

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun testLex() {
        val lexer = Lexer("2 + 2 * (5 - 3)")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0,0, 1, 2.0),
            Token(TokType.PLUS, "+", 2, 2, 1),
            Token(TokType.NUMBER, "2", 4, 4, 1, 2.0),
            Token(TokType.MULTIPLY, "*", 6, 6, 1),
            Token(TokType.LPAREN, "(", 8, 8, 1),
            Token(TokType.NUMBER, "5", 9, 9, 1, 5.0),
            Token(TokType.MINUS, "-", 11, 11, 1),
            Token(TokType.NUMBER, "3", 13, 13, 1, 3.0),
            Token(TokType.RPAREN, ")", 14, 14, 1),
            Token(TokType.EOF, 15, 15, 1)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }
    @Test
    fun testLexNewLine() {
        val lexer = Lexer("2 + 2 * (5 - 3)\n2 + 3 + 4")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0,0, 1, 2.0),
            Token(TokType.PLUS, "+", 2,2, 1),
            Token(TokType.NUMBER, "2", 4,4, 1, 2.0),
            Token(TokType.MULTIPLY, "*", 6,6, 1),
            Token(TokType.LPAREN, "(", 8, 8, 1),
            Token(TokType.NUMBER, "5", 9, 9,1, 5.0),
            Token(TokType.MINUS, "-", 11, 11, 1),
            Token(TokType.NUMBER, "3", 13, 13, 1, 3.0),
            Token(TokType.RPAREN, ")", 14, 14, 1),
            Token(TokType.NUMBER, "2", 16, 1, 2, 2.0),
            Token(TokType.PLUS, "+", 18, 3, 2),
            Token(TokType.NUMBER, "3", 20, 5, 2, 3.0),
            Token(TokType.PLUS, "+", 22, 7, 2),
            Token(TokType.NUMBER, "4", 24, 9, 2, 4.0),
            Token(TokType.EOF, 25, 10, 2)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexInvalidCharacter() {
        val lexer = Lexer("2 + £")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0, 0, 1, 2.0),
            Token(TokType.PLUS, 2, 2, 1),
            Token(TokType.INVALID, "£", 4, 4, 1),
            Token(TokType.EOF, 5, 5, 1)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexComment() {
        val lexer = Lexer("2 # comment #")
        val expectedTokens = listOf(
            Token(TokType.NUMBER, "2", 0, 0, 1, 2.0),
            Token(TokType.EOF, 13, 13, 1)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexKeyWords() {
        val lexer = Lexer("if(2>9) return 2")
        val expectedTokens = listOf(
            Token(TokType.KEYWORD, "if", 0, 0, 1),
            Token(TokType.LPAREN, 2, 2, 1),
            Token(TokType.NUMBER, "2", 3, 3, 1, 2.0),
            Token(TokType.GREATER, 4, 4, 1),
            Token(TokType.NUMBER, "9", 5, 5, 1, 9.0),
            Token(TokType.RPAREN, 6, 6, 1),
            Token(TokType.KEYWORD, "return", 8, 8, 1),
            Token(TokType.NUMBER, "2", 15, 15, 1, 2.0),
            Token(TokType.EOF, 16, 16, 1)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }

    @Test
    fun testLexIdentifier() {
        val lexer = Lexer("foo bar_123")
        val expectedTokens = listOf(
            Token(TokType.IDENTIFIER, "foo", 0, 0, 1),
            Token(TokType.IDENTIFIER, "bar_123", 4, 4, 1),
            Token(TokType.EOF, 11, 11, 1)
        )
        val actualTokens = lexer.lexList()
        assertEquals(expectedTokens, actualTokens)
    }
}