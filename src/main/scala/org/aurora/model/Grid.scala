package org.aurora.model
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.ui.cellTextInput



case class GridCell(grid:Grid,x:Int,y:Int) :
  lazy val inputElement = cellTextInput

object GridCell :
  def apply(grid:Grid,x:Int,y:Int) =  new GridCell(grid,x,y)



case class Grid(cols:Int,rows:Int) :
  private def createGrid(cols:Int,rows:Int) = 
    val g = Array.ofDim[GridCell ](rows, cols)
    val maxRow = g.length - 1
    val maxCol = g(0).length -1 
    
    for(col <- 0 to maxCol;
        row <- 0 to maxRow
    )  g(row)(col) = GridCell(this,col,row)
    g
  end createGrid

  lazy val grid = createGrid(cols,rows)



  def get(x:Int, y:Int) = grid(y)(x)
  def inputElement(x:Int, y:Int) =
     grid(y)(x).inputElement


  /**
    * create 2 dim array of GridCell
    */



        

