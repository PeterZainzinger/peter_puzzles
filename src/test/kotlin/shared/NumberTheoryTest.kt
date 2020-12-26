package shared

import assertNumber
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.random.Random

class NumberTheoryTest {

    @Test
    fun chineseRemainderBasic() {
        val res = chineseRemainder(
            listOf(
                2 to 3,
                3 to 4,
                2 to 5,
            )
        )
        assertNumber(47, res)
    }

    @Test
    fun primeFactors() {
        repeat(1000000) {
            val test = Random.nextInt(10000000)
            val factors = primeFactors(test)
            val probe = factors.map { (key, value) ->
                key.toDouble().pow(value)
            }.product().toInt()
            assertNumber(test, probe)
        }
    }
}