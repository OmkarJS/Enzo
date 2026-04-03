package omkar.android.projects.domain.model.sensory

sealed interface SensoryInput

data class VisualInput(
    val brightness: Int,
    /*val motion: Int*/
): SensoryInput

data class AudioInput(
    val brightness: Int,
    val position: Int,
    val motion: Int
): SensoryInput

data class TouchInput(
    val brightness: Int,
    val position: Int,
    val motion: Int
): SensoryInput