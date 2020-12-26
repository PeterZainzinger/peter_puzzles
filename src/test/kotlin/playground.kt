import org.junit.jupiter.api.Test
import shared.toMatrix


class PlaygroundTest {

    @Test
    fun matrixMul() {
        val input = listOf(
            listOf(0.7220180, 0.07121225, 0.6881997),
            listOf(-0.2648886, -0.89044952, 0.3700456),
            listOf(-0.6391588, 0.44947578, 0.6240573)
        ).toMatrix()
        val inverse = input.inverse()
        println(input.mm(inverse))
    }

}