package org.dersbian.klexicon

class Lexer(inp: String) : LBase(inp) {

    fun lexToJson(): Pair<List<Token>, String> {
        val tokens = lex()
        val json = jackson.writeValueAsString(tokens)
        return tokens to json
    }

    fun lex(): List<Token> {
        while (p <= len) {
            when {
                cRTS.isWS() -> skipWS()
                cRT.isDigit() -> toks.add(parseNum())
                cRT.isLetter() -> toks.add(parseId())
                //EOF
                cRT == NC -> toks.add(Token(TokType.EOF, p++, rp++, l))
                cRT == '#' -> skipComment()
                // Operatori
                cRT == '+' -> toks.add(Token(TokType.PLUS, p++, rp++, l))
                cRT == '-' -> toks.add(Token(TokType.MINUS, p++, rp++, l))
                cRT == '*' -> toks.add(Token(TokType.MULTIPLY, p++, rp++, l))
                cRT == '/' -> toks.add(Token(TokType.DIVIDE, p++, rp++, l))
                cRT == '%' -> toks.add(Token(TokType.MODULO, p++, rp++, l))
                cRT == '&' -> toks.add(Token(TokType.AND, p++, rp++, l))
                cRT == '|' -> toks.add(Token(TokType.OR, p++, rp++, l))
                cRT == '(' -> toks.add(Token(TokType.LPAREN, p++, rp++, l))
                cRT == ')' -> toks.add(Token(TokType.RPAREN, p++, rp++, l))
                cRT == '=' -> toks.add(Token(TokType.EQUAL, p++, rp++, l))
                cRT == '!' -> toks.add(Token(TokType.NOT, p++, rp++, l))
                cRT == '>' -> toks.add(Token(TokType.GREATER, p++, rp++, l))
                cRT == '<' -> toks.add(Token(TokType.LESS, p++, rp++, l))
                // Caratteri non validi
                else -> toks.add(Token(TokType.INVALID, cRTS, p++, rp++, l))
            }
        }
        return toks
    }

    private fun skipComment() {
        val tLen = COMMENT.find(inp, p)!!.value.length
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
        return Token(TokType.IDENTIFIER, id, mRs.range.first, rSp, l)
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