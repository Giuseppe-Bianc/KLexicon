package org.dersbian.klexicon

import com.fasterxml.jackson.annotation.JsonInclude

data class Token(
    @JvmField val type: TokType,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JvmField val text: String,
    @JvmField val pos: Int,
    @JvmField val relPos: Int,
    @JvmField val line: Int,
    @JvmField val value: Any? = null
) {
    constructor(type: TokType, pos: Int, relPos: Int, line: Int, textValue: Pair<String, Any?>) : this(
        type,
        textValue.first,
        pos,
        relPos,
        line,
        textValue.second
    )

    constructor(type: TokType, pos: Int, relPos: Int, line: Int) : this(type, type.text, pos, relPos, line)

    override fun toString() = buildString {
        append("$type")
        if (text.isNotBlank()) append(", '$text'")
        append(", pos=$pos, relPos=$relPos line=$line")
        if (value != null) append(", value=$value")
    }
}