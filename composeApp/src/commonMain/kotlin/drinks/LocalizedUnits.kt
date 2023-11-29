package fi.tuska.beerclock.drinks

/**
 * Map of the weights of a single unit of alcohol, in grams, in different countries,
 * keyed by the ISO country code.
 *
 * These were taken from Wikipedia [Standard drink](https://en.wikipedia.org/wiki/Standard_drink)
 * page on 29.11.2023.
 */
val SingleUnitWeights = mapOf(
    "AT" to 20.0, // Austria
    "AU" to 10.0, // Australia
    "CA" to 13.5, // Canada
    "DK" to 12.0, // Denmark
    "ES" to 10.0, // Spain
    "FI" to 12.0, // Finland
    "FR" to 10.0, // France
    "GB" to 8.0, // UK
    "HU" to 17.0, // Hungary
    "IE" to 10.0, // Ireland
    "IS" to 8.0, // Iceland
    "IT" to 12.0, // Italy
    "JP" to 19.75, // Japan
    "NL" to 10.0, // Netherlands
    "NZ" to 10.0, // New Zealand
    "PL" to 10.0, // Poland
    "PT" to 11.0, // Portugal
    "SE" to 12.0, // Sweden
    "US" to 14.0, // USA
)
