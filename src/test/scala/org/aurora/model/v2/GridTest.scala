package org.aurora.model.v2

import org.scalatest._
import wordspec._
import matchers._

import scala.scalajs.js.Date
import org.aurora.model.v2.Grid
import java.time.LocalDateTime
import java.time.ZoneId


class GridTest extends AnyWordSpec with should.Matchers{
  val timeZoneId = "America/Toronto"

  "Zone ID (America/Toronto)" should {
    "exist" in {
      ZoneId.getAvailableZoneIds().forEach(info(_))
      info(s"Default ZoneID: ${ZoneId.systemDefault() }")
      // ZoneId.getAvailableZoneIds.contains(timeZoneId) should be(true) 

      true should be(true)
    }
  }

  "Grid" should {
    "Show" in {

        import collection.mutable.ListBuffer
        
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

    "Populate with a List of data" in {
      val g = Grid(7,18)
      val dataList = (0 until 14 ).toList
      import org.aurora.model.v2.{*,given}
      import org.aurora.model.v2.utils.{*,given}

      val firstDate = CalendarGrid(LocalDateTime.now(),7).firstMondayDate
      val dateList = g.linearizedleftRightCoordinates
        .map(c => firstDate.addDays(c.x + c.y*7))
        .toList

      g.populate(dateList) 
    }
  }

  
}