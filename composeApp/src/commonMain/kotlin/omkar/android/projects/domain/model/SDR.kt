package omkar.android.projects.domain.model

import omkar.android.projects.app.constants.Constants.SdrConstants.SDR_SIZE

data class SDR(
    val size: Int = SDR_SIZE,
    val activeBits: IntArray
) {
    init {
        activeBits.sort()

        require(activeBits.toSet().size == activeBits.size) {
            "SDR bits must be unique"
        }
    }

    fun overlap(other: SDR): Int {
        var i = 0
        var j = 0
        var count = 0

        while (i < activeBits.size && j < other.activeBits.size) {
            when {
                activeBits[i] == other.activeBits[j] -> {
                    count++; i++; j++
                }
                activeBits[i] < other.activeBits[j] -> i++
                else -> j++
            }
        }
        return count
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SDR

        if (size != other.size) return false
        if (!activeBits.contentEquals(other.activeBits)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + activeBits.contentHashCode()
        return result
    }
}