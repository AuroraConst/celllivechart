package org.aurora.model
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.ui.cellTextInput



case class GridCell(grid:Grid,x:Int,y:Int) :
  lazy val inputElement = cellTextInput(value)
  lazy val value = Var("")

object GridCell :
  def apply(grid:Grid,x:Int,y:Int) =  new GridCell(grid,x,y)



case class Grid(cols:Int,rows:Int) :
  private lazy val grid = createGrid(cols,rows)
  lazy val xRange = (0 to cols-1)
  lazy val yRange = (0 to rows-1)

  
  private def createGrid(cols:Int,rows:Int):Array[Array[GridCell]] = 
    val g = Array.ofDim[GridCell ](rows, cols)
    
    for(col <- xRange;
        row <- yRange
    )  g(row)(col) = GridCell(this,col,row)
    g
  end createGrid


  def get(x:Int, y:Int) = grid(y)(x)
  
  def inputElement(x:Int, y:Int) =
    grid(y)(x).inputElement


  /**
    * create 2 dim array of GridCell
    */



        

