package omkar.android.projects.presentation.bitforge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val TAG = "BitForgeSdrUi"

@Preview
@Composable
fun ColumnScope.BitForgeSdrUi(
    activeBits: IntArray? = null,
    noOfRows: Int = 64,
    noOfColumns: Int = 32
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .border(1.dp, color = Color.Black)
    ) {
        val gridWidth = size.width / noOfColumns
        val gridHeight = size.height / noOfRows
        val borderColor = Color.Black

        // Rows lines
        for(i in 1 until noOfRows) {
            drawLine(
                color = borderColor,
                start = Offset(x = 0f, y = i * gridHeight),
                end = Offset(x = size.width, y = i * gridHeight),
                strokeWidth = 1f
            )
        }
        
        // Column columns
        for(i in 1 until noOfColumns) {
            drawLine(
                color = borderColor,
                start = Offset(x = i * gridWidth, y = 0f),
                end = Offset(x = i * gridWidth, y = size.height),
            )
        }

        Logger.withTag(TAG).d("Active indexes: ${activeBits?.joinToString()}")

        // Active grids
        activeBits?.let {
            for(index in activeBits) {
                val row = index / noOfColumns
                val column = index % noOfColumns
                // Logger.withTag(TAG).d("Grid row: $row, Grid column: $column")

                drawRect(
                    color = Color.Green,
                    topLeft = Offset(
                        x = column * gridWidth,
                        y = row * gridHeight
                    ),
                    size = Size(gridWidth, gridHeight),
                    style = Fill
                )
            }
        }
    }
}