package com.pro.blog.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class TimeOperation(
    val option: String
) {
    YEAR("year"),
    MONTH("month"),
    WEEK("week"),
    DAY("day"),
}

fun getTimestamp() = System.currentTimeMillis()

fun timestampMinus(type: TimeOperation, number: Long): Long {
    val now = getTimestamp()
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val nowStr = formatter.format(now)
    val date = LocalDateTime.parse(nowStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    val newDate = when (type) {
        TimeOperation.YEAR -> {
            date.minusYears(number)
        }
        TimeOperation.MONTH -> {
            date.minusMonths(number)
        }
        TimeOperation.WEEK -> {
            date.minusWeeks(number)
        }
        TimeOperation.DAY -> {
            date.minusDays(number)
        }
    }

    return formatter.parse(newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).time
}
