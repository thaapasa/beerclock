package fi.tuska.beerclock.settings

enum class Gender(
    /**
     * Approximate liquid volume multipliers for genders, to calculate
     * [volume of distribution](https://en.wikipedia.org/wiki/Volume_of_distribution).
     * Expressed as `L/kg`.
     *
     * Used values taken from [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     *
     * Vd is the volume of distribution (L);
     * typically body weight (kg) multiplied by 0.71 L/kg for men and 0.58 L/kg for women
     */
    val volumeOfDistributionMultiplier: Double
) {
    MALE(0.71), FEMALE(0.58);

    companion object {
        fun safeValueOf(s: String): Gender? = try {
            Gender.valueOf(s)
        } catch (e: IllegalArgumentException) {
            // Thrown if s doesn't match any gender
            null
        }
    }
}
