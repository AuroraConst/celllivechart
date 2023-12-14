package org.aurora.brainstorm.utils

import scala.collection.mutable.ListBuffer

trait GridT[D](cols:Int,rows:Int) :
  lazy val grid:ListBuffer[ListBuffer[Option[D]]]
  val xRange = (0 until cols)
  val yRange = (0 until rows)
  def coordinate(data:D):Coordinate
  def data(c:Coordinate):Option[D]

trait GridDataT[G,D](grid:G,data:D) :
  def coordinate:Coordinate

