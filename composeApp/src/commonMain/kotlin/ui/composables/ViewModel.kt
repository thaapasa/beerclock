package fi.tuska.beerclock.ui.composables

import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

private val logger = getLogger("ViewModel")

open class ViewModel : CoroutineScope by CoroutineScope(Dispatchers.Main), Disposable {

    protected var isActive = true
        private set

    override fun onDispose() {
        logger.debug("Disposing ${this::class.simpleName}")
        cancel() // Cancel all coroutines when the ViewModel is cleared
        isActive = false
    }

    fun scope(): CoroutineScope {
        return this
    }
}
