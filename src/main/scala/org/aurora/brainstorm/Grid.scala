package org.aurora.brainstorm
import org.scalajs.dom
import java.awt.Container

case class Coordinate(x:Int,y:Int)


trait ContainerDataT[D,DC <: DataCT[D]] :
  lazy val a:Array[DC]

trait DataCT[D] :
  lazy val cd:ContainerDataT[D,DataCT[D]] 

// case class ContainerData()

// case class DataC(s:String) extends DataCT[String] :
//   lazy val cd:ContainerDataT[String,DataC] = 
//     new ContainerDataT[String,DataC] () :
//       lazy val a:Array[DataC] = null

     


  
// trait GridTrait[D,E <: GridDataTrait[G,D]](cols:Int,rows:Int) {
//   lazy val gridarray:Array[Array[E]]
//   lazy val xRange = (0 to cols-1)
//   lazy val yRange = (0 to rows-1)

//   def get(x:Int,y:Int):GridDataTrait[D] = gridarray(y)(x)
//   def coordinate(data:D) :Coordinate 
// }


// trait GridDataTrait[G <:GridTrait[D] ,D](g: GridTrait[D,GridDataTrait[D]],coordinate:Coordinate,data: D)


// case class GridData(grid:GridTrait[String,GridData],coordinate:Coordinate,data:String) extends GridDataTrait[String](grid,coordinate,data)


// case class Grid(cols:Int,rows:Int) extends GridTrait[String,GridData](cols,rows) :
//   lazy val gridarray: Array[Array[GridDataTrait[String]]] = 
//     val g = Array.ofDim[GridData](rows,cols)

//     for(col <- xRange;
//       row <- yRange
//     )  g(row)(col) =  GridData(this,Coordinate(col,row),Coordinate(col,row).toString())
//     g
//   end gridarray
  
//     def coordinate(data:String) = Coordinate(999,999)
