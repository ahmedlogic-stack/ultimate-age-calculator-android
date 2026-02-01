package com.example.agecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agecalculator.model.AgeCard
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale


class AgeViewModel : ViewModel() {
    private val _ageCardList = MutableLiveData<List<AgeCard>>()
    val ageCardList: LiveData<List<AgeCard>> = _ageCardList

    fun calculateAllCards( dob: LocalDate) {
        val now = LocalDateTime.now()
        val today = now.toLocalDate()

        //Age Right Now
        val period = Period.between(dob, today)
        val years = period.years
        val months = period.months
        val days = period.days

        val totalMonths = years * 12 + months
        val totalDays = ChronoUnit.DAYS.between(dob, now)
        val totalWeeks = totalDays / 7
        val totalHours = ChronoUnit.HOURS.between(dob.atStartOfDay(), now)
        val totalMinutes = ChronoUnit.MINUTES.between(dob.atStartOfDay(), now)
        val totalSeconds = ChronoUnit.SECONDS.between(dob.atStartOfDay(), now)

        val ageRightNow = AgeCard.AgeRightNow(years, months, days,
            totalMonths.toInt(), totalWeeks.toInt(),
            totalDays.toInt(), totalHours.toInt(),
            totalMinutes.toInt(),
            totalSeconds.toInt())

      //Born Day
        val bornDayName = dob.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val bornDayCard = AgeCard.BornDay(bornDayName)

        //Next Birthday
        var nextBirthday = dob.withYear(today.year)
        if (!nextBirthday.isAfter(today)) nextBirthday = nextBirthday.plusYears(1)
        val nextPeriod = Period.between(today, nextBirthday)
        val nextDayName = nextBirthday.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val nextBirthdayCard = AgeCard.NextBirthday(nextPeriod.months, nextPeriod.days, nextDayName)

     //Birthday Countdown

        val totalSecond = ChronoUnit.SECONDS.between(now, nextBirthday.atStartOfDay())

        val dayse = totalSecond / (24 * 3600)
        val hours = (totalSecond % (24 * 3600)) / 3600
        val minutes = (totalSecond % 3600) / 60
        val seconds = totalSecond % 60

        val countdownCard = AgeCard.BirthdayCountdown(
            months = nextPeriod.months,
            days = dayse.toInt(),
            hours = hours.toInt(),
            minutes = minutes.toInt(),
            seconds = seconds.toInt()
        )


        //Horoscope

        val zodiacCard = getZodiacSign(dob)

        _ageCardList.value = listOf(ageRightNow, bornDayCard, nextBirthdayCard, countdownCard, zodiacCard)


    }
    private fun getZodiacSign(dob: LocalDate): AgeCard.Horoscope {
        val day = dob.dayOfMonth
        val month = dob.monthValue
        val sign = when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> "Capricorn"
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
            else -> "Pisces"
        }
        val emoji = when(sign) {
            "Aries" -> "♈"; "Taurus" -> "♉"; "Gemini" -> "♊"; "Cancer" -> "♋"
            "Leo" -> "♌"; "Virgo" -> "♍"; "Libra" -> "♎"; "Scorpio" -> "♏"
            "Sagittarius" -> "♐"; "Capricorn" -> "♑"; "Aquarius" -> "♒"; else -> "♓"
        }
        val traits = when(sign) {
            "Aries" -> "Courageous, confident"; "Taurus" -> "Reliable, patient"
            "Gemini" -> "Adaptable, intelligent"; "Cancer" -> "Emotional, loyal"
            "Leo" -> "Generous, cheerful"; "Virgo" -> "Analytical, hardworking"
            "Libra" -> "Diplomatic, graceful"; "Scorpio" -> "Passionate, brave"
            "Sagittarius" -> "Optimistic, adventurous"; "Capricorn" -> "Disciplined, responsible"
            "Aquarius" -> "Innovative, humanitarian"; else -> "Compassionate, artistic"
        }
        return AgeCard.Horoscope(sign, emoji, traits)
    }



}