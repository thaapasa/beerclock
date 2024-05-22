package fi.tuska.beerclock.util

fun safeToDouble(str: String): Double? = try {
    str.toDouble()
} catch (e: NumberFormatException) {
    null
} catch (e: NullPointerException) {
    null
}

fun safeToInt(str: String): Int? = try {
    str.toInt()
} catch (e: NumberFormatException) {
    null
} catch (e: NullPointerException) {
    null
}

fun Int.zeroPad(length: Int): String {
    return this.toString().padStart(length, '0')
}
