package org.aurora.model.v2
import scala.scalajs.js.Date
import org.aurora.model.v2.utils.{*,given}

case class CalendarGrid (startDate:Date, numWeeks:Int):
  lazy val firstMondayDate = startDate.firstMondayDate
  lazy val grid = Grid(7,numWeeks)

  //xcoordinate
  private def x(d:Date) = 6 - d.dayOfWeek.ordinal 
  //ycoordinate
  private def y(d:Date) = (d.differenceInDays(firstMondayDate) / 7).toInt

  def data(date:Date):Option[GridData] =
    data(x(date),y(date))

  def data(x:Int,y:Int):Option[GridData] =
    grid.data(x,y)
    

