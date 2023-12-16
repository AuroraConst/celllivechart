package org.aurora.model.v2
import org.scalajs.dom
import scala.scalajs.js.Date
import collection.mutable.ListBuffer
import org.aurora.model.v2.utils.{Coordinate, LLBufferDimensionT, GridT, GridDataT}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom


given LLBufferDimensionT[Grid,GridData] with 
  extension(g:Grid)
    def dim = 
      def row = g.xRange
        .foldLeft ( ListBuffer[Option[GridData]]() ) { 
        (lb,_) => lb.addOne(None)
      }

       g.yRange
        .foldLeft(ListBuffer[ListBuffer[Option[GridData]]]()){
          (lb,y) => lb.addOne(row)
      }



case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows) :
  val focusedCoodinate  = Var[Option[Coordinate]](None)
  val focusedGridData = focusedCoodinate.signal.map{   optCoord =>
     val result = for{
        c <- optCoord
        gd <- data(c)
      } yield(gd.s)

      result.getOrElse("--")  
  }
  def populate(d:List[Date]):Unit  =
    val iterator = d.toIterator
    linearizedleftRightCoordinates.foreach { c =>   
        val data = iterator.nextOption()
        data.foreach {
          d => update(c,GridData(this,c.x,c.y,d.toDateString()) )
        }
    }

  lazy val grid = this.dim
    
    



case class GridData(g:Grid,x:Int,y:Int,s:String) extends  GridDataT[Grid,String](g,s) :
  import  org.aurora.model.v2.utils.EditorToggleState.* 
  import org.aurora.model.v2.utils.given

  lazy val inputHtmlElement = this.htmlElement
  val toggleState = Var(UnSelected)

  val cellText = Var(s)
  def coordinate: Coordinate = 
    Coordinate(x,y)