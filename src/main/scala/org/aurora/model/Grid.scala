package org.aurora.model

object Grid :
  case class GridCell(data:String)

  def show[T] ( g:  Array[Array[T]]) = 
    val maxRow = g.length -1
    val maxCol = g(0).length -1
    for(row <- 0 to maxRow;
       col <- 0 to maxCol ) 
       if ( col < maxCol)
        print(g(row)(col))
        else
        println(g(row)(col))
    

  /**
    * create 2 dim array of GridCell
    */
  def grid(cols:Int,rows:Int) = 
      val g = Array.ofDim[GridCell ](rows, cols)
      val maxRow = g.length - 1
      val maxCol = g(0).length -1 
      
      for(col <- 0 to maxCol;
          row <- 0 to maxRow
      )  g(row)(col) = GridCell(s"\"$col,$row\"")
      g  
  end grid
end Grid        
        

