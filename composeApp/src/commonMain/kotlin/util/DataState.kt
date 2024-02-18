package fi.tuska.beerclock.util

sealed class DataState<out T> {
    data object Initial : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()

    fun <R> mapOr(f: (value: T) -> R, defaultValue: R): R {
        return if (this is Success) f(this.data) else defaultValue
    }
    
    fun valueOrNull(): T? {
        return if (this is Success) this.data else null
    }
}

