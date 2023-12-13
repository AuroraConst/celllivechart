package org.aurora.brainstorm
import org.scalajs.dom

case class Coordinate(x:Int,y:Int)

trait GridTrait[D](cols:Int,rows:Int) {
  lazy val gridarray:Array[Array[GridDataTrait[D]]]
  lazy val xRange = (0 to cols-1)
  lazy val yRange = (0 to rows-1)

  def get(x:Int,y:Int):GridDataTrait[D] = gridarray(y)(x)
  def coordinate(data:D) :Coordinate 
}


trait GridDataTrait[D](g: GridTrait[D],coordinate:Coordinate,data: D)


case class GridData(grid:GridTrait[GridData],coordinate:Coordinate,data:String) extends GridDataTrait[String](grid,coordinate,data) 


case class Grid(cols:Int,rows:Int) extends GridTrait[GridData](cols,rows) :
  lazy val gridarray: Array[Array[GridData]] = 
    val g = Array.ofDim[GridData](rows,cols)

    for(col <- xRange;
      row <- yRange
    )  g(row)(col) =  GridData(this,Coordinate(col,row),Coordinate(col,row).toString())
    g
