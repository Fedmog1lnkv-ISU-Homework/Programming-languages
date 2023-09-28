import java.util.Stack

fun isOperator(token: String): Boolean {
    return token in setOf("+", "-", "*", "/")
}

fun isOperand(token: String): Boolean {
    return token.matches(Regex("\\d+"))
}

fun buildExpressionTree(tokens: List<String>): String {
    val stack = Stack<String>()
    for (token in tokens.reversed()) {
        if (isOperand(token)) {
            stack.push(token)
        } else if (isOperator(token)) {
            if (stack.size < 2) {
                throw IllegalArgumentException("Invalid expression")
            }
            val operand1 = stack.pop()
            val operand2 = stack.pop()
            val expression = "($operand1 $token $operand2)"
            stack.push(expression)
        } else {
            throw IllegalArgumentException("Invalid token")
        }
    }
    if (stack.size != 1) {
        throw IllegalArgumentException("Invalid expression")
    }
    return stack.pop()
}
fun prefixToInfix(prefixExpression: String): String {
    val tokens = prefixExpression.split(" ")
    return buildExpressionTree(tokens)
}

fun main() {
    print("Введите выражение в префиксной нотации: ")
    val prefixExpression = readLine()
    try {
        val infixExpression = prefixToInfix(prefixExpression ?: "")
        println("Инфиксная запись: $infixExpression")
    } catch (e: IllegalArgumentException) {
        println("Ошибка: ${e.message}")
    }
}