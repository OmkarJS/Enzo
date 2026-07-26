package omkar.android.projects.domain.usecases

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.touch.TouchInput
import omkar.android.projects.domain.repository.encoders.ITouchEncoder

private const val TAG = "BitForgeUseCase"

class BitForgeUseCase(
    private val touchEncoder: ITouchEncoder
) {
    suspend operator fun invoke(input: TouchInput): Flow<SDR?> {
        // SDR conversion
        return touchEncoder.encode(input)
    }
}