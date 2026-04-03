package omkar.android.projects.presentation.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.app.utils.ApiResponseWrapper
import omkar.android.projects.app.expectuals.getViewModelScope
import omkar.android.projects.domain.repository.VisualInputSource
import omkar.android.projects.domain.usecases.CorticalLoopUseCase

class HomeViewModel(
    private val corticalLoopUseCase: CorticalLoopUseCase
) {
    private val viewModelScope: CoroutineScope = getViewModelScope()

    fun startCorticalLoop(source: VisualInputSource) {
        viewModelScope.launch {
            corticalLoopUseCase.invoke(source)
        }
    }
}