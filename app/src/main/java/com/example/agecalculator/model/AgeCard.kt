package com.example.agecalculator.model

 sealed  class AgeCard {
     data class AgeRightNow(val years: Int, val months: Int, val days: Int,
         val monthsOld: Int, val weeksOld: Int, val daysOld: Int,
         val hoursOld: Int, val minutesOld: Int, val secondsOld: Int
         ) : AgeCard()
     data class BornDay(val dayName: String) : AgeCard()
     data class BirthdayCountdown(val months: Int, val days: Int, val hours: Int, val minutes: Int, val seconds: Int) : AgeCard()
     data class NextBirthday(val months: Int, val days: Int, val dayName: String) : AgeCard()
     data class Horoscope(val zodiacSign: String, val emoji: String, val traits: String) : AgeCard()
 }