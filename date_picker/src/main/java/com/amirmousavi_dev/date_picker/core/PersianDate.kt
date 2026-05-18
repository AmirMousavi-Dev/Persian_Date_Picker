package com.amirmousavi_dev.date_picker.core

import com.amirmousavi_dev.date_picker.util.FormatDigits

/**
 * An immutable, robust representation of a date in the Persian calendar system.
 * This class is designed to be platform-independent and compatible with Compose Multiplatform.
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
        require(day in 1..lengthOfMonth) { "Day must be between 1 and $lengthOfMonth for month $month, but was $day" }
    }

    /**
     * The day of the week, where Sunday is 1 and Saturday is 7.
     */
    val dayOfWeek: Int
        get() = ((toJulianDay(year, month, day) + 1) % 7) + 1

    /**
     * The Persian name for the day of the week.
     */
    val dayName: String
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
     * The Persian name for the month.
     */
    val monthName: String
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
     * A formatted string of the date in `YYYY/MM/DD` format.
     */
    val formattedDate: String
        get() = "${year.toString().padStart(4, '0')}/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"

    /**
     * A user-friendly string combining day of the week, day, and month (e.g., "سه‌شنبه ۹ مهر").
     */
    val fullDateName: String
        get() = "$dayName ${FormatDigits.toPersianDigits(day.toString())} $monthName"

    /**
     * Returns true if the current Persian year is a leap year.
     */
    val isLeapYear: Boolean
        get() = getLeapFactor(year) == 0

    /**
     * The number of days in the current Persian month.
     */
    val lengthOfMonth: Int
        get() = when {
            month < 7 -> 31
            month < 12 -> 30
            month == 12 -> if (isLeapYear) 30 else 29
            else -> 0
        }

    /**
     * Returns a new `PersianDate` instance with the given number of days added.
     *
     * @param days The number of days to add (can be negative to subtract).
     */
    fun plusDays(days: Int): PersianDate {
        val jd = toJulianDay(year, month, day)
        return fromJulianDay(jd + days)
    }

    /**
     * Converts this `PersianDate` instance to a `java.util.GregorianCalendar` instance.
     * Note: This is JVM-only and provided for backward compatibility.
     */
    fun toGregorian(): java.util.GregorianCalendar {
        val jd = toJulianDay(year, month, day)
        val j = 4 * jd + 139361631 + (4 * jd + 183187720) / 146097 * 3 / 4 * 4 - 3908
        val i = (j % 1461) / 4 * 5 + 308
        val gDay = (i % 153) / 5 + 1
        val gMonth = ((i / 153) % 12) + 1
        val gYear = j / 1461 - 100100 + (8 - gMonth) / 6
        return java.util.GregorianCalendar(gYear, gMonth - 1, gDay)
    }

    override fun compareTo(other: PersianDate): Int {
        if (year != other.year) return year.compareTo(other.year)
        if (month != other.month) return month.compareTo(other.month)
        return day.compareTo(other.day)
    }

    override fun toString(): String {
        return "${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
    }

    companion object {

        /**
         * Gets the current date in the Persian calendar.
         * Note: This implementation uses JVM `java.util.Calendar` and should be replaced
         * with a platform-specific source (like kotlinx-datetime) in Multiplatform environments.
         */
        fun now(): PersianDate {
            val gc = java.util.GregorianCalendar()
            return fromGregorian(gc)
        }

        /**
         * Creates a `PersianDate` instance from a `java.util.Date` object.
         */
        fun fromDate(date: java.util.Date): PersianDate {
            val gc = java.util.GregorianCalendar()
            gc.time = date
            return fromGregorian(gc)
        }

        /**
         * Creates a `PersianDate` instance from a `java.util.GregorianCalendar` object.
         */
        fun fromGregorian(gc: java.util.GregorianCalendar): PersianDate {
            return fromGregorian(
                gc.get(java.util.Calendar.YEAR),
                gc.get(java.util.Calendar.MONTH) + 1,
                gc.get(java.util.Calendar.DAY_OF_MONTH)
            )
        }

        /**
         * Creates a `PersianDate` instance from Gregorian year, month, and day.
         */
        fun fromGregorian(year: Int, month: Int, day: Int): PersianDate {
            val jd = gregorianToJulianDayNumber(year, month, day)
            return fromJulianDay(jd)
        }

        /**
         * Creates a `PersianDate` instance from year, month, and day components.
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
            val gYear = julianDayToGregorianYear(julianDayNumber)
            var persianYear = gYear - 621

            var julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardinComponents(persianYear))
            var diff = julianDayNumber - julianDayFarvardinFirst

            if (diff < 0) {
                persianYear--
                julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardinComponents(persianYear))
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
            val julianDayFarvardinFirst = gregorianToJulianDayNumber(getGregorianFirstFarvardinComponents(year))
            val dayInYear = if (month <= 6) {
                (month - 1) * 31 + day
            } else {
                186 + (month - 7) * 30 + day
            }
            return julianDayFarvardinFirst + dayInYear - 1
        }

        private fun gregorianToJulianDayNumber(components: IntArray): Int {
            return gregorianToJulianDayNumber(components[0], components[1], components[2])
        }

        private fun gregorianToJulianDayNumber(gYear: Int, gMonth: Int, gDay: Int): Int {
            return (((1461 * (gYear + 4800 + (gMonth - 14) / 12)) / 4
                    + (367 * (gMonth - 2 - 12 * ((gMonth - 14) / 12))) / 12
                    - (3 * ((gYear + 4900 + (gMonth - 14) / 12) / 100)) / 4 + gDay
                    - 32075) - (gYear + 100100 + (gMonth - 8) / 6) / 100 * 3 / 4 + 752)
        }

        private fun julianDayToGregorianYear(julianDayNumber: Int): Int {
            val j = 4 * julianDayNumber + 139361631 + (4 * julianDayNumber + 183187720) / 146097 * 3 / 4 * 4 - 3908
            val i = (j % 1461) / 4 * 5 + 308
            val gMonth = ((i / 153) % 12) + 1
            return j / 1461 - 100100 + (8 - gMonth) / 6
        }

        private fun getGregorianFirstFarvardinComponents(persianYear: Int): IntArray {
            val gregorianYear = persianYear + 621
            var persianLeap = -14
            var jp = breaks[0]
            var marchDay = 0

            var jump: Int
            for (j in 1..19) {
                val jm = breaks[j]
                jump = jm - jp
                if (persianYear < jm) {
                    val n = persianYear - jp
                    persianLeap += n / 33 * 8 + (n % 33 + 3) / 4
                    if (jump % 33 == 4 && jump - n == 4) persianLeap++
                    val gregorianLeap = (gregorianYear / 4) - ((gregorianYear / 100 + 1) * 3 / 4) - 150
                    marchDay = 20 + (persianLeap - gregorianLeap)
                    break
                }
                persianLeap += jump / 33 * 8 + (jump % 33) / 4
                jp = jm
            }
            return intArrayOf(gregorianYear, 3, marchDay)
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
