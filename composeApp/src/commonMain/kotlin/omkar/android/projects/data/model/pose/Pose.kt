package omkar.android.projects.data.model.pose

data class Pose(
    val quaternionX: Float,
    val quaternionY: Float,
    val quaternionZ: Float,
    val quaternionValue: Float,

    val angularVelocityX: Float,
    val angularVelocityY: Float,
    val angularVelocityZ: Float,

    val tilt: Float

    // Maybe add pitch, yaw and roll in the future
)
