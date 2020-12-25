package y2020

object Aoc2020D02 {
    data class PasswordLine(
        val start: Int,
        val end: Int,
        val letter: Char,
        val password: String
    ) {
        fun isValid(): Boolean {
            val letterCount = password.toCharArray().toList().filter { it == letter }.count()
            return letterCount in start..end
        }

        fun isValidOther(): Boolean {
            val firstLetter = password[start - 1] == letter
            val secondLetter = password[end - 1] == letter
            //xor
            return (firstLetter || secondLetter) && !(firstLetter && secondLetter)
        }

    }

}


