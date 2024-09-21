package org.aurora.model.v2

import org.aurora.model.v2.utils.{*,given}
import java.time.{LocalDateTime, LocalDate, LocalTime,Duration, DayOfWeek}

case class CalendarGrid (startDate:LocalDateTime, numWeeks:Int):
  lazy val firstMondayDate = startDate.firstMondayDate
  lazy val grid = Grid(7,numWeeks)

  //xcoordinate
  private def x(d:LocalDateTime) =  (d.getDayOfWeek.getValue % 7) - 1
  //ycoordinate
  private def y(d:LocalDateTime) = (d.differenceInDays(firstMondayDate) / 7).toInt

  def data(date:LocalDateTime):Option[GridData] =
    data(x(date),y(date))

  def data(x:Int,y:Int):Option[GridData] =
    grid.data(x,y)
    

