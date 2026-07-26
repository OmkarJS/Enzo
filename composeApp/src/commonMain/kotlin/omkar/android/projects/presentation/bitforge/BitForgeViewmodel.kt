package omkar.android.projects.presentation.bitforge

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import omkar.android.projects.app.expectuals.getViewModelScope
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.touch.TouchInput
import omkar.android.projects.domain.usecases.BitForgeUseCase

private const val TAG = "BitForgeViewmodel"

class BitForgeViewmodel(
    private val bitForgeUseCase: BitForgeUseCase
) {
    private val viewModelScope: CoroutineScope = getViewModelScope()

    private var _bitForgeValue = MutableStateFlow<SDR?>(null)
    val bitForgeValue: StateFlow<SDR?> = _bitForgeValue.asStateFlow()

    fun touchInput(
        input: TouchInput
    ) {
        viewModelScope.launch {
            bitForgeUseCase.invoke(input).collect {
                _bitForgeValue.value = it
                Logger.withTag(TAG).d("touchInput: $it")
            }
        }
    }
}