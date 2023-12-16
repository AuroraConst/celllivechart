package org.aurora.model.v2
import scala.scalajs.js.Date
import org.aurora.model.v2.utils.{*,given}

case class CalendarGrid (startDate:Date, numWeeks:Int):
  lazy val firstMondayDate = startDate.firstMondayDate
  lazy val grid = Grid(numWeeks,7)

  //xcoordinate
  def x(d:Date) = 6 - d.dayOfWeek.ordinal 
  //ycoordinate
  def y(d:Date) = (d.differenceInDays(firstMondayDate) / 7).toInt

  def data(x:Int,y:Int) =
    grid.data(x,y)
    

