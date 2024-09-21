package org.aurora.model.v2.utils

// import scala.scalajs.js.{Date,Math}
import java.time.{LocalDateTime, LocalDate, LocalTime,Duration, DayOfWeek}
import java.time.format.DateTimeFormatter
/**
  * extension methods for Javascript type: Date
  */


extension (d:LocalDateTime)
  
  def differenceInDays(d2:LocalDateTime) = 
     Duration.between(d,d2).toMillis()
      



  def toDateTimeString =  DateTimeFormatter.ofPattern("yyyy-MM-dd").format(d)
  //normalizes  date to midnight  
  def toMidnight:LocalDateTime =  d.`with`(LocalTime.MIDNIGHT)
    

  def firstMondayDate:LocalDateTime = d.`with`(DayOfWeek.MONDAY)

    // Gets a list of consecutive days
  def listConsecutiveDays(numDays: Int): List[LocalDateTime] = 
    (0 until numDays).map(d.plusDays(_)).toList

  // def toDateString() = d.datetoLocaleDateString()

  /**
    * creates new Instance of Date with @num days added to original
    *
    * @param num
    * @return
    */    
  def addDays(num:Int):LocalDateTime =
    d.plusDays(num)

  


