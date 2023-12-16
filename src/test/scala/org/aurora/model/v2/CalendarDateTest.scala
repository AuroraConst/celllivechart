package org.aurora.model.v2

import org.scalatest._
import wordspec._
import matchers._

import scala.scalajs.js.Date
import org.aurora.model.v2.utils.{*,given}


class CalendarDateTest extends AnyWordSpec with should.Matchers{
  "dayOfWeek() extension" should {
    "map to DayOfWeek enum" in {
        val xmas = new Date("2023/12/25")
        xmas.dayOfWeek should be(DayOfWeek.MON)
        
        xmas.addDays(1).dayOfWeek should be(DayOfWeek.TUE)
        xmas.addDays(2).dayOfWeek should be(DayOfWeek.WED)
        xmas.addDays(3).dayOfWeek should be(DayOfWeek.THU)
        xmas.addDays(4).dayOfWeek should be(DayOfWeek.FRI)
        xmas.addDays(5).dayOfWeek should be(DayOfWeek.SAT)
        xmas.addDays(6).dayOfWeek should be(DayOfWeek.SUN)
        xmas.addDays(7).dayOfWeek should be(DayOfWeek.MON)
    }

    "toMidnight()" should {
      "set the time to midnight for a given date" in {
        val d = new Date()
        val dMidnight = d.toMidnight

        d.toDateString() should be(dMidnight.toDateString())
        dMidnight.toTimeString() should be("00:00:00 GMT-0500 (Eastern Standard Time)")
      }
    }

    "firstMondayDate" should {
      "create a date instance representing the preceding Monday (unchanged if day is Monday)" in {

        (0 until 7).foreach{ i =>
          val date = new Date()
          val monday = date.firstMondayDate
          monday.dayOfWeek should be(DayOfWeek.MON)
          (date.getTime() - monday.getTime() >=0) should be(true)
        }
      }
    }

    "listConsecutiveDays" should {
      "linearize  consecutive dates" in {
        import org.aurora.model.v2.*
        import org.aurora.model.v2.utils.*
        val grid = Grid (7,3)
        var date = new Date().toMidnight
        val dateList = date.listConsecutiveDays(grid.linearizedleftRightCoordinates.size)

        dateList.foreach{
          d => info(s"$d")
        }  

      }
    }
  }
}
