package org.aurora.brainstorm.utils

import scala.collection.mutable.ListBuffer

trait GridT[D](cols:Int,rows:Int) :
  lazy val grid:ListBuffer[ListBuffer[Option[D]]]
  val xRange = (0 until cols)
  val yRange = (0 until rows)
  val leftRightFlatCollection = for(
    y <- yRange;
    x <- xRange
    ) yield(Coordinate(x,y))
  def coordinate(data:D):Coordinate
  def data(c:Coordinate):Option[D]
  def update(c:Coordinate, data:D):Unit
  

trait GridDataT[G,D](grid:G,data:D) :
  def coordinate:Coordinate



import scala.collection.mutable.ListBuffer  
trait LBufferInitializerT[T,D] :
  extension (t:T) 
    def initLLBuffer:ListBuffer[ListBuffer[Option[D]]] 

    




