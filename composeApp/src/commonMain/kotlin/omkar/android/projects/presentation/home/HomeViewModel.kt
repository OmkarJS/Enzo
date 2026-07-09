package omkar.android.projects.presentation.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.app.expectuals.getViewModelScope
import omkar.android.projects.domain.repository.inputsource.VisualInputSource
import omkar.android.projects.domain.usecases.CorticalLoopUseCase
import omkar.android.projects.domain.usecases.CorticalState

class HomeViewModel(
    private val corticalLoopUseCase: CorticalLoopUseCase
) {
    private val viewModelScope: CoroutineScope = getViewModelScope()

    val uiState: StateFlow<CorticalState?> = corticalLoopUseCase.uiState

    fun startCorticalLoop(source: VisualInputSource) {
        viewModelScope.launch {
            corticalLoopUseCase.invoke(source)
        }
    }
}