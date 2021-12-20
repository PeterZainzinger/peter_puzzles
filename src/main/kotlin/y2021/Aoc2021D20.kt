package y2021

import shared.*


object Aoc2021D20 {
    data class Picture(
        val algorithm: String,
        val picture: Map<Point, Boolean>,
        val original: Map<Point, Boolean> = picture
    ) {

        val lightenUp: Int
            get() = picture.filter { it.value }.size

        private fun enhance(iteration: Int): Picture {
            val newMap =
                picture.keys.flatMap { getNeighborsForPoint(it) }.toSet().associateWith { getNewPixel(it, iteration) }
            return Picture(algorithm, newMap, original)
        }

        fun pictureAfterSteps(steps: Int): Picture {
            var current = this
            repeat(steps) {
                current = current.enhance(it + 1)

            }
            return current
        }

        private fun getNewPixel(point: Point, iteration: Int): Boolean {
            val number = getNeighborsForPoint(point).joinToString("") {
                when (picture[it]) {
                    true -> "1"
                    false -> "0"
                    null -> when (algorithm.first()) {
                        '#' -> when (iteration % 2) {
                            1 -> "0"
                            else -> "1"
                        }
                        else -> "0"
                    }
                }
            }.toInt(2)

            return algorithm[number] == '#'
        }

        override fun toString(): String {
            val topLeft = Point(picture.keys.minOf { it.x - 1 }, picture.keys.minOf { it.y - 1 })
            val bottomRight = Point(picture.keys.maxOf { it.x + 1 }, picture.keys.maxOf { it.y + 1 })
            return (topLeft.y..bottomRight.y).joinToString("\n") { y ->
                (topLeft.x..bottomRight.x).joinToString("") { x ->
                    when (picture[Point(x, y)]) {
                        null, false -> "."
                        else -> "#"
                    }
                }
            }

        }

        companion object {
            fun parse(text: String): Picture {
                val (algo, picture) = text.split("\n\n")

                return Picture(algo, picture.splitLines().flatMapIndexed { y, s ->
                    s.splitChars().mapIndexed { x, c ->
                        when (c) {
                            "#" -> Point(x, y) to true
                            else -> Point(x, y) to false
                        }
                    }
                }.toMap())
            }
        }

    }

    private fun getNeighborsForPoint(point: Point) = (point.y - 1..point.y + 1).flatMap { y ->
        (point.x - 1..point.x + 1).map { x ->
            Point(x, y)
        }
    }
}


