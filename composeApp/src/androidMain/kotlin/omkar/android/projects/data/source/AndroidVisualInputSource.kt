package omkar.android.projects.data.source

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import omkar.android.projects.domain.model.sensory.VisualInput
import omkar.android.projects.domain.repository.inputsource.VisualInputSource
import omkar.android.projects.domain.repository.pose.IPoseSource
import java.util.concurrent.Executors

private const val TAG = "CameraDataSource"

actual class AndroidVisualInputSource (
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val previewView: PreviewView,
    private val poseSource: IPoseSource
): VisualInputSource {

    private val _visualInputs = MutableSharedFlow<VisualInput>(extraBufferCapacity = 1)
    actual override val visualInput: Flow<VisualInput>
        get() = _visualInputs.asSharedFlow()

    private val cameraExecutor = Executors.newSingleThreadExecutor()

    /**
    - ImageProxy contains data in YUV format (Y has brightness info)
     */
    private fun calculateBrightness(image: ImageProxy): Int {
        val yPlane = image.planes[0]
        val buffer = yPlane.buffer

        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        var sum = 0L

        for (byte in data) {
            sum += byte.toInt() and 0xFF
        }

        return (sum / data.size).toInt()
    }

    fun startAnalyser(): ImageAnalysis {
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(
                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
            )
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            val brightness = calculateBrightness(imageProxy)

            val visualInput = VisualInput(
                brightness = brightness,
                pose = poseSource.currentPose
            )

            Logger.withTag(TAG).d("startAnalyser: Visual Input - $visualInput")
            _visualInputs.tryEmit(visualInput)

            imageProxy.close()
        }

        return imageAnalysis
    }

    actual override fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        poseSource.start()

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val imageAnalysis = startAnalyser()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )

        }, ContextCompat.getMainExecutor(context))
    }

    actual override fun stopCamera() {
        poseSource.stop()
        ProcessCameraProvider.getInstance(context).get().unbindAll()
    }
}