package omkar.android.projects.data.source

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.domain.model.sensory.VisualInput
import omkar.android.projects.domain.repository.VisualInputSource

expect class CameraDataSource: VisualInputSource {
    override val visualInput: Flow<VisualInput>
    override fun startCamera()
    override fun stopCamera()
}