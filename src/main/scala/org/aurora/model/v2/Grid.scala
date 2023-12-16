package org.aurora.model.v2
import org.scalajs.dom
import scala.scalajs.js.Date
import collection.mutable.ListBuffer
import org.aurora.model.v2.utils.{Coordinate, LLBufferDimensionT, GridT, GridDataT}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom


given LLBufferDimensionT[GridData] with 
  def emptyRow = ListBuffer[Option[GridData]]() 
  def emptyBuffer = ListBuffer[ListBuffer[Option[GridData]]]() 

  extension(g:Grid) 
    def initLLBuffer:ListBuffer[ListBuffer[Option[GridData]]] =
      dim(g.cols,g.rows)


      
case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows):
  lazy val grid =  this.initLLBuffer
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
      val data = for {
        date <- iteratorCoordinates.nextOption();
      } yield  (GridData(this,c.x,c.y,Data(date,date.toDateString())))
      update(c,data)
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