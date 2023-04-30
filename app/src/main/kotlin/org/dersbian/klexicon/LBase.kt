package org.dersbian.klexicon

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.commons.lang3.StringUtils

abstract class LBase(@JvmField inline val inp: String) {

    init {
        require(StringUtils.isNotBlank(inp)) { "impossibile proseguire stringa vuota" }
    }

    var p: Int = 0 // position
    var rp: Int = 0 // position relative to the line
    var l: Int = 1 // line
    val toks: MutableList<Token> = mutableListOf()
    val len: Int = inp.length
    val inA = inp.toCharArray()
    val ind: IntRange
        inline get() = inp.indices
    val pRT: Char
        inline get() = inA.getOrNull(p - 1) ?: NC
    val cRT: Char
        inline get() = inA.getOrNull(p) ?: NC
    val cRTS: String
        inline get() = cRT.toString()
    val nXT: Char
        inline get() = inA.getOrNull(p + 1) ?: NC
    val nXTS: String
        inline get() = nXT.toString()
    val nXTL: Char
        inline get() = nXT.lowercaseChar()
    val nXTLS: String
        inline get() = nXTL.toString()

    fun matchToBInt(c: Char, type: NumType): Pair<String, Double?> {
        val mRs: MatchResult = when (type) {
            NumType.EXADECIMAL -> EXADECIMAL // provenienti da una cache
            NumType.OCTAL -> OCTAL
            NumType.NUMBER -> NUMBERR
        }.find(inp, p) ?: return "" to null
        val text = mRs.value
        return when (type) {
            NumType.EXADECIMAL -> "0$c$text" to text.toLongOrNull(type.base)?.toDouble()
            NumType.OCTAL -> "0$text" to text.toLongOrNull(type.base)?.toDouble()
            NumType.NUMBER -> text to text.toDoubleOrNull()
        }.also {
            val tL = text.length
            p += tL
            rp += tL
        }
    }

    companion object {
        val jackson: ObjectWriter by lazy { ObjectMapper().registerKotlinModule().writerWithDefaultPrettyPrinter() }
        val IDENTIFIER = RxCach.getOrPut(IDENTS)
        val EXADECIMAL = RxCach.getOrPut(EXADECIMALS)
        val OCTAL = RxCach.getOrPut(OCTALS)
        val NUMBERR = RxCach.getOrPut(NUMS)
        val COMMENT = RxCach.getOrPut(COMMENTS)

        @JvmStatic
        fun String.isWS(): Boolean = StringUtils.isWhitespace(this)

        @JvmStatic
        fun String.isNotWS(): Boolean = !StringUtils.isWhitespace(this)
    }
}