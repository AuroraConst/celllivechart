package org.aurora.model.v2.utils

import scala.scalajs.js.{Date,Math}



enum DayOfWeek(num:Int):
  case MON extends DayOfWeek(6)
  case TUE extends DayOfWeek(1)
  case WED extends DayOfWeek(2)
  case THU extends DayOfWeek(3)
  case FRI extends DayOfWeek(4)
  case SAT extends DayOfWeek(5)
  case SUN extends DayOfWeek(0)


extension (d:Date)
  def differenceInDays(d2:Date) = 
    val differenceInTime = d.getTime() - d2.getTime()
      Math.round(differenceInTime / millisecPerDay)

  def dayOfWeek = 
    d.getDay().toInt match {
      case 0 =>  DayOfWeek.SUN
      case x =>  DayOfWeek.fromOrdinal(x-1)
    }

  def toDateTimeString = d.toLocaleDateString() + " " + d.toTimeString()
  //normalizes  date to midnight  
  def toMidnight:Date =
    val newDate = new Date(d.getTime())
    newDate.setHours(0,0,0,0)
    newDate

  def firstMondayDate:Date = d.dayOfWeek match {
    case DayOfWeek.SUN => d.addDays(-6)
    case dOfWeek => d.addDays(-dOfWeek.ordinal)
  }

  def listOfConsecutiveDay(numDays:Int) = 
    val firstDate = new Date(d.getTime())
    (0 until numDays).map{ _ =>
      val olddate = new Date(firstDate.getTime())
      firstDate.setDate(firstDate.getDate()+1)
      olddate
    }.toList



  
  /**
    * creates new Instance of Date with @num days added to original
    *
    * @param num
    * @return
    */    
  def addDays(num:Int):Date =
    new Date(d.getTime() + num * millisecPerDay  )

  


