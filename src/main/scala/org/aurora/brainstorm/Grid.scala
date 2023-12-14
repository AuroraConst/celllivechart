package org.aurora.brainstorm
import org.scalajs.dom
import scala.scalajs.js.Date
import org.aurora.brainstorm.utils._
import scala.collection.mutable.ListBuffer


case class Grid(cols:Int,rows:Int) extends GridT[GridData](cols,rows) :
  val grid  = ListBuffer(ListBuffer(GridData(this,"")))
  def coordinate(data:GridData):Coordinate = ???
  def data(c:Coordinate):GridData = ???

case class GridData(g:Grid,s:String) extends  GridDataT[Grid,String](g,s) :
  def coordinate: Coordinate = ???
