package org.aurora.brainstorm
import org.scalajs.dom
import scala.scalajs.js.Date
import org.aurora.brainstorm.utils._
import scala.collection.mutable.ListBuffer


case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows) :
  lazy val grid = 
    val lb = ListBuffer(ListBuffer[Option[GridData]]())
    
    //note that after above construction, there is already one ListBuffer[?]()
    //in the data structure
    yRange.drop(1).foreach ( y =>
      lb.addOne(ListBuffer[Option[GridData]]())
    )
    lb.foreach {
      r => 
        xRange.foreach{x =>
          r.addOne(None)
        }
    }
    
    lb
  end grid    
    
   
  def coordinate(data:GridData):Coordinate =
    data.coordinate
  def data(c:Coordinate):Option[GridData] = 
    if(xRange.contains(c.x) && yRange.contains(c.y))
      grid(c.y)(c.x)
      else
      None  
    

case class GridData(g:Grid,x:Int,y:Int,s:String) extends  GridDataT[Grid,String](g,s) :
  def coordinate: Coordinate = 
    Coordinate(x,y)