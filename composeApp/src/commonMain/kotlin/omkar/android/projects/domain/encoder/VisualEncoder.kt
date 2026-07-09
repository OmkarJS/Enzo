package omkar.android.projects.domain.encoder

import omkar.android.projects.app.constants.Constants.SdrConstants
import omkar.android.projects.data.model.pose.Pose
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.sensory.SensoryInput
import omkar.android.projects.domain.model.sensory.VisualInput
import omkar.android.projects.domain.repository.encoders.SensoryEncoder

class VisualEncoder : SensoryEncoder {

    override fun encode(sensoryInput: SensoryInput): SDR {
        val input = sensoryInput as VisualInput
        val activeBits = mutableSetOf<Int>()

        encodeSlidingWindow(
            value = input.brightness.toFloat(),
            min = 0f,
            max = 255f,
            offset = SdrConstants.VISION_START,
            totalBits = SdrConstants.VISION_TOTAL,
            activeCount = SdrConstants.VISION_ACTIVE_BITS,
            activeBits = activeBits
        )

        encodePose(input.pose, activeBits)

        return SDR(activeBits = activeBits.toIntArray())
    }

    override fun decodeBrightness(reconstructedSdr: SDR): Int {
        val visionBits = reconstructedSdr.activeBits.filter {
            it >= SdrConstants.VISION_START && it < SdrConstants.VISION_START + SdrConstants.VISION_TOTAL
        }

        if (visionBits.isEmpty()) return 0

        // Find the average position of the active bits (Center of Mass)
        val averageBitIndex = visionBits.average().toFloat()
        val relativeIndex = averageBitIndex - SdrConstants.VISION_START

        val resolution = SdrConstants.VISION_TOTAL - SdrConstants.VISION_ACTIVE_BITS
        val brightness = (relativeIndex / resolution) * 255f

        return brightness.toInt().coerceIn(0, 255)
    }

    private fun encodePose(pose: Pose, activeBits: MutableSet<Int>) {
        val refStart = SdrConstants.REF_START

        // Quaternions: 4 components * 80 bits = 320 bits
        encodeSlidingWindow(pose.quaternionX, -1f, 1f, refStart, 80, 10, activeBits)
        encodeSlidingWindow(pose.quaternionY, -1f, 1f, refStart + 80, 80, 10, activeBits)
        encodeSlidingWindow(pose.quaternionZ, -1f, 1f, refStart + 160, 80, 10, activeBits)
        encodeSlidingWindow(pose.quaternionValue, -1f, 1f, refStart + 240, 80, 10, activeBits)

        // Angular Velocity: 3 components * 40 bits = 120 bits
        encodeSlidingWindow(pose.angularVelocityX, -10f, 10f, refStart + 320, 40, 6, activeBits)
        encodeSlidingWindow(pose.angularVelocityY, -10f, 10f, refStart + 360, 40, 6, activeBits)
        encodeSlidingWindow(pose.angularVelocityZ, -10f, 10f, refStart + 400, 40, 6, activeBits)

        // Tilt: 72 bits
        encodeSlidingWindow(pose.tilt, -1.57f, 1.57f, refStart + 440, 72, 8, activeBits)
    }

    /**
     * Standard HTM Scalar Encoder logic.
     * Maps a value to a contiguous range of bits to ensure semantic overlap.
     */
    private fun encodeSlidingWindow(
        value: Float,
        min: Float,
        max: Float,
        offset: Int,
        totalBits: Int,
        activeCount: Int,
        activeBits: MutableSet<Int>
    ) {
        val clippedValue = value.coerceIn(min, max)
        val range = max - min

        val resolution = totalBits - activeCount
        val startBit = ((clippedValue - min) / range * resolution).toInt()

        for (i in 0 until activeCount) {
            activeBits.add(offset + startBit + i)
        }
    }
}