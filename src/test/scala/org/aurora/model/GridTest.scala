package org.aurora.model

import org.scalatest._
import wordspec._
import matchers._



class GridTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
        val grid = Grid(7,2)
        grid.get(0,0) should be (GridCell(None,0,0))
        grid.get(1,1) should be (GridCell(None,1,1))

        info("grid:")
        info(s"\n${grid.show()}")


    }
  }
}
