package org.aurora.model.v2.utils

import org.aurora.model.v2.{Grid,GridData}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

trait HtmlAble[T]:
  extension (t:T) 
    def htmlElement:HtmlElement

import org.aurora.model.ui.*

given HtmlAble[GridData] with
  extension(g: GridData) 
    def htmlElement: HtmlElement = 
      //TODO FINIDHg.inputElement
      null


given HtmlAble[Grid] with
  extension(g: Grid) 
    def htmlElement: HtmlElement = 
      def row(y:Int) = g.xRange.map(x => td(g.data(x,y).map( x => x.htmlElement  ).getOrElse(div("error")))) 
      def rows = g.yRange.map(y => tr(row(y)))

      table(
          thead(tr(th("Mon"), th("Tue"), th("Wed"), th("Thu"), th("Fri"), th("Sat"),th("Sun"))),
          tbody(rows*)
      )

// given HtmlAble[CalendarGrid] with
//   extension(cg:CalendarGrid)
//     def htmlElement(): HtmlElement =
//     cg.grid.htmlElement()