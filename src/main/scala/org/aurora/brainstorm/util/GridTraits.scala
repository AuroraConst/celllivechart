package org.aurora.brainstorm.util

trait Grid[D] :
  def coordinate(data:D):Coordinate
  def data(c:Coordinate):D

trait GridData[G <: Grid[D]] :
  val grid:G
  def coordinate:Coordinate

