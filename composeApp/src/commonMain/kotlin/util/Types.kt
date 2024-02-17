package fi.tuska.beerclock.util

typealias SuspendAction<T> = suspend (vm: T) -> Unit
