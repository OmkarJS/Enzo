package omkar.android.projects.domain.repository.encoders

import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.sensory.SensoryInput

interface SensoryEncoder {
    fun encode(sensoryInput: SensoryInput): SDR
    fun decodeBrightness(reconstructedSdr: SDR): Int
}