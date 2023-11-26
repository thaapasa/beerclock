package fi.tuska.beerclock.localization

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


actual fun getDecimalFormatter(
    maximumFractionDigits: Int,
    decimalSeparator: Char,
    isGroupingUsed: Boolean,
    groupingSeparator: Char
): (value: Double) -> String {
    val formatter = DecimalFormat().apply {
        this.isGroupingUsed = isGroupingUsed
        this.maximumFractionDigits = maximumFractionDigits
        this.decimalFormatSymbols = DecimalFormatSymbols().apply {
            this.decimalSeparator = decimalSeparator
            this.groupingSeparator = groupingSeparator
        }
    }
    return { value: Double -> formatter.format(value) }
}