package fi.tuska.beerclock.util

import android.os.Parcel
import fi.tuska.beerclock.screens.history.HistoryScreen

fun <T> parcelizeAndRead(value: T): T {
    val parcel = Parcel.obtain()
    parcel.writeValue(value)
    parcel.setDataPosition(0)
    val readValue = parcel.readValue(HistoryScreen::class.java.classLoader)
    return readValue as T
}
