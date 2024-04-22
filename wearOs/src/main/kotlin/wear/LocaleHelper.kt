package fi.tuska.beerclock.wear

import android.content.Context
import java.util.Locale


/**
 * This class is used to change your application locale on-the-fly.
 * <p/>
 * Originally created by gunhansancar on 07/10/15.
 * <p/>
 * Implementation adapted from https://medium.com/@gunhan/change-language-programmatically-in-android-69d4756c7d79
 */
object LocaleHelper {

    fun setCurrentLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

}
