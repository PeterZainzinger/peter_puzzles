import org.junit.jupiter.api.Assertions.assertEquals

abstract class BaseTest<T>(
    private val year: Int,
    private val day: Int,
) {

    abstract fun parseInput(input: String): T

    fun getInput(example: Int? = null) =
        parseInput(loadInputAsText(example))

    private fun loadInputAsText(example: Int? = null) = object {}.javaClass.getResource(
        "y${year}/${day}${
            when (example) {
                null -> ""
                else -> "_ex$example"
            }
        }.txt"
    ).readText()
}


fun assertNumber(should: Int, actual: Int) = assertEquals(should, actual)
fun assertNumber(should: Long, actual: Long) = assertEquals(should, actual)

