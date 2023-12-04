package fi.tuska.beerclock.settings

enum class Gender(
    /**
     * Approximate liquid volume multipliers for genders; i.e. how much of your body mass
     * is liquid.
     */
    val liquidVolumePercentage: Double
) {
    MALE(0.75), FEMALE(0.66);

    companion object {
        fun safeValueOf(s: String): Gender? = try {
            Gender.valueOf(s)
        } catch (e: IllegalArgumentException) {
            // Thrown if s doesn't match any gender
            null
        }
    }
}
