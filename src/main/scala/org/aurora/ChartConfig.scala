package org.aurora
import scala.scalajs.js
import scala.scalajs.js.annotation.*
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object ChartConfig {
  val chartConfig =
    import typings.chartJs.mod.*
    new ChartConfiguration {
      `type` = ChartType.bar
      data = new ChartData {
        datasets = js.Array(
          new ChartDataSets {
            label = "Price"
            borderWidth = 1
            backgroundColor = "green"
          },
          new ChartDataSets {
            label = "Full price"
            borderWidth = 1
            backgroundColor = "blue"
          }
        )
      }
      options = new ChartOptions {
        scales = new ChartScales {
          yAxes = js.Array(new CommonAxe {
            ticks = new TickOptions {
              beginAtZero = true
            }
          })
        }
      }
    }
  end chartConfig

  def renderDataChart(): Element =
    import scala.scalajs.js.JSConverters.*
    import typings.chartJs.mod.*
    import Main.model.*

    var optChart: Option[Chart] = None

    canvasTag(
      // Regular properties of the canvas
      width := "100%",
      height := "200px",

      // onMountUnmount callback to bridge the Laminar world and the Chart.js world
      onMountUnmountCallback(
        // on mount, create the `Chart` instance and store it in optChart
        mount = { nodeCtx =>
          val domCanvas: dom.HTMLCanvasElement = nodeCtx.thisNode.ref
          val chart = Chart.apply.newInstance2(domCanvas, chartConfig)
          optChart = Some(chart)
        },
        // on unmount, destroy the `Chart` instance
        unmount = { thisNode =>
          for (chart <- optChart)
            chart.destroy()
          optChart = None
        }
      ),

      // Bridge the FRP world of dataSignal to the imperative world of the `chart.data`
      dataSignal --> { data =>
        for (chart <- optChart) {
          chart.data.labels = data.map(_.label).toJSArray
          chart.data.datasets.get(0).data = data.map(_.price).toJSArray
          chart.data.datasets.get(1).data = data.map(_.fullPrice).toJSArray
          chart.update()
        }
      },
    )
  end renderDataChart  
}