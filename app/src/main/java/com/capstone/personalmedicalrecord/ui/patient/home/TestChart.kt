package com.capstone.personalmedicalrecord.ui.patient.home

import android.content.Context
import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.constant
import io.data2viz.charts.chart.discrete
import io.data2viz.charts.chart.mark.line
import io.data2viz.charts.chart.quantitative
import io.data2viz.charts.config.configs.lightConfig
import io.data2viz.charts.core.CursorType
import io.data2viz.charts.core.PanMode
import io.data2viz.charts.core.TriggerMode
import io.data2viz.charts.core.ZoomMode
import io.data2viz.color.Colors
import io.data2viz.geom.Size
import io.data2viz.viz.VizContainerView

data class TestData(
    val country: String, val corona: Int,
)

class TestChart(context: Context) : VizContainerView(context) {
    companion object {
        const val vizSize = 500.0
    }

    private val listData = arrayListOf(
        TestData("Indonesia", 4210200),
        TestData("Malaysia", 6320400),
        TestData("USA", 5530050),
        TestData("Singapore", 4010250),
        TestData("South Korea", 5467345),
        TestData("Indones", 3291280),
        TestData("Malsia", 6304200),
        TestData("UA", 1503050),
        TestData("Singe", 700250),
        TestData("Sotrea", 5043345),
    )

    private val chart = chart(listData, lightConfig) {
        size = Size(vizSize, vizSize)
        title = "Corona Case in 2021"
        setPadding(16, 16, 16, 16)

        config {
            cursor {
                show = true
                type = CursorType.Crosshair
            }
            events {
                triggerMode = TriggerMode.Column
                zoomMode = ZoomMode.X
                panMode = PanMode.X
            }
        }

        val country = discrete({ domain.country })
        val corona = quantitative({ domain.corona.toDouble() })

        line(country, corona) {
            strokeColor = discrete({ Colors.Web.black })

            strokeWidth = constant(3.0)

            // Set some limits, to disable zoom out (or pan out) out of these values
            x {
                min = listData.first().corona.toString()
                max = listData.last().corona.toString()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chart.size = Size(vizSize, vizSize * h / w)
    }
}