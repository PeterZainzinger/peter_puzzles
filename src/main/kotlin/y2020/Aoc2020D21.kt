package y2020

import shared.fold1

object Aoc2020D21 {

    data class Food(
        val ingridients: Set<String>,
        val allergens: Set<String>
    )

    fun solveFoods(input: List<Food>): Map<String, String> {
        var currentInput = input.toList()
        // allergen -> ingriedent
        val result = mutableMapOf<String, String>()

        val allAllergens = input.flatMap { it.allergens }.toSet()

        while (result.size < allAllergens.size) {
            var found = false
            val leftAllagens = currentInput.flatMap { it.allergens }.toSet()
            allAllergens.forEach { allergen ->
                // try to resolve with a given allergen
                val options = currentInput
                    .filter { it.allergens.contains(allergen) }
                    .map { it.ingridients }
                    .fold1 { acc, item -> acc.intersect(item) } ?: emptySet()
                if (options.size == 1) {
                    found = true
                    val matchedIngridient = options.first()
                    result[allergen] = matchedIngridient
                    currentInput =
                        currentInput.map {
                            it.copy(
                                ingridients = it.ingridients.filterNot { it == matchedIngridient }.toSet(),
                                allergens = it.allergens.filterNot { it == allergen }.toSet()
                            )
                        }
                }
            }

            if (!found) {
                throw Exception("nothing found")
            }
        }
        return result.toMap()
    }
}
