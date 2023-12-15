package org.aurora.model.v2
import org.scalajs.dom
import scala.scalajs.js.Date
import scala.collection.mutable.ListBuffer
import org.aurora.model.v2.utils.{Coordinate, LBufferInitializerT, GridT, GridDataT}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom


given LBufferInitializerT[Grid,GridData] with
  extension(g:Grid)
    def initLLBuffer = 
      def row = g.xRange
        .foldLeft ( ListBuffer[Option[GridData]]() ) {
        (lb,_) => lb.addOne(None)
      }

       g.yRange
        .foldLeft(ListBuffer[ListBuffer[Option[GridData]]]()){
          (lb,y) => lb.addOne(row)
      }

    def populate(s:List[Int]):Unit  =
      val iterator = s.toIterator
      g.leftRightFlatCollection.foreach{ 
        c =>   
          val data = iterator.nextOption()
          data.foreach {
            d => g.update(c,GridData(g,  c.x,c.y,d.toString()) )
          }
      }


case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows) :
  val focusedCoodinate  = Var("xxxxx")

  def emptyRow:ListBuffer[Option[GridData]] =
    ListBuffer[Option[GridData]]()
  lazy val grid = this.initLLBuffer
    
    

case class GridData(g:Grid,x:Int,y:Int,s:String) extends  GridDataT[Grid,String](g,s) :
  val data = Var("initial")
  def coordinate: Coordinate = 
    Coordinate(x,y)