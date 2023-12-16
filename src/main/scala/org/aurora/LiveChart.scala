package org.aurora

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import typings.std.stdStrings.text
import scala.scalajs.js.Date
import org.aurora.model.v2.{*,given}


@main
def LiveChart(): Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    Main.appElement()
  )
end LiveChart

object Main:
  val model = new Model

  import org.aurora.model.v2.Grid
  import org.aurora.model.v2.utils.{*,given}

  def appElement(): Element =
    import org.aurora.model.given
    val g = Grid(7,11)
    var firstDate = CalendarGrid(new Date(),7).firstMondayDate.toMidnight
    val dateList = g.leftRightFlatCollection
      .map{_ =>
        val olddate = new Date(firstDate.getTime())
        firstDate.setDate(firstDate.getDate()+1)
        olddate
      }
      .toList
 
     g.populate(dateList) 

      
    div(
      h1("Chart", img(src:= "/vite.svg")),
      div(child.text <-- g.focusedCoodinate.signal
        .map{ optCoord =>
          val data = for{
            c <- optCoord;
            data <- g.data(c)
          } yield (data)

          data.map(_.s).getOrElse("error")
          }
      ),
      g.htmlElement,
    )
  end appElement






  def inputForString(valueSignal: Signal[String],
      valueUpdater: Observer[String]): Input =
    input(
      typ := "text",
      value <-- valueSignal,
      onInput.mapToValue --> valueUpdater,
    )
  end inputForString

  def inputForDouble(valueSignal: Signal[Double],
      valueUpdater: Observer[Double]): Input =
    val strValue = Var[String]("")
    input(
      typ := "text",
      value <-- strValue.signal,
      onInput.mapToValue --> strValue,
      valueSignal --> strValue.updater[Double] { (prevStr, newValue) =>
        if prevStr.toDoubleOption.contains(newValue) then prevStr
        else newValue.toString
      },
      strValue.signal --> { valueStr =>
        valueStr.toDoubleOption.foreach(valueUpdater.onNext)
      },
    )
  end inputForDouble

  def inputForInt(valueSignal: Signal[Int],
      valueUpdater: Observer[Int]): Input =
    input(
      typ := "text",
      controlled(
        value <-- valueSignal.map(_.toString),
        onInput.mapToValue.map(_.toIntOption).collect {
          case Some(newCount) => newCount
        } --> valueUpdater,
      ),
    )
  end inputForInt


end Main
