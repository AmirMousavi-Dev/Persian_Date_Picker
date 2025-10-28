package com.amirmousavi_dev.date_picker.core

import com.amirmousavi_dev.date_picker.util.FormatDigits
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

/**
 * An immutable, robust representation of a date in the Persian calendar system.
 *
 * Use the factory methods in the companion object (e.g., `PersianDate.now()`,
 * `PersianDate.fromGregorian()`) to create instances.
 *
 * @property year The Persian year.
 * @property month The Persian month (1-12).
 * @property day The Persian day (1-31).
 */
data class PersianDate(
    val year: Int,
    val month: Int,
    val day: Int
) : Comparable<PersianDate> {

    init {
        require(month in 1..12) { "Month must be between 1 and 12, but was $month" }
        require(day in 1..monthLength) { "Day must be between 1 and $monthLength for month $month, but was $day" }
    }


    /**
     * The day of the week, where Sunday is 1 and Saturday is 7.
     * Note: This involves a conversion to Gregorian and may be computationally intensive if used excessively.
     */
    val dayOfWeek: Int
        get() = toGregorian()[Calendar.DAY_OF_WEEK]

    /**
     * The Persian name for the day of the week.
     */
    val dayOfWeekString: String
        get() = when (dayOfWeek) {
            7 -> "شنبه"
            1 -> "یک‌شنبه"
            2 -> "دوشنبه"
            3 -> "سه‌شنبه"
            4 -> "چهارشنبه"
            5 -> "پنجشنبه"
            6 -> "جمعه"
            else -> "نامعلوم"
        }

    /**
     * The Persian name for the month).
     */
    val monthString: String
        get() = when (month) {
            1 -> "فروردین"
            2 -> "اردیبهشت"
            3 -> "خرداد"
            4 -> "تیر"
            5 -> "مرداد"
            6 -> "شهریور"
            7 -> "مهر"
            8 -> "آبان"
            9 -> "آذر"
            10 -> "دی"
            11 -> "بهمن"
            12 -> "اسفند"
            else -> "نامعلوم"
        }

    /**
     * A formatted string of the date (e.g., "1403/07/09").
     */
    val dateString: String
        get() = String.format(Locale.US, "%04d/%02d/%02d", year, month, day)

    /**
     * A user-friendly string combining day of the week, day, and month (e.g., "سه‌شنبه ۹ مهر").
     */
    val dayOfWeekDayMonthString: String
        get() = "$dayOfWeekString ${FormatDigits.toPersianDigits(day.toString())} $monthString"

    /**
     * Returns true if the current Persian year is a leap year.
     */
    val isLeap: Boolean
        get() = getLeapFactor(year) == 0

    /**
     * The number of days in the current Persian month.
     */
    val monthLength: Int
        get() = when {
            month < 7 -> 31
            month < 12 -> 30
            month == 12 -> if (isLeap) 30 else 29
            else -> 0
        }



    /**
     * Returns a new `PersianDate` instance with the given number of days added.
     *
     * @param days The number of days to add (can be negative to subtract).
     */
    fun plusDays(days: Int): PersianDate {
        val gc = toGregorian()
        gc.add(Calendar.DAY_OF_MONTH, days)
        return fromGregorian(gc)
    }

    /**
     * Converts this `PersianDate` instance to a `java.util.GregorianCalendar` instance.
     */
    fun toGregorian(): GregorianCalendar {
        val julianDay = toJulianDay(year, month, day)
        return julianDayToGregorianCalendar(julianDay)
    }

    /**
     * Compares this date to another date.
     *
     * @return A negative integer, zero, or a positive integer as this date is before,
     * at the same time, or after the specified date.
     */
    override fun compareTo(other: PersianDate): Int {
        if (year != other.year) return year.compareTo(other.year)
        if (month != other.month) return month.compareTo(other.month)
        return day.compareTo(other.day)
    }

    /**
     * Returns a string representation of the date in `YYYY-MM-DD` format.
     */
    override fun toString(): String {
        return String.format(Locale.US, "%04d-%02d-%02d", year, month, day)
    }


    companion object {


        /**
         * Gets the current date in the Persian calendar.
         */
        
        fun now(): PersianDate = fromGregorian(GregorianCalendar())

        /**
         * Creates a `PersianDate` instance from a `java.util.Date` object.
         */
        
        fun fromDate(date: Date): PersianDate {
            val gc = GregorianCalendar()
            gc.time = date
            return fromGregorian(gc)
        }

        /**
         * Creates a `PersianDate` instance from a `java.util.GregorianCalendar` object.
         */
        
        fun fromGregorian(gc: GregorianCalendar): PersianDate {
            val jd = gregorianToJulianDayNumber(gc)
            return fromJulianDay(jd)
        }

        /**
         * Creates a `PersianDate` instance from year, month, and day components.
         * This method is an alias for the data class constructor and will perform validation.
         */
        
        fun of(year: Int, month: Int, day: Int): PersianDate {
            return PersianDate(year, month, day)
        }



        /** The source of this algorithm is "Calendrical Calculations" by Dershowitz and Reingold. */
        private val breaks = intArrayOf(
            -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
            1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178
        )

        private fun fromJulianDay(julianDayNumber: Int): PersianDate {
            val gc = julianDayToGregorianCalendar(julianDayNumber)
            val gregorianYear = gc[GregorianCalendar.YEAR]

            var persianYear = gregorianYear - 621


            var julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardin(persianYear))

            var diff = julianDayNumber - julianDayFarvardinFirst

            if (diff < 0) {
                persianYear--
                julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardin(persianYear))
                diff = julianDayNumber - julianDayFarvardinFirst
            }

            val persianMonth: Int
            val persianDay: Int

            if (diff < 186) {
                persianMonth = 1 + (diff / 31)
                persianDay = 1 + (diff % 31)
            } else {
                val remainingDays = diff - 186
                persianMonth = 7 + (remainingDays / 30)
                persianDay = 1 + (remainingDays % 30)
            }

            return PersianDate(persianYear, persianMonth, persianDay)
        }

        private fun toJulianDay(year: Int, month: Int, day: Int): Int {
            val julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardin(year))
            val dayInYear = if (month <= 6) {
                (month - 1) * 31 + day
            } else {
                186 + (month - 7) * 30 + day
            }
            return julianDayFarvardinFirst + dayInYear - 1
        }

        private fun gregorianToJulianDayNumber(gc: GregorianCalendar): Int {
            val gregorianYear = gc[GregorianCalendar.YEAR]
            val gregorianMonth = gc[GregorianCalendar.MONTH] + 1
            val gregorianDay = gc[GregorianCalendar.DAY_OF_MONTH]

            return (((1461 * (gregorianYear + 4800 + (gregorianMonth - 14) / 12)) / 4
                    + (367 * (gregorianMonth - 2 - 12 * ((gregorianMonth - 14) / 12))) / 12
                    - (3 * ((gregorianYear + 4900 + (gregorianMonth - 14) / 12) / 100)) / 4 + gregorianDay
                    - 32075) - (gregorianYear + 100100 + (gregorianMonth - 8) / 6) / 100 * 3 / 4 + 752)
        }

        private fun julianDayToGregorianCalendar(julianDayNumber: Int): GregorianCalendar {
            val j = 4 * julianDayNumber + 139361631 + (4 * julianDayNumber + 183187720) / 146097 * 3 / 4 * 4 - 3908
            val i = (j % 1461) / 4 * 5 + 308

            val gregorianDay = (i % 153) / 5 + 1
            val gregorianMonth = ((i / 153) % 12) + 1
            val gregorianYear = j / 1461 - 100100 + (8 - gregorianMonth) / 6

            return GregorianCalendar(gregorianYear, gregorianMonth - 1, gregorianDay)
        }

        private fun getGregorianFirstFarvardin(persianYear: Int): GregorianCalendar {
            val gregorianYear = persianYear + 621
            var persianLeap = -14
            var jp = breaks[0]
            var marchDay = 0

            var jump: Int
            for (j in 1..19) {
                val jm = breaks[j]
                jump = jm - jp
                if (persianYear < jm) {
                    var n = persianYear - jp
                    persianLeap += n / 33 * 8 + (n % 33 + 3) / 4
                    if (jump % 33 == 4 && jump - n == 4) persianLeap++
                    val gregorianLeap = (gregorianYear / 4) - ((gregorianYear / 100 + 1) * 3 / 4) - 150
                    marchDay = 20 + (persianLeap - gregorianLeap)
                    if (jump - n < 6) n = n - jump + (jump + 4) / 33 * 33
                    break
                }
                persianLeap += jump / 33 * 8 + (jump % 33) / 4
                jp = jm
            }
            return GregorianCalendar(gregorianYear, 2, marchDay)
        }

        private fun getLeapFactor(persianYear: Int): Int {
            var leap = 0
            var jp = breaks[0]
            var jump: Int
            for (j in 1..19) {
                val jm = breaks[j]
                jump = jm - jp
                if (persianYear < jm) {
                    var n = persianYear - jp
                    if (jump - n < 6) n = n - jump + (jump + 4) / 33 * 33
                    leap = ((((n + 1) % 33) - 1) % 4)
                    if (leap == -1) leap = 4
                    break
                }
                jp = jm
            }
            return leap
        }
    }
}
