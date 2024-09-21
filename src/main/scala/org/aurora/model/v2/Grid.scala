package org.aurora.model.v2
import org.scalajs.dom
import collection.mutable.ListBuffer
import org.aurora.model.v2.utils.{Coordinate, LLBufferDimensionT, GridT, GridDataT}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import java.time.LocalDateTime


case class Data(date:LocalDateTime,s:String)
case class Header(header:String) :
  val selected = Var(false)

given LLBufferDimensionT[GridData] with 
  def emptyRow = ListBuffer[Option[GridData]]() 
  def emptyBuffer = ListBuffer[ListBuffer[Option[GridData]]]() 

  extension(g:Grid) 
    def initLLBuffer:ListBuffer[ListBuffer[Option[GridData]]] =
      dim(g.cols,g.rows)


      
case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows):
  lazy val grid =  this.initLLBuffer
  val headers = List("MON","TUES","WED","THU","FRI","SAT","SUN")
    .map(h => Header(h))

  val summaryText = Var("") 
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
  def populate(d:List[LocalDateTime]):Unit  =
    import org.aurora.model.v2.utils.toDateTimeString
    val iteratorCoordinates = d.toIterator
    linearizedleftRightCoordinates.foreach { c =>   
      val data = for {
        date <- iteratorCoordinates.nextOption()
      } yield  (GridData(this,c.x,c.y,Data(date,date.toDateTimeString)))
      update(c,data)
    } 




case class GridData(g:Grid,x:Int,y:Int,d:Data) extends  GridDataT[Grid,Data](g,d) :
  import  org.aurora.model.v2.utils.EditorToggleState.* 
  import org.aurora.model.v2.utils.given

  lazy val inputHtmlElement = this.htmlElement
  val toggleState = Var(UnSelected)
  val varData = Var[Data](d) 
  val varDataWriter = varData.updater[String]((data,b) => data.copy(s = b))

  def coordinate: Coordinate = 
    Coordinate(x,y)