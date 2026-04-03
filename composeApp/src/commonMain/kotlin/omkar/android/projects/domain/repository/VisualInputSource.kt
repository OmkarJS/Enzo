package omkar.android.projects.domain.repository

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.domain.model.sensory.VisualInput

interface VisualInputSource {
    val visualInput: Flow<VisualInput>

    fun startCamera()
    fun stopCamera()
}