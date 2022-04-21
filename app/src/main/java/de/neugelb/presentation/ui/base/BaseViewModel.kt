package de.neugelb.presentation.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext
import de.neugelb.domain.usecase.Error
import de.neugelb.domain.usecase.Result
import de.neugelb.domain.usecase.Success

abstract class BaseViewModel : ViewModel(), CoroutineScope, KoinComponent {

    val errorLiveEvent: LiveEvent<Error<*>> = LiveEvent()

    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + supervisorJob + CoroutineExceptionHandler { _, _ -> Unit }

    fun doInBackground(action: suspend () -> Unit) = launch {
        withContext(Dispatchers.IO) { action() }
    }

    fun launchUnit(action: suspend () -> Unit) {
        launch { action() }
    }

    protected inline fun <T> Result<T>.doOnSuccess(action: (T) -> Unit) {
        when (this) {
            is Error -> errorLiveEvent.postValue(this)
            is Success -> action(this.value)
        }
    }

    protected suspend fun <T> Result<T>.doOnError(action: suspend (Error<T>) -> Unit) {
        when (this) {
            is Error -> action(this)
            is Success -> Unit
        }
    }

    override fun onCleared() {
        supervisorJob.cancelChildren()
        super.onCleared()
    }
}