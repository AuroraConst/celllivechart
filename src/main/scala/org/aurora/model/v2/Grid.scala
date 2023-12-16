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
  lazy val grid = this.dim
  val focusedCoodinate  = Var[Option[Coordinate]](None)
  val focusedGridData = focusedCoodinate.signal.map{   optCoord =>
     val result = for{
        c <- optCoord
        gd <- data(c)
      } yield(gd.varData.now())
      result
  }

  /**
    * populate grid based on a List[Date]
    */
  def populate(d:List[Date]):Unit  =
    val iteratorCoordinates = d.toIterator
    linearizedleftRightCoordinates.foreach { c =>   
      val optDate = iteratorCoordinates.nextOption()
      val optData =for {  date <- optDate  } yield (GridData(this,c.x,c.y,Data(date,date.toDateString())))
      update(c,optData)
    } 


case class Data(date:Date,s:String)

case class GridData(g:Grid,x:Int,y:Int,d:Data) extends  GridDataT[Grid,Data](g,d) :
  import  org.aurora.model.v2.utils.EditorToggleState.* 
  import org.aurora.model.v2.utils.given

  lazy val inputHtmlElement = this.htmlElement
  val toggleState = Var(UnSelected)
  val varData = Var(d)
  val varDataWriter = varData.updater[String]((data,b) => data.copy(s = b))

  def coordinate: Coordinate = 
    Coordinate(x,y)