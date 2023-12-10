package org.aurora.model.ui
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.{Grid,GridCell}
import org.scalajs.dom.HTMLTableElement

package object typeclasses :
  
  trait Showable[T]:
    extension (t:T) def show():String

  given Showable[GridCell] with
    extension(g: GridCell) 
      def show(): String = s"(${g.x},${g.y})"

  given Showable[Grid] with
    extension(g: Grid)
      def show(): String =
        val sb = new StringBuilder("")
        for 
          r <- 0 to g.rows-1
          c <- 0 to g.cols-1
        do 
          sb ++=  g.get(c,r).show() 
          if(c == g.cols -1)
            sb ++= "\n"   
          else  
            sb ++= ","
        s"$sb"



  trait HtmlAble[T]:
    extension (t:T) def htmlElement():HtmlElement

  import org.aurora.model.ui.*

  given HtmlAble[GridCell] with
    extension(g: GridCell) 
      def htmlElement(): HtmlElement = 
        g.inputElement


  given HtmlAble[Grid] with
    extension(g: Grid) 
      def htmlElement(): HtmlElement = 
        val row = g.xRange.map(x => td(g.get(x,0).inputElement  ))
        table(
            thead(tr(th("Mon"), th("Tue"), th("Wed"), th("Thu"), th("Fri"), th("Sat"),th("Sun"))),
            tbody(tr(row*))
        )
