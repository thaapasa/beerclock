package fi.tuska.beerclock.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

// For Android @Parcelize
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class CommonParcelize()

expect interface CommonParcelable

// For Android @TypeParceler
@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Retention(AnnotationRetention.SOURCE)
@Repeatable
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
expect annotation class CommonTypeParceler<T, P : CommonParceler<in T>>()

// For Android Parceler
expect interface CommonParceler<T>

expect object ToNullParceler : CommonParceler<Any?>
expect object LocalDateParceler : CommonParceler<LocalDate?>
expect object InstantParceler : CommonParceler<Instant?>
