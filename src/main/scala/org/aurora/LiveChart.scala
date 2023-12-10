package org.aurora

import scala.scalajs.js
import scala.scalajs.js.annotation.*

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

@main
def LiveChart(): Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    Main.appElement()
  )
end LiveChart

object Main:
  val model = new Model
  import model.*

  import org.aurora.model.*
  import org.aurora.model.ui.typeclasses.given

  def appElement(): Element =
    div(
       h1("Chart", img(src:= "/vite.svg")),
       Grid(7,2).htmlElement(),
      renderDataTable(),
      // renderDataList(),
    )
  end appElement

  // def renderDataList(): Element =
  //   ul(
  //     children <-- dataSignal.split(_.id) { (id, initial, itemSignal) =>
  //       li(child.text <-- itemSignal.map(item => s"${item.count} ${item.label}"))
  //     }
  //   )
  // end renderDataList


  //TODO customize spacing of editors using css
  def renderDataTable(): Element =
    table(
      thead(tr(th("Label"), th("Price"), th("Count"), th("Full price"), th("Action"))),
      tbody(
        children <-- dataSignal.split(_.id) { (id, initial, itemSignal) =>
          renderDataItem(id, itemSignal)
        },
      ),
      tfoot(tr(
        td(button("➕", onClick --> (_ => addDataItem(DataItem())))),
        td(),
        td(),
        td(child.text <-- dataSignal.map(data => "%.2f".format(data.map(_.fullPrice).sum))),
      )),
    )
  end renderDataTable

  def renderDataItem(id: DataItemID, itemSignal: Signal[DataItem]): Element =
    import org.aurora.model.ui.*
    tr(
      td(
        cellTextInput
      ),
      td(
        inputForDouble(
          itemSignal.map(_.price),
          makeDataItemUpdater(id, { (item, newPrice) =>
            item.copy(price = newPrice)
          })
        )
      ),
      td(
        inputForInt(
          itemSignal.map(_.count),
          makeDataItemUpdater(id, { (item, newCount) =>
            item.copy(count = newCount)
          })
        )
      ),
      td(
        child.text <-- itemSignal.map(item => "%.2f".format(item.fullPrice))
      ),
      td(button("🗑️", onClick --> (_ => removeDataItem(id)))),
    )
  end renderDataItem

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
