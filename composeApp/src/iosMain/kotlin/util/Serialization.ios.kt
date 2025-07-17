package fi.tuska.beerclock.util

import kotlinx.datetime.LocalDate
import kotlin.time.Instant

// Note: no need to define CommonParcelize here (bc its @OptionalExpectation)
actual interface CommonParcelable  // not used on iOS

// Note: no need to define CommonTypeParceler<T,P : CommonParceler<in T>> here (bc its @OptionalExpectation)
actual interface CommonParceler<T> // not used on iOS
actual object ToNullParceler : CommonParceler<Any?> // not used on iOS
actual object LocalDateParceler : CommonParceler<LocalDate?> // not used on iOS
actual object InstantParceler : CommonParceler<Instant?> // not used on iOS
