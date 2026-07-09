package omkar.android.projects.domain.repository.spatialpooler

import omkar.android.projects.domain.model.SDR

interface ISpatialPooler {
    fun compute(
        inputSdr: SDR,
        learn: Boolean = true
    ): SDR

    fun decode(
        predictedSdr: SDR
    ): SDR
}