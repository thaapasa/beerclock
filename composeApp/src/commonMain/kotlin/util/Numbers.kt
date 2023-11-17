package fi.tuska.beerclock.util

fun safeToDouble(str: String): Double? = try {
    str.toDouble()
} catch (e: NumberFormatException) {
    null
} catch (e: NullPointerException) {
    null
}

