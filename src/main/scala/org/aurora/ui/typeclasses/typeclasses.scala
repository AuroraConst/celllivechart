package org.aurora.model.ui
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.{Grid,GridCell,Coordinates}
import org.scalajs.dom.HTMLTableElement
import org.aurora.model.CalendarGrid
import scala.scalajs.js.Date
import org.aurora.model.calendardate.addDays

package object typeclasses :
  trait IdCoordinate[T] :
    extension (t:T)
      def id:String
      def id(x:Int,y:Int):String 
      def coordinate(id:String) : Coordinates

  given IdCoordinate[GridCell] with
    extension(g: GridCell) 
      def id:String  = s"${g.x},${g.y}"
      def id(x:Int,y:Int) = s"${x},${y}"

      def coordinate(id:String) :Coordinates = 
        val c = id.split(",")
        Coordinates(c(0).toInt,c(1).toInt)

      def coordinate() :Coordinates = 
        Coordinates(g.x,g.y) 
  
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

  given Showable[CalendarGrid] with
    extension(cg: CalendarGrid)
      def show(): String =
        val sb = new StringBuilder("")
        for 
          r <- 0 to cg.numWeeks-1
          c <- 0 to 6
        do 
          sb ++=  s"${(cg.grid.get(c,r)).show()}" 
          if(c == 6)
            sb ++= "\n"   
          else  
            sb ++= ","
        s"$sb"     


  //TODO trait bounded navigation typeclass
  trait BoundedNavigation[T] :
    extension (t:T)
      def withinBoundsX(x:Int):Boolean
      def withinBoundsY(y:Int):Boolean


  given BoundedNavigation[Grid] with
    extension(g: Grid) 
      def withinBoundsX(x:Int):Boolean = 
        x >= 0 && x < g.cols
      def withinBoundsY(y:Int):Boolean = 
        y >= 0 && y < g.rows



  //TODO trait conversion of T to (X:Int,Y:Int)    
  trait CoordinateDataConversion[T,U] :
    extension (t:T)
      def toCoordinate(d:U):Coordinates
      def toData(c:Coordinates):Option[U]

  given CoordinateDataConversion[CalendarGrid,Date] with
    extension(cg: CalendarGrid) 
      def toCoordinate(d:Date): Coordinates = 
        Coordinates(cg.x(d),cg.y(d))   
      def toData(c:Coordinates): Option[Date] = 
        if(cg.grid.withinBoundsX(c.x)  && cg.grid.withinBoundsY(c.y))
          Some( cg.firstMondayDate.addDays(c.y*7 + c.x))
           else 
          None
          
       
      def toData(x:Int, y:Int): Option[Date] = 
        toData(Coordinates(x,y))






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
        def row(y:Int) = g.xRange.map(x => td(g.get(x,y).inputElement  )) 
        def rows = g.yRange.map(y => tr(row(y)))

        table(
            thead(tr(th("Mon"), th("Tue"), th("Wed"), th("Thu"), th("Fri"), th("Sat"),th("Sun"))),
            tbody(rows*)
        )
