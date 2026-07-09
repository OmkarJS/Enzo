package omkar.android.projects.domain.usecases

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import omkar.android.projects.data.repository.temporalmemory.TemporalMemoryImpl
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.repository.encoders.SensoryEncoder
import omkar.android.projects.domain.repository.inputsource.VisualInputSource
import omkar.android.projects.domain.repository.spatialpooler.ISpatialPooler
import omkar.android.projects.domain.repository.temporalmemory.ITemporalMemory

private const val TAG = "CorticalLoopUseCase"

data class CorticalState(
    val actualBrightness: Int,
    val predictedBrightness: Int,
    val confidence: Int
)

class CorticalLoopUseCase (
    private val sensoryEncoder: SensoryEncoder,
    private val iSpatialPooler: ISpatialPooler,
    private val iTemporalMemory: ITemporalMemory
) {
    private val _uiState = MutableStateFlow<CorticalState?>(null)
    val uiState = _uiState.asStateFlow()

    suspend operator fun invoke(visualInputSource: VisualInputSource) {
        visualInputSource.visualInput.collect { visualInput ->

            val sensorySDR: SDR = sensoryEncoder.encode(visualInput)

            // Spatial Pooler
            val pooledSDR: SDR = iSpatialPooler.compute(
                sensorySDR,
                learn = true
            )

            // Temporal Memory
            val predictionSDR = iTemporalMemory.compute(
                pooledSDR,
                learn = true
            )

            // DECODING
            val reconstructedSensory = iSpatialPooler.decode(predictionSDR)
            val predictedVal = sensoryEncoder.decodeBrightness(reconstructedSensory)

            // CALCULATE CONFIDENCE
            val overlap = pooledSDR.overlap(predictionSDR)
            val confidence = (overlap.toFloat() / pooledSDR.activeBits.size * 100).toInt()

            _uiState.value = CorticalState(
                actualBrightness = visualInput.brightness,
                predictedBrightness = predictedVal,
                confidence = confidence
            )

            logResults(pooledSDR, predictionSDR)
        }
    }

    private fun logResults(actual: SDR, predicted: SDR) {
        val overlap = actual.overlap(predicted)
        val confidence = if (actual.activeBits.isNotEmpty()) {
            overlap.toFloat() / actual.activeBits.size
        } else 0f

        Logger.withTag(TAG).d(
            "HTM_RESULT: Confidence: ${ (confidence * 100).toInt() }% | " +
                    "Actual Active: ${actual.activeBits.size} | " +
                    "Overlap: $overlap"
        )

        if (confidence < 0.1f) {
            Logger.withTag("HTM_RESULT").w("ANOMALY DETECTED: Sequence broken or new environment.")
        }
    }
}