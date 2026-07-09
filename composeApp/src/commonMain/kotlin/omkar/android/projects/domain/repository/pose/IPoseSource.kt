package omkar.android.projects.domain.repository.pose

import omkar.android.projects.data.model.pose.Pose

interface IPoseSource {
    val currentPose: Pose
    fun start()
    fun stop()
}