package org.aurora.model

import org.scalatest._
import wordspec._
import matchers._



class GridTest extends AnyWordSpec with should.Matchers{
  "this" should {
    "work" in {
        val g = Grid.grid(5,2) 
        Grid.show[Grid.GridCell](g)
    }
  }
}
