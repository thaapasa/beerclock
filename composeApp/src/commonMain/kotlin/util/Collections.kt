package fi.tuska.beerclock.util

inline fun Collection<Any>.clampIndex(index: Int): Int {
    return index.clamp(0, size - 1)
}

