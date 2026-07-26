package omkar.android.projects.presentation.bitforge

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import omkar.android.projects.app.components.ExtraSmallSpacer
import omkar.android.projects.app.theme.LocalAppColors
import omkar.android.projects.domain.model.touch.TouchInput
import org.koin.compose.koinInject

private const val TAG = "BitforgePage"

@Composable
fun BitForgePage(
    onBackPressed: () -> Unit
) {
    val colors = LocalAppColors.current
    val bitForgeViewmodel: BitForgeViewmodel = koinInject()

    // States
    val bitForgeState by bitForgeViewmodel.bitForgeValue.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.primaryVariant.copy(alpha = 0.05f))
                .pointerInput(Unit) {
                    val boundsWidth = size.width.toFloat()
                    val boundsHeight = size.height.toFloat()

                    /*detectTapGestures(
                        onTap = { offset ->
                            // Logger.withTag(TAG).d("onTap: X - ${offset.x}, Y - ${offset.y}")
                            bitForgeViewmodel.touchInput(
                                TouchInput(
                                    x = offset.x,
                                    y = offset.y,
                                    maxX = boundsWidth,
                                    maxY = boundsHeight
                                )
                            )
                        }
                    )*/

                    detectDragGestures(
                        onDragStart = { offset ->
                            // Logger.withTag(TAG).d("onDragStart: X - ${offset.x}, Y - ${offset.y}")
                        },
                        onDrag = { change, dragAmount ->
                            Logger.withTag(TAG).d("onDrag: X - ${change.position.x}, Y - ${change.position.y}, dragAmount - $dragAmount")
                            bitForgeViewmodel.touchInput(
                                TouchInput(
                                    x = change.position.x,
                                    y = change.position.y,
                                    maxX = boundsWidth,
                                    maxY = boundsHeight
                                )
                            )
                        }
                    )
                }
        ) {}

        ExtraSmallSpacer()

        BitForgeSdrUi(
            activeBits = bitForgeState?.activeBits
        )
    }
}