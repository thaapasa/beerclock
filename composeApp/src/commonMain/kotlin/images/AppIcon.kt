package fi.tuska.beerclock.images

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import beerclock.composeapp.generated.resources.Res
import beerclock.composeapp.generated.resources.ic_add_circle
import beerclock.composeapp.generated.resources.ic_arrow_back
import beerclock.composeapp.generated.resources.ic_bolt
import beerclock.composeapp.generated.resources.ic_calendar
import beerclock.composeapp.generated.resources.ic_car
import beerclock.composeapp.generated.resources.ic_car_crash
import beerclock.composeapp.generated.resources.ic_chevron_left
import beerclock.composeapp.generated.resources.ic_chevron_right
import beerclock.composeapp.generated.resources.ic_clock
import beerclock.composeapp.generated.resources.ic_close
import beerclock.composeapp.generated.resources.ic_contract
import beerclock.composeapp.generated.resources.ic_date_range
import beerclock.composeapp.generated.resources.ic_delete
import beerclock.composeapp.generated.resources.ic_edit
import beerclock.composeapp.generated.resources.ic_experiment
import beerclock.composeapp.generated.resources.ic_female
import beerclock.composeapp.generated.resources.ic_glass_full
import beerclock.composeapp.generated.resources.ic_graph
import beerclock.composeapp.generated.resources.ic_history
import beerclock.composeapp.generated.resources.ic_info
import beerclock.composeapp.generated.resources.ic_input
import beerclock.composeapp.generated.resources.ic_language
import beerclock.composeapp.generated.resources.ic_local_bar
import beerclock.composeapp.generated.resources.ic_male
import beerclock.composeapp.generated.resources.ic_menu
import beerclock.composeapp.generated.resources.ic_moon
import beerclock.composeapp.generated.resources.ic_palette
import beerclock.composeapp.generated.resources.ic_person
import beerclock.composeapp.generated.resources.ic_save_as
import beerclock.composeapp.generated.resources.ic_search
import beerclock.composeapp.generated.resources.ic_settings
import beerclock.composeapp.generated.resources.ic_speed
import beerclock.composeapp.generated.resources.ic_sports_bar
import beerclock.composeapp.generated.resources.ic_star
import beerclock.composeapp.generated.resources.ic_star_filled
import beerclock.composeapp.generated.resources.ic_water_drop
import fi.tuska.beerclock.settings.Gender
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
enum class AppIcon(private val drawable: DrawableResource) : Image {
    ADD_CIRCLE(Res.drawable.ic_add_circle),
    ARROW_BACK(Res.drawable.ic_arrow_back),
    BEER(Res.drawable.ic_sports_bar),
    BOLT(Res.drawable.ic_bolt),
    CALENDAR(Res.drawable.ic_calendar),
    CALENDAR_WEEK(Res.drawable.ic_date_range),
    CAR(Res.drawable.ic_car),
    CAR_ALERT(Res.drawable.ic_car_crash),
    CHEVRON_LEFT(Res.drawable.ic_chevron_left),
    CHEVRON_RIGHT(Res.drawable.ic_chevron_right),
    CLOCK(Res.drawable.ic_clock),
    CLOSE(Res.drawable.ic_close),
    CONTRACT(Res.drawable.ic_contract),
    DELETE(Res.drawable.ic_delete),
    EXPERIMENT(Res.drawable.ic_experiment),
    DRINK(Res.drawable.ic_local_bar),
    EDIT(Res.drawable.ic_edit),
    FEMALE(Res.drawable.ic_female),
    GAUGE(Res.drawable.ic_speed),
    GLASS_FULL(Res.drawable.ic_glass_full),
    GRAPH(Res.drawable.ic_graph),
    HISTORY(Res.drawable.ic_history),
    INFO(Res.drawable.ic_info),
    INPUT(Res.drawable.ic_input),
    LANGUAGE(Res.drawable.ic_language),
    MALE(Res.drawable.ic_male),
    MENU(Res.drawable.ic_menu),
    MOON(Res.drawable.ic_moon),
    PALETTE(Res.drawable.ic_palette),
    PERSON(Res.drawable.ic_person),
    SAVE_AS(Res.drawable.ic_save_as),
    SETTINGS(Res.drawable.ic_settings),
    STAR(Res.drawable.ic_star),
    STAR_FILLED(Res.drawable.ic_star_filled),
    SEARCH(Res.drawable.ic_search),
    WATER_DROP(Res.drawable.ic_water_drop);

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter {
        return painterResource(this.drawable)
    }

    @Composable
    fun icon(
        contentDescription: String = name,
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
    ) {
        Icon(
            painter = painter(),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }

    @Composable
    fun iconButton(
        contentDescription: String = name,
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
    ) {
        IconButton(onClick = onClick, content = { icon(contentDescription) }, modifier = modifier)
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
