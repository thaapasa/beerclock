package fi.tuska.beerclock.common.localization

object EnStrings : Strings {
    override val appName = "Beer Clock"
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val main = "Menu"
        override val settings = "Settings"
        override val drinks = "Drinks"
        override val statistics = "Statistics"
    }

    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Settings"
        override val weightLabel = "Weight in kg"
        override val genderLabel = "Gender"
    }

    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Male"
        override val female = "Female"
    }
}