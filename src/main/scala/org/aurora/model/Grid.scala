package org.aurora.model
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom


trait Showable[T]:
  extension (t:T) def show():String

given Showable[GridCell] with
  extension(g: GridCell) 
    def show(): String = s"${g.inputElement}:(${g.x},${g.y})"

given Showable[Grid] with
  extension(g: Grid)
    def show(): String =
      val sb = new StringBuilder("")
      for 
        r <- 0 to g.rows-1
        c <- 0 to g.cols-1
      do 
        sb ++=  g.get(c,r).show() 
        if(c == g.cols -1)
          sb ++= "\n"   
        else  
          sb ++= ","
      s"$sb"


case class GridCell(inputElement:Option[HtmlElement],x:Int,y:Int)

object GridCell :
  def apply(inputElement:Option[HtmlElement],x:Int,y:Int) =  new GridCell(inputElement,x,y)






case class Grid(cols:Int,rows:Int) :
  private def createGrid(cols:Int,rows:Int) = 
    val g = Array.ofDim[GridCell ](rows, cols)
    val maxRow = g.length - 1
    val maxCol = g(0).length -1 
    
    for(col <- 0 to maxCol;
        row <- 0 to maxRow
    )  g(row)(col) = GridCell(None,col,row)
    g
  end createGrid

  lazy val grid = createGrid(cols,rows)

  def get(x:Int, y:Int) = grid(y)(x)

  def inputElement(x:Int,y:Int) =  
    grid(y)(x) = get(x,y).copy(  inputElement = Some( ???))

  /**
    * create 2 dim array of GridCell
    */



        

