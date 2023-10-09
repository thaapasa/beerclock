package fi.tuska.beerclock.common

import androidx.compose.ui.window.ComposeUIViewController

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }