package fi.tuska.beerclock.util

fun safeToDouble(str: String): Double? = try {
    str.toDouble()
} catch (e: NumberFormatException) {
    null
} catch (e: NullPointerException) {
    null
}

fun Int.zeroPad(length: Int): String {
    return this.toString().padStart(length, '0')
}

inline fun Int.clamp(min: Int, max: Int): Int {
    return if (this < min) min else if (this > max) max else this
}
