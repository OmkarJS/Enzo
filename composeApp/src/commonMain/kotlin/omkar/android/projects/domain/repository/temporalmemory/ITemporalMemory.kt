package omkar.android.projects.domain.repository.temporalmemory

import omkar.android.projects.domain.model.SDR

interface ITemporalMemory {
    fun compute(
        inputSDR: SDR,
        learn: Boolean = true
    ): SDR
}