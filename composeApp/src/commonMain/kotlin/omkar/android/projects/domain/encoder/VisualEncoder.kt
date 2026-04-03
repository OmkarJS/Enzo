package omkar.android.projects.domain.encoder

import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.sensory.SensoryInput
import omkar.android.projects.domain.model.sensory.VisualInput
import omkar.android.projects.domain.repository.SensoryEncoder
import kotlin.random.Random
import omkar.android.projects.app.constants.Constants.SdrConstants.BRIGHTNESS_BUCKETS
import omkar.android.projects.app.constants.Constants.SdrConstants.BRIGHTNESS_SEED
import omkar.android.projects.app.constants.Constants.SdrConstants.VISION_ACTIVE_BITS
import omkar.android.projects.app.constants.Constants.SdrConstants.VISION_START
import omkar.android.projects.app.constants.Constants.SdrConstants.VISION_TOTAL

class VisualEncoder(): SensoryEncoder {

    companion object {
        private const val MAX_BRIGHTNESS_VALUE = 255
    }

    override fun encode(sensoryInput: SensoryInput): SDR {
        val visualInput = sensoryInput as VisualInput
        val activeBits = mutableSetOf<Int>()

        encodeScalar(
            value = visualInput.brightness,
            activeBits = activeBits
        )

        return SDR(
            activeBits = activeBits.toIntArray()
        )
    }

    private fun encodeScalar(
        value: Int,
        activeBits: MutableSet<Int>
    ) {
        // Calculate which bucket value falls into
        val bucket = (value * BRIGHTNESS_BUCKETS) / (MAX_BRIGHTNESS_VALUE + 1)

        val random = Random(BRIGHTNESS_SEED + bucket)

        repeat(VISION_ACTIVE_BITS) {
            val bit = VISION_START + random.nextInt(VISION_TOTAL)
            activeBits.add(bit)
        }
    }
}