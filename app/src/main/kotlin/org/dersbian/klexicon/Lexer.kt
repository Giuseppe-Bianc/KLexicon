package org.dersbian.klexicon

class Lexer(inp: String) : LBase(inp) {
    private val KEYWORDS = setOf("if", "else", "while", "for", "return")

    fun lexToJson(): Pair<Sequence<Token>, String> {
        val tokens = lex()
        return tokens to jackson.writeValueAsString(tokens)
    }

    fun lex(): Sequence<Token> = sequence {
        while (p <= len) {
            when {
                cRTS.isWS() -> skipWS()
                cRT.isDigit() -> yield(parseNum())
                cRT.isLetter() -> yield(parseId())
                //EOF
                cRT == NC -> yield(Token(TokType.EOF, p++, rp++, l))
                cRT == '#' -> skipComment()
                // Operatori
                cRT == '+' -> yield(Token(TokType.PLUS, p++, rp++, l))
                cRT == '-' -> yield(Token(TokType.MINUS, p++, rp++, l))
                cRT == '*' -> yield(Token(TokType.MULTIPLY, p++, rp++, l))
                cRT == '/' -> yield(Token(TokType.DIVIDE, p++, rp++, l))
                cRT == '%' -> yield(Token(TokType.MODULO, p++, rp++, l))
                cRT == '&' -> yield(Token(TokType.AND, p++, rp++, l))
                cRT == '|' -> yield(Token(TokType.OR, p++, rp++, l))
                cRT == '(' -> yield(Token(TokType.LPAREN, p++, rp++, l))
                cRT == ')' -> yield(Token(TokType.RPAREN, p++, rp++, l))
                cRT == '=' -> yield(Token(TokType.EQUAL, p++, rp++, l))
                cRT == '!' -> yield(Token(TokType.NOT, p++, rp++, l))
                cRT == '>' -> yield(Token(TokType.GREATER, p++, rp++, l))
                cRT == '<' -> yield(Token(TokType.LESS, p++, rp++, l))
                // Caratteri non validi
                else -> yield(Token(TokType.INVALID, cRTS, p++, rp++, l))
            }
        }
    }

    private fun skipComment() {
        val mtc = COMMENT.find(inp, p) ?: return
        val tLen = mtc.value.length
        p += tLen
        rp += tLen
    }

    private fun parseNum(): Token {
        var nt = ' '
        val sInd = p
        val rSInd = rp
        val numberType: NumType = when (cRT) {
            ZR -> when {
                p in ind && (nXTL == X) -> {
                    p += 2
                    nt = pRT
                    NumType.EXADECIMAL
                }

                else -> NumType.OCTAL
            }

            else -> NumType.NUMBER
        }
        return Token(TokType.NUMBER, sInd, rSInd, l, matchToBInt(nt, numberType))
    }

    private fun parseId(): Token {
        val rSp = rp
        val mRs: MatchResult = IDENTIFIER.find(inp, p) ?: return Token(TokType.EOF, p, rp, l)
        val id = mRs.value
        val idL = id.length
        p += idL
        rp += idL
        val tokType = if (id in KEYWORDS) TokType.KEYWORD else TokType.IDENTIFIER
        return Token(tokType, id, mRs.range.first, rSp, l)
    }

    private fun skipWS() {
        if (cRTS.isNotWS()) return
        while (cRTS.isWS()) {
            if (cRT == '\n') {
                l++
                rp = 0
            }
            p++
            rp++
        }
    }
}