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
    

  def grid(rows:Int,cols:Int) = 
      val g = Array.ofDim[GridCell ](cols, rows)
      
      val maxRow = g.length - 1
      val maxCol = g(0).length -1 
      for(row <- 0 to maxRow;
          col <- 0 to maxCol
      )  g(row)(col) = GridCell(s"\"$row,$col\"")
      g  
  end grid
end Grid        
        

