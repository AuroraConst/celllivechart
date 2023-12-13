package org.aurora.model
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.aurora.model.ui.cellTextInput

case class Coordinates(x:Int,y:Int) :
  def addX(x:Int) = this.copy(x = this.x + x)
  def addY(y:Int) = this.copy(y = this.y + y)



import org.aurora.model.ui.typeclasses.given
trait GridT[T](cols:Int,rows:Int) :
  lazy val xRange = (0 to cols-1)
  lazy val yRange = (0 to rows-1)
  def get(x:Int,y:Int):Option[T]
  def coordinate(data:T):Coordinates
  def inBounds(c:Coordinates):Boolean =
    inBounds(c.x,c.y)
  def inBounds(x:Int,y:Int):Boolean =
    xRange.contains(x) && yRange.contains(y)
/**
  * enforces that GridData must reference the parent Grid
  *
  * @param grid
  * @param x
  * @param y
  */
trait GridDataT[G](grid:G,x:Int,y:Int)


import scala.scalajs.js.Date
case class GridCellDate[Grid](grid:Grid,d:Date,x:Int,y:Int) extends GridDataT[Grid](grid,x,y)
  lazy val inputElement = cellTextInput(this)
  lazy val value = Var(new Date())
 


case class GridCell(grid:Grid,x:Int,y:Int) extends GridDataT[Grid](grid,x,y) :
  lazy val inputElement = cellTextInput(this)
  lazy val value = Var("")
     
case class Grid(cols:Int,rows:Int) extends GridT[GridCell](cols,rows) :
  private lazy val grid = createGrid(cols,rows)

  val focusedCoordinate = Var("")
  
  private def createGrid(cols:Int,rows:Int):Array[Array[GridCell]] = 
    val g = Array.ofDim[GridCell ](rows, cols)
    
    for(col <- xRange;
        row <- yRange
    )  g(row)(col) = GridCell(this,col,row)
    g
  end createGrid

  override def get(x:Int, y:Int) = 
    inBounds(x,y) match{
      case true => Some(grid(y)(x))
      case false => None
    } 
  override  def coordinate(data:GridCell):Coordinates =
    Coordinates(data.x,data.y)
  
  def inputElement(x:Int, y:Int) =
    for{d <- get(x,y)
        inputElement <- Option(d)
    }
    yield inputElement





        

