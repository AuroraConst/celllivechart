package org.aurora.model.v2.utils

import scala.collection.mutable.ListBuffer
import org.scalajs.dom.Coordinates

trait GridT[D](cols:Int,rows:Int) :
  lazy val grid:ListBuffer[ListBuffer[Option[D]]]
  val xRange = (0 until cols)
  val yRange = (0 until rows)
  val leftRightFlatCollection = for(
    y <- yRange;
    x <- xRange
    ) yield(Coordinate(x,y))
  
  def inBounds(c:Coordinate): Boolean = 
    xRange.contains(c.x) && yRange.contains(c.y)

  def update(c:Coordinate, data:D):Unit =
    if(inBounds(c))   grid(c.y)(c.x) = Some(data)

  def data(c:Coordinate):Option[D] = 
    if(inBounds(c))
      grid(c.y)(c.x)   else   None
  
  def data(x:Int,y:Int):Option[D] = 
    data(Coordinate(x,y))


end GridT
  

trait GridDataT[G,D](grid:G,data:D) :
  def coordinate:Coordinate



import scala.collection.mutable.ListBuffer  
trait LBufferInitializerT[T,D] :
  extension (t:T) 
    def initLLBuffer:ListBuffer[ListBuffer[Option[D]]] 

    




