package org.aurora.brainstorm.utils

import scala.collection.mutable.ListBuffer

trait GridT[D](cols:Int,rows:Int) :
  val grid:ListBuffer[ListBuffer[D]]
  lazy val xRange = (0 to cols-1)
  lazy val yRange = (0 to rows-1)
  def coordinate(data:D):Coordinate
  def data(c:Coordinate):D

trait GridDataT[G,D](grid:G,data:D) :
  def coordinate:Coordinate

