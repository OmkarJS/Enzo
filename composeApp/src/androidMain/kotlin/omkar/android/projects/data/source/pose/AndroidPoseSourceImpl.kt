package omkar.android.projects.data.source.pose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import co.touchlab.kermit.Logger
import omkar.android.projects.data.model.pose.Pose
import omkar.android.projects.domain.repository.pose.IPoseSource
import kotlin.math.sqrt

private const val TAG = "AndroidPoseSourceImpl"

class AndroidPoseSourceImpl(
    context: Context
): IPoseSource, SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private val quaternion = FloatArray(4)
    private var angularVelocity = FloatArray(3)

    private var _currentPose: Pose = Pose(
        0f,
        0f,
        0f,
        1f,
        0f,
        0f,
        0f,
        0f
    )
    override val currentPose: Pose get() = _currentPose

    override fun start() {
        Logger.withTag(TAG).d("Sensor listeners registered")
        rotationSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun stop() {
        Logger.withTag(TAG).d("Sensor listeners unregistered")
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                /** Quaternion [w, x, y, z] */
                SensorManager.getQuaternionFromVector(quaternion, event.values)

                /** Euler's angle for tilt and pitch, roll, yaw */
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                updatePose()
            }
            Sensor.TYPE_GYROSCOPE -> {
                /** Angular Velocity [rad/s] */
                angularVelocity[0] = event.values[0]
                angularVelocity[1] = event.values[1]
                angularVelocity[2] = event.values[2]

                updatePose()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun updatePose() {
        val pitch = orientationAngles[1]
        val roll = orientationAngles[2]

        _currentPose = Pose(
            quaternion[1],
            quaternion[2],
            quaternion[3],
            quaternion[0],
            angularVelocity[0],
            angularVelocity[1],
            angularVelocity[2],
            tilt = sqrt(pitch * pitch + roll * roll)
        )
    }
}