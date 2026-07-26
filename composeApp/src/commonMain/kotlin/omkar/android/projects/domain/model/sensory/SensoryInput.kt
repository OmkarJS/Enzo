package omkar.android.projects.domain.model.sensory

import omkar.android.projects.data.model.pose.Pose

sealed interface SensoryInput

data class VisualInput(
    val brightness: Int,
    val pose: Pose
): SensoryInput

data class AudioInput(
    val brightness: Int,
    val position: Int,
    val motion: Int
): SensoryInput