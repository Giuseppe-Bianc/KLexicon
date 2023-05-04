package org.dersbian.klexicon

class Evaluator(input: String) {
    val expr = Parser(input).parse()

    fun evaluate(): Double = evaluate(expr)

    private inline fun evaluate(expr: Expr): Double {
        return when (expr) {
            is Num -> expr.value
            is BinOp -> evaluateBinaryOp(expr)
            is UnaryOp -> evaluateUnaryOp(expr)
        }
    }

    private fun evaluateBinaryOp(expr: BinOp): Double {
        val left = evaluate(expr.left)
        val right = evaluate(expr.right)
        return when (expr.op.type) {
            TokType.PLUS -> left + right
            TokType.MINUS -> left - right
            TokType.MULTIPLY -> left * right
            TokType.DIVIDE -> left / right
            else -> throw UnsupportedOperationException("Unsupported operator ${expr.op}")
        }
    }

    private fun evaluateUnaryOp(expr: UnaryOp): Double {
        val operand = evaluate(expr.operand)
        return when (expr.operator.type) {
            TokType.MINUS -> -operand
            else -> throw UnsupportedOperationException("Unsupported operator ${expr.operator}")
        }
    }
}

