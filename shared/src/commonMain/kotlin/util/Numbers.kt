package fi.tuska.beerclock.common.util

fun safeToDouble(str: String): Double? = try {
    str.toDouble()
} catch (e: NumberFormatException) {
    null
} catch (e: NullPointerException) {
    null
}

