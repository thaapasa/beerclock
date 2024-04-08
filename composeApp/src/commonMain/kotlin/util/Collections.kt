package fi.tuska.beerclock.util

fun Collection<Any>.clampIndex(index: Int): Int {
    return index.coerceIn(0, size - 1)
}

