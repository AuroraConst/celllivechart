package org.aurora.model

import org.scalatest._
import wordspec._
import matchers._



class GridTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
        val grid = Grid(7,2)
        grid.get(0,0) should be (GridCell(grid,0,0))
        grid.get(1,1) should be (GridCell(grid,1,1))

        import ui.typeclasses.given
        info("grid:")
        info(s"\n${grid.show()}")


    }
  }
}
