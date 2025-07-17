package fi.tuska.beerclock.util

import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.TypeParceler
import kotlin.time.Instant

actual typealias CommonParcelable = Parcelable

actual typealias CommonParceler<T> = Parceler<T>
actual typealias CommonTypeParceler<T, P> = TypeParceler<T, P>

actual object ToNullParceler : Parceler<Any?> {
    override fun create(parcel: Parcel): Any? {
        return null
    }

    override fun Any?.write(parcel: Parcel, flags: Int) {
        // No need to write anything
    }
}


actual object LocalDateParceler : Parceler<LocalDate?> {
    override fun create(parcel: Parcel): LocalDate? {
        val data = parcel.readString()
        return if (data == "null") null else data?.let {
            (LocalDate::parse)(
                it,
                LocalDate.Formats.ISO
            )
        }
    }

    override fun LocalDate?.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this?.toString() ?: "null")
    }
}


actual object InstantParceler : Parceler<Instant?> {
    override fun create(parcel: Parcel): Instant? {
        val data = parcel.readString()
        return if (data == "null") null else data?.let { (Instant::parse)(it) }
    }

    override fun Instant?.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this?.toString() ?: "null")
    }
}
