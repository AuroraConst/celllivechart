package org.aurora.brainstorm

import org.scalatest._
import wordspec._
import matchers._

import scala.scalajs.js.Date


class BrainstormGridTest extends AnyWordSpec with should.Matchers{
  "Brainstorm Grid" should {
    "Show" in {

        import scala.collection.mutable.ListBuffer
        
        val g = Grid(5,3)
        g.yRange.size should be(3)
        g.grid.size should be(3)
        g.xRange.size should be(5)
        g.yRange.size should be(3)
        g.yRange.size should be(g.grid.size)
        
        g.grid.foreach (
          r =>
            r.toString  should be ("ListBuffer(None, None, None, None, None)")
        )
        
    }

  }
}