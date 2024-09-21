package org.aurora.model.v2

import org.scalatest._
import wordspec._
import matchers._
import java.time.{LocalDateTime, DayOfWeek}
import org.aurora.model.v2.utils.{*,given}

class CalendarDateTest extends AnyWordSpec with should.Matchers{
  "dayOfWeek() extension" should {
    "map to DayOfWeek enum" in {
        val xmas = LocalDateTime.of(2023,12,25,0,0)
        xmas.getDayOfWeek.getValue() should be(DayOfWeek.MONDAY.getValue)
        xmas.plusDays(1).getDayOfWeek should be(DayOfWeek.TUESDAY)
        xmas.plusDays(2).getDayOfWeek should be(DayOfWeek.WEDNESDAY)
        xmas.plusDays(3).getDayOfWeek should be(DayOfWeek.THURSDAY)
        xmas.plusDays(4).getDayOfWeek should be(DayOfWeek.FRIDAY)
        xmas.plusDays(5).getDayOfWeek should be(DayOfWeek.SATURDAY)
        xmas.plusDays(6).getDayOfWeek should be(DayOfWeek.SUNDAY)
        xmas.plusDays(7).getDayOfWeek should be(DayOfWeek.MONDAY)
    }

    "toMidnight()" should {
      "set the time to midnight for a given date" in {
        val d = LocalDateTime.now()
        val dMidnight = d.toMidnight

        d.toLocalDate() == dMidnight.toLocalDate()  should be(true)
      }
    }

    "firstMondayDate" should {
      "create a date instance representing the preceding Monday (unchanged if day is Monday)" in {

        (0 until 7).foreach{ i =>
          val date = LocalDateTime.now()
          val monday = date.firstMondayDate
          monday.getDayOfWeek().getValue() should be(DayOfWeek.MONDAY.getValue())
        }
      }
    }

    "listConsecutiveDays" should {
      "linearize  consecutive dates" in {
        import org.aurora.model.v2.*
        import org.aurora.model.v2.utils.*
        val grid = Grid (7,3)
        var date = LocalDateTime.now().toMidnight
        val dateList = date.listConsecutiveDays(grid.linearizedleftRightCoordinates.size)

        dateList.foreach{
          d => info(s"$d")
        }  

      }
    }
  }
}
