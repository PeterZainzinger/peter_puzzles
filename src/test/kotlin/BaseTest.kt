import org.junit.jupiter.api.Assertions.assertEquals

abstract class BaseTest<T>(
    private val year: Int,
    private val day: Int,
) {

    abstract fun parseInput(input: String): T

    fun getInput(example: Int? = null) =
        parseInput(loadInputAsText(example))

    private fun loadInputAsText(example: Int? = null) = loadFileWithName(
        "y$year/${day}${
        when (example) {
            null -> ""
            else -> "_ex$example"
        }
        }.txt"
    )

    protected fun loadFileWithName(name: String) = object {}.javaClass.getResource(name).readText()
}

fun assertNumber(should: Int, actual: Int) = assertEquals(should, actual)
fun assertNumber(should: Long, actual: Long) = assertEquals(should, actual)
fun assertString(should: String, actual: String) = assertEquals(should, actual)
