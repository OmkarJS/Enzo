package omkar.android.projects.data.source

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.domain.model.sensory.VisualInput
import omkar.android.projects.domain.repository.inputsource.VisualInputSource

expect class AndroidVisualInputSource: VisualInputSource {
    override val visualInput: Flow<VisualInput>
    override fun startCamera()
    override fun stopCamera()
}