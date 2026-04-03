package omkar.android.projects.domain.usecases

import co.touchlab.kermit.Logger
import omkar.android.projects.domain.repository.SensoryEncoder
import omkar.android.projects.domain.repository.VisualInputSource

private const val TAG = "CorticalLoopUseCase"

class CorticalLoopUseCase (
    private val sensoryEncoder: SensoryEncoder
) {
    suspend operator fun invoke(visualInputSource: VisualInputSource) {
        visualInputSource.visualInput.collect { visualInput ->
            // Sparse Distributed Representation
            val sdr = sensoryEncoder.encode(visualInput)
            Logger.withTag(TAG).d("SDR: $sdr")

            // Reference frame bits addition

            // Invariant representation

            // Add to sequence

            // Predict
        }
    }
}