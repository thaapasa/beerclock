package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import fi.tuska.beerclock.localization.Strings

data class Tab<T>(val title: (strings: Strings) -> String, val data: T)

@Composable
fun <T> TabSwitcher(
    tabs: List<Tab<T>>,
    selectedTab: T,
    modifier: Modifier = Modifier,
    onClick: (tab: Tab<T>) -> Unit,
) {
    val strings = Strings.get()
    SegmentedButton(modifier = modifier) {
        tabs.forEachIndexed { index, tab ->
            if (index > 0) {
                SegmentDivider()
            }
            if (tab.data == selectedTab) {
                TextSegment(
                    tab.title(strings),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    background = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onClick(tab) }
                )
            } else {
                TextSegment(tab.title(strings), modifier = Modifier.clickable { onClick(tab) })
            }
        }
    }
}
