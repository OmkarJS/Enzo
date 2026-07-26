package omkar.android.projects.data.repository.spatialpooler

import omkar.android.projects.app.constants.Constants
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.repository.spatialpooler.ISpatialPooler
import kotlin.random.Random

class SpatialPoolerImpl(
    private val numInputs: Int = 2048,
    private val numColumns: Int = 2048,
    private val activeColumnsLimit: Int = 40, // ~2% sparsity
    private val potentialSynapses: Int = 20,  // Synapses per column
    private val permanenceThreshold: Float = 0.1f,
    private val permanenceIncrement: Float = 0.01f,
    private val permanenceDecrement: Float = 0.008f
): ISpatialPooler {
    private val columns = Array(numColumns) { Column(it) }

    inner class Column(val id: Int) {
        // Map of InputBitIndex to Permanence
        val synapses = mutableMapOf<Int, Float>()
        var overlap = 0

        init {
            // Randomly connect to potential input bits
            val random = Random(id)
            repeat(potentialSynapses) {
                val inputIndex = random.nextInt(numInputs)
                synapses[inputIndex] = 0.15f // Initial permanence
            }
        }
    }

    override fun compute(
        inputSdr: SDR,
        learn: Boolean
    ): SDR {
        val inputBits = inputSdr.activeBits

        // 1. Calculate Overlap
        columns.forEach { col ->
            col.overlap = col.synapses.count { (idx, perm) ->
                perm >= permanenceThreshold && inputBits.contains(idx)
            }
        }

        // 2. Global Inhibition (Pick top-k active columns)
        val activeColumns = columns
            .filter { it.overlap > 0 }
            .sortedByDescending { it.overlap }
            .take(activeColumnsLimit)
            .map { it.id }
            .toIntArray()

        // 3. Hebbian Learning
        if (learn) {
            activeColumns.forEach { colId ->
                val col = columns[colId]
                col.synapses.forEach { (idx, perm) ->
                    if (inputBits.contains(idx)) {
                        col.synapses[idx] = (perm + permanenceIncrement).coerceAtMost(1.0f)
                    } else {
                        col.synapses[idx] = (perm - permanenceDecrement).coerceAtLeast(0.0f)
                    }
                }
            }
        }

        return SDR(
            size = numColumns,
            activeBits = activeColumns
        )
    }

    override fun decode(predictedSdr: SDR): SDR {
        val bitVotes = IntArray(numInputs) { 0 }

        // Each predicted column "votes" for the input bits it is connected to
        predictedSdr.activeBits.forEach { colId ->
            val col = columns[colId]
            col.synapses.forEach { (bitIdx, perm) ->
                if (perm >= permanenceThreshold) {
                    bitVotes[bitIdx]++
                }
            }
        }

        // Pick the top bits (e.g., the 30 bits with the most votes)
        val reconstructedBits = bitVotes.indices
            .filter { bitVotes[it] > 0 }
            .sortedByDescending { bitVotes[it] }
            .take(Constants.SdrConstants.VISION_ACTIVE_BITS)
            .toIntArray()

        return SDR(size = numInputs, activeBits = reconstructedBits)
    }
}