package fi.tuska.beerclock.localization

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

actual fun getDecimalFormatter(
    maximumFractionDigits: Int,
    decimalSeparator: Char,
    isGroupingUsed: Boolean,
    groupingSeparator: Char
): (number: Double) -> String {
    val formatter = NSNumberFormatter().apply {
        this.maximumFractionDigits = maximumFractionDigits.toULong()
        this.usesGroupingSeparator = isGroupingUsed
        this.groupingSeparator = groupingSeparator.toString()
        this.decimalSeparator = decimalSeparator.toString()
    }
    return { value: Double -> formatter.stringFromNumber(number = NSNumber(double = value)) ?: "" }
}
