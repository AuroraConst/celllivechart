package org.aurora.model
import calendardate._
import scala.scalajs.js.Date


case class CalendarGrid (startDate:Date, numWeeks:Int):
  lazy val firstMondayDate = startDate.firstMondayDate
  lazy val grid = Grid(numWeeks,7)

  //xcoordinate
  def x(d:Date) = 6 - d.dayOfWeek.ordinal 
  //ycoordinate
  def y(d:Date) = (d.differenceInDays(firstMondayDate) / 7).toInt

  def get(x:Int,y:Int) =
    grid.get(x,y)
    

