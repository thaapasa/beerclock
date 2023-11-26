package fi.tuska.beerclock.localization


expect fun getDecimalFormatter(
    maximumFractionDigits: Int,
    decimalSeparator: Char,
    isGroupingUsed: Boolean,
    groupingSeparator: Char
): (value: Double) -> String
