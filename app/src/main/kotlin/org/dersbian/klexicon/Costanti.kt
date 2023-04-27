package org.dersbian.klexicon

const val ZR = '0'
const val X = 'x'
const val NC = '\u0000'
const val IDENTS = "\\w+"
const val NUMS = "\\d+(\\.\\d+)?([eE][+-]?\\d+)?"
const val COMMENTS = "#(.*?)#"
const val OCTALS = "[0-7]"
const val EXADECIMALS = "[0-9a-zA-Z]"
const val INPUT =
    "(()( % 3 + 4 * 2 - ( 1 - 5 ) / 2 == != >= % <= < > = & && || % -22 | \r\n 2 + 0x33 \n inter = 077\n 23.2e-3 + 2e3\n # commento prova #\n 2+2  3 +3"
const val INPUTS = INPUT + INPUT + INPUT + INPUT
const val INPUTSS = INPUTS + INPUTS
const val INPUTSSS = INPUTSS + INPUT
const val INPUTSSSS = INPUTSS + INPUT + INPUT