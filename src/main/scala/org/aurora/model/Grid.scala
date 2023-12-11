package org.aurora.model
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.ui.cellTextInput

case class Coordinates(x:Int,y:Int) :
  def addX(x:Int) = this.copy(x = this.x + x)
  def addY(y:Int) = this.copy(y = this.y + y)



import org.aurora.model.ui.typeclasses.given
case class GridCell(grid:Grid,x:Int,y:Int) :
  lazy val inputElement = cellTextInput(this)
  lazy val value = Var("")
 


case class Grid(cols:Int,rows:Int) :
  private lazy val grid = createGrid(cols,rows)
  lazy val xRange = (0 to cols-1)
  lazy val yRange = (0 to rows-1)

  val focusedCoordinate = Var("")

  
  private def createGrid(cols:Int,rows:Int):Array[Array[GridCell]] = 
    val g = Array.ofDim[GridCell ](rows, cols)
    
    for(col <- xRange;
        row <- yRange
    )  g(row)(col) = GridCell(this,col,row)
    g
  end createGrid


  def get(x:Int, y:Int) = grid(y)(x)
  
  def inputElement(x:Int, y:Int) =
    get(x,y).inputElement





        

