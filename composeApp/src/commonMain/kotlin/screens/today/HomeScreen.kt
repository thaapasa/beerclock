package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.drinks.create.NewDrinkButton
import fi.tuska.beerclock.ui.components.BacStatusCard
import fi.tuska.beerclock.ui.components.HelpButton
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import fi.tuska.beerclock.util.CommonParcelize

@CommonParcelize
object HomeScreen : ParcelableScreen {

    @Composable
    override fun Content() {
        val vm = rememberWithDispose { HomeViewModel() }

        LaunchedEffect(Unit) {
            vm.loadTodaysDrinks()
        }

        MainLayout(
            actionButton = { NewDrinkButton() },
            snackbarHostState = vm.snackbar,
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                BacStatusCard(vm)
                StatusHighlights(vm)
                Spacer(modifier = Modifier.height(16.dp))
                DailyBacGraph(bac = vm.bacStatus, now = vm.now)
            }
        }
    }
}

@Composable
fun StatusHighlights(vm: HomeViewModel) {
    if (vm.canDrive() && !vm.isYesterday) {
        return
    }
    val iconBg = MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
    val strings = Strings.get()
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (vm.isYesterday) {
            HelpButton(text = strings.home.yesterday) { onClick ->
                AppIcon.MOON.icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable(onClick = onClick)
                        .background(iconBg)
                        .padding(8.dp)
                )
            }
        }
        if (!vm.canDrive()) {
            Spacer(modifier = Modifier.width(8.dp))
            HelpButton(text = strings.home.cantDrive(vm.timeWhenSober())) { onClick ->
                AppIcon.CAR_ALERT.icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable(onClick = onClick)
                        .background(iconBg)
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}
