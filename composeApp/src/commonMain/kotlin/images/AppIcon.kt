package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import fi.tuska.beerclock.settings.Gender
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class AppIcon(private val path: String) {
    MALE("drawable/icons/male.xml"),
    FEMALE("drawable/icons/female.xml"),
    DRINK("drawable/icons/sports_bar.xml"),
    TODAY("drawable/icons/local_bar.xml"),
    HISTORY("drawable/icons/history.xml"),
    GRAPH("drawable/icons/graph.xml");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter {
        return painterResource(this.path)
    }

    companion object {
        fun forGender(gender: Gender): AppIcon {
            return when (gender) {
                Gender.MALE -> MALE;
                Gender.FEMALE -> FEMALE;
            }
        }
    }
}
