package omkar.android.projects.data.repository.temporalmemory

import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.repository.temporalmemory.ITemporalMemory

class TemporalMemoryImpl(
    private val numColumns: Int = 2048,
    private val cellsPerColumn: Int = 4,
    private val activationThreshold: Int = 6, // Min synapses to trigger prediction
    private val learningThreshold: Int = 4,
    private val initialPermanence: Float = 0.21f,
    private val permanenceIncrement: Float = 0.02f,
    private val permanenceDecrement: Float = 0.005f
): ITemporalMemory {
    // State storage: BitSet or BooleanArray for performance
    private var activeCells = mutableSetOf<Int>()      // Global cell indices (0 to 8191)
    private var predictiveCells = mutableSetOf<Int>()
    
    // Connections: CellIndex -> List of Segments
    // Each Segment is a list of Synapses to OTHER cells
    private val segments = mutableMapOf<Int, MutableList<DistalSegment>>()

    data class DistalSegment(
        val synapses: MutableMap<Int, Float> = mutableMapOf() // TargetCellIndex -> Permanence
    )

    override fun compute(
        inputSDR: SDR,
        learn: Boolean
    ): SDR {
        val nextActiveCells = mutableSetOf<Int>()
        val nextPredictiveCells = mutableSetOf<Int>()
        val activeColumnIndices = inputSDR.activeBits

        // 1. Determine Active Cells
        for (colIndex in activeColumnIndices) {
            var predicted = false
            for (cellInCol in 0 until cellsPerColumn) {
                val cellIndex = colIndex * cellsPerColumn + cellInCol
                if (predictiveCells.contains(cellIndex)) {
                    nextActiveCells.add(cellIndex)
                    predicted = true
                    // Learning: Strengthen the segment that caused this prediction
                    if (learn) reinforceCorrectPrediction(cellIndex)
                }
            }

            // Bursting: No cell predicted this active column
            if (!predicted) {
                for (cellInCol in 0 until cellsPerColumn) {
                    nextActiveCells.add(colIndex * cellsPerColumn + cellInCol)
                }
                if (learn) learnNewSequence(colIndex)
            }
        }

        // 2. Predict next state based on nextActiveCells
        predictNextState(nextActiveCells, nextPredictiveCells)

        activeCells = nextActiveCells
        predictiveCells = nextPredictiveCells

        // Return an SDR of the predictive columns (this is your "Prediction")
        val predictedColumns = predictiveCells.map { it / cellsPerColumn }.distinct().toIntArray()
        return SDR(size = numColumns, activeBits = predictedColumns)
    }

    private fun predictNextState(currentActive: Set<Int>, nextPredictive: MutableSet<Int>) {
        segments.forEach { (cellIndex, segmentList) ->
            for (segment in segmentList) {
                val activeSynapses = segment.synapses.count { (target, perm) ->
                    perm > 0.1f && currentActive.contains(target)
                }
                if (activeSynapses >= activationThreshold) {
                    nextPredictive.add(cellIndex)
                    break
                }
            }
        }
    }

    private fun learnNewSequence(colIndex: Int) {
        // Pick a cell in the bursting column to represent this new sequence
        // Simplified: pick cell 0 and create a segment to the PREVIOUSLY active cells
        val cellIndex = colIndex * cellsPerColumn + 0 
        val segmentList = segments.getOrPut(cellIndex) { mutableListOf() }
        val newSegment = DistalSegment()

        // Connect to a subset of the previously active cells\\
        activeCells.shuffled().take(20).forEach { prevCell ->
            newSegment.synapses[prevCell] = initialPermanence
        }
        segmentList.add(newSegment)
    }

    private fun reinforceCorrectPrediction(cellIndex: Int) {
        val segmentList = segments[cellIndex] ?: return

        for (segment in segmentList) {
            // Check if this segment was "active" in the previous step
            // (i.e., did it have enough active synapses to cause a prediction?)
            val activeSynapses = segment.synapses.filter { (target, perm) ->
                perm >= 0.1f && activeCells.contains(target)
            }

            if (activeSynapses.size >= activationThreshold) {
                // HEBBIAN LEARNING:
                // 1. Increase permanence for synapses that were active (they were right)
                // 2. Decrease permanence for synapses that were inactive (they were dead weight)

                val synapseIterator = segment.synapses.entries.iterator()
                while (synapseIterator.hasNext()) {
                    val entry = synapseIterator.next()
                    val targetCellIndex = entry.key
                    val currentPerm = entry.value

                    if (activeCells.contains(targetCellIndex)) {
                        // This synapse contributed to a correct prediction
                        entry.setValue((currentPerm + permanenceIncrement).coerceAtMost(1.0f))
                    } else {
                        // This synapse was inactive; it didn't help
                        val newPerm = (currentPerm - permanenceDecrement).coerceAtLeast(0.0f)
                        entry.setValue(newPerm)

                        // Optional: Prune dead synapses to keep the map small
                        if (newPerm <= 0.0f) {
                            synapseIterator.remove()
                        }
                    }
                }
            }
        }
    }
}