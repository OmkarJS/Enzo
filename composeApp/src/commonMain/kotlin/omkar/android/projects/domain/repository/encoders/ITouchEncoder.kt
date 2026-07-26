package omkar.android.projects.domain.repository.encoders

import kotlinx.coroutines.flow.Flow
import omkar.android.projects.domain.model.SDR
import omkar.android.projects.domain.model.touch.TouchInput

interface ITouchEncoder {
    fun encode(input: TouchInput): Flow<SDR?>
}