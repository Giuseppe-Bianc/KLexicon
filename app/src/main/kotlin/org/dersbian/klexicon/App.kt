package org.dersbian.klexicon

fun main() {
    val lexer = Lexer(INPUTSSSS)
    val (tokens, json) = lexer.lexToJson()
    val sb = StringBuilder()
    //tokens.forEach { sb.appendLine(it) }
    //sb.appendLine(json)
    val parser = Parser("-1 + 2 + 3 + 4 + ((4 / 2) + 3)")
    val expression = parser.parse()
    val evaluator = Evaluator("-1 + 2 + 3 + 4 + ((4 / 2) + 3)")
    val res = evaluator.evaluate()

    prettyPrintEvaluated("-1 + 2 + 3 + 4 + ((4 / 2) + 3)", expression)
    //println(sb.toString())

}

fun prettyPrintEvaluated(input: String, node: Expr, indent: String = "", isLast: Boolean = true) {
    val evaluator = Evaluator(input)
    val res = evaluator.evaluate()
    println("$input = $res")
    prettyPrint(node, indent, isLast)
}

fun prettyPrint(node: Expr, indent: String = "", isLast: Boolean = true) {
    print("$indent${if (isLast) "└──" else "├──"}")
    when (node) {
        is Num -> println(" ${node}")
        is BinOp -> {
            println(" BinOp(${node.op})")
            prettyPrint(node.left, "${indent}${if (isLast) "    " else "│   "}", false)
            prettyPrint(node.right, "${indent}${if (isLast) "    " else "│   "}", true)
        }

        is UnaryOp -> {
            println(" UnaryOp(${node.operator})")
            prettyPrint(node.operand, "${indent}${if (isLast) "    " else "│   "}", true)
        }
    }
}
