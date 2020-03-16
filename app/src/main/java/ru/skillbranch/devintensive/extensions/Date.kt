package ru.skillbranch.devintensive.extensions

import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = java.text.SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val curDate = date.time
    val _date = this.time
    val diff = curDate - _date
    System.out.println("$curDate")
    System.out.println("$_date")
    System.out.println("$diff")
    when {
        (diff / SECOND) in 0..1 -> return "только что"
        (diff / SECOND) in 1..45 -> return "несколько секунд назад"
        (diff / SECOND) in 45..75 -> return "минуту назад"
        (diff / SECOND) in 75..45 * 60 -> return "${diff / MINUTE} минут назад"
        (diff / MINUTE) in 45..75 -> return "час назад"
        (diff / MINUTE) in 75..22 * 60 -> return "${diff / HOUR} часов назад"
        (diff / HOUR) in 22..26 -> return "день назад"
        (diff / HOUR) in 26..360 * 24 -> return "${diff / DAY} дней назад"
        (diff / DAY) > 360 -> return "более года назад"
        else -> return "неизвестно"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}