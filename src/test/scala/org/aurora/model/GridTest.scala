package org.aurora.model

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.model.calendardate.dayOfWeek



class GridTest extends AnyWordSpec with should.Matchers{
  "getting GridCells by (x,y)" should {
    "work" in {
        val grid = Grid(7,2)
        grid.get(0,0) should be (GridCell(grid,0,0))
        grid.get(1,1) should be (GridCell(grid,1,1))

        import ui.typeclasses.given
        info("grid:")
        info(s"\n${grid.show()}")
    }
  }

  "typeclass IdCoordinate" should {
    "work" in {
      import org.aurora.model.ui.typeclasses.given
      val grid = Grid(7,2)

      grid.get(0,0).id should be("0,0")
      grid.get(1,1).id should be("1,1")
      grid.get(0,0).coordinate("0,0") should be(Coordinates(0,0))
    }
  }


  "typeclass CoordinateDataConversion" should {
    "work" in {
      import org.aurora.model.ui.typeclasses.given
      import scala.scalajs.js.Date
      import org.aurora.model.calendardate.*

      val calgrid = CalendarGrid(new Date(),7)
      calgrid.toData(Coordinates(0,0)).dayOfWeek == DayOfWeek.MON should be (true)
    }
  }
}
