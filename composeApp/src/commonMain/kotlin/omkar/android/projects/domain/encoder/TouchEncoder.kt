package omkar.android.projects.domain.encoder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import omkar.android.projects.app.constants.Constants
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.touch.TouchInput
import omkar.android.projects.domain.repository.encoders.ITouchEncoder

class TouchEncoder: ITouchEncoder {

/*
        [Taking X as the example]

        1) Normalise the X value
        normalisedX = X / maxX (Every value normalised will be inside the maxX range

        2) Calculate the start boundary (Farthest)
        farthest start point = 1024 (Half of full sdr size) - 16 (Half of active bits) [Since 32 bits is total active bits in 2048 sized SDR]
        StartX = normalisedX * farthest start point

        3) Turn on 16 bits starting from the index StartX in the SDR
*/
    override fun encode(input: TouchInput): Flow<SDR?> = flow {
        val activeBits = IntArray(32)

        // X
        val normalizedX = input.x / input.maxX
        val maxStartX = (Constants.SdrConstants.SDR_SIZE / 2) - (Constants.SdrConstants.ACTIVE_BITS / 2)
        val startX = (maxStartX * normalizedX).toInt()

        // Y
        val normalizedY = input.y / input.maxY
        val maxStartY = (Constants.SdrConstants.SDR_SIZE / 2) - (Constants.SdrConstants.ACTIVE_BITS / 2)
        val relativeStartY = maxStartY * normalizedY
        val startY = ((Constants.SdrConstants.SDR_SIZE / 2) + relativeStartY).toInt()

        for (i in 0 until (Constants.SdrConstants.ACTIVE_BITS / 2)) {
            activeBits[i] = startX + i
            activeBits[(Constants.SdrConstants.ACTIVE_BITS / 2) + i] = startY + i
        }

        emit(
            SDR(
                activeBits = activeBits
            )
        )
    }.flowOn(Dispatchers.Default)
}