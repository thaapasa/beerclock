package fi.tuska.beerclock.localization

object FiStrings : Strings {
    override val appName = "Kaljakello"
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val main = "Valikko"
        override val settings = "Asetukset"
        override val drinks = "Juomat"
        override val statistics = "Tilastot"
    }

    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Asetukset"
        override val weightLabel = "Paino kiloina"
        override val genderLabel = "Sukupuoli"
    }

    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Mies"
        override val female = "Nainen"
    }

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Merkkaa juoma"
        override val submit = "Juo!"
    }

    override val newDrink = NewDrinks
}