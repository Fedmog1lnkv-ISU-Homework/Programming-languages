import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse

class MainKtTest {

    @Test
    fun testIsOperator() {
        assertTrue(isOperator("+"))
        assertTrue(isOperator("-"))
        assertTrue(isOperator("*"))
        assertTrue(isOperator("/"))
        assertFalse(isOperator("5"))
        assertFalse(isOperator("invalid"))
    }

    @Test
    fun testIsOperand() {
        assertTrue(isOperand("5"))
        assertTrue(isOperand("12345"))
        assertFalse(isOperand("+"))
        assertFalse(isOperand("invalid"))
    }

    @Test
    fun testBuildExpressionTree() {
        assertEquals("(13 - 4)", buildExpressionTree(listOf("-", "13", "4")))
        assertEquals("((2 * 2) - 1)", buildExpressionTree(listOf("-", "*", "2", "2", "1")))
        assertEquals("((5 + 3) * 2)", buildExpressionTree(listOf("*", "+", "5", "3", "2")))
        assertEquals("((8 / 4) + (6 - 2))", buildExpressionTree(listOf("+", "/", "8", "4", "-", "6", "2")))
        assertEquals("((7 - (5 * 2)) / 3)", buildExpressionTree(listOf("/", "-", "7", "*", "5", "2", "3")))
        assertEquals(
            "(((8 + 4) * 2) - (3 / 1))",
            buildExpressionTree(listOf("-", "*", "+", "8", "4", "2", "/", "3", "1"))
        )
        assertEquals(
            "((1 + (2 * (3 - 4))) / 5)",
            buildExpressionTree(listOf("/", "+", "1", "*", "2", "-", "3", "4", "5"))
        )
    }

    @Test
    fun testInvalidBuildExpressionTree() {
        assertThrows<IllegalArgumentException> {
            // Неверное выражение без достаточного количества операндов
            buildExpressionTree(listOf("+", "2", "*"))
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение с недопустимым токеном
            buildExpressionTree(listOf("+", "2", "abc"))
        }

        assertThrows<IllegalArgumentException> {
            // Пустое выражение
            buildExpressionTree(emptyList())
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение с оператором без операндов
            buildExpressionTree(listOf("+"))
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение с недопустимым оператором
            buildExpressionTree(listOf("?", "2", "3"))
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение с числом и недопустимым оператором
            buildExpressionTree(listOf("5", "*", "2", "3"))
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение с недопустимыми числами
            buildExpressionTree(listOf("5", "abc", "3"))
        }
    }

    @Test
    fun testPrefixToInfix() {
        assertEquals("(17 + 5)", prefixToInfix("+ 17 5"))
        assertEquals("(3 + 4)", prefixToInfix("+ 3 4"))
        assertEquals("((3 + 4) * (5 - 2))", prefixToInfix("* + 3 4 - 5 2"))
        assertEquals("(5 * (2 + (8 / 4)))", prefixToInfix("* 5 + 2 / 8 4"))
        assertEquals("((9 - 7) / (6 * 3))", prefixToInfix("/ - 9 7 * 6 3"))
        assertEquals(
            "(5 * ((6 / 2) - 2))",
            prefixToInfix("* 5 - / 6 2 2")
        )
        assertEquals("(5 - (3 - (2 - (1 - 4))))", prefixToInfix("- 5 - 3 - 2 - 1 4"))
    }

    @Test
    fun testInvalidPrefixToInfix() {
        assertThrows<IllegalArgumentException> {
            // Неверное выражение с недопустимым токеном
            prefixToInfix("+ 2 3 abc")
        }

        assertThrows<IllegalArgumentException> {
            // Неверное выражение без достаточного количества операндов
            prefixToInfix("+ 2")
        }

        assertThrows<IllegalArgumentException> {
            // Пустое выражение
            prefixToInfix("")
        }

        assertThrows<IllegalArgumentException> {
            // Выражение с оператором без операндов
            prefixToInfix("+")
        }

        assertThrows<IllegalArgumentException> {
            // Выражение с недопустимым оператором
            prefixToInfix("? 2 3")
        }

        assertThrows<IllegalArgumentException> {
            // Выражение с числом и недопустимым оператором
            prefixToInfix("5 * 2 3")
        }

        assertThrows<IllegalArgumentException> {
            // Выражение с недопустимыми числами
            prefixToInfix("5 abc 3")
        }
    }
}