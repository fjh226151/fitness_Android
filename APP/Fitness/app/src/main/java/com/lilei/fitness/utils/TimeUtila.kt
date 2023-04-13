package com.lilei.fitness.utils

import java.util.*

class TimeUtila : TimeUtils() {

    companion object {
        const val oneDay: Long = 1000 * 60 * 60 * 24
        fun betweenDays(start: Long, end: Long): Long {
            return (end - start) / (24 * 3600 * 1000) + 1
        }

        fun betweenHour(start: Long, end: Long): Float {
//            return (end - start) / (3600f * 1000f)
            return (end - start).toFloat() * 0.00001f / 36f
        }

        fun getOneHour(): Long {
            return 3600000
        }

        fun getOneDay(): Long {
            return 3600000 * 24
        }

        /**
         * 获取当天凌晨时间戳
         */
        fun getSameDay0Point(): Long {
            val c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            return c.timeInMillis
        }

        fun getSameDay1Point(): Long {
            val c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = 24
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            return c.timeInMillis
        }

        private fun getTodayCurrentTimeMillis(): Long {
            System.currentTimeMillis()
            val currentTimeYear = TimeFormatUtil.getCurrentTimeYear()
            val currentTimeMonth = TimeFormatUtil.getCurrentTimeMonth()
            val currentTimeDay: String = TimeFormatUtil.getCurrentTimeDay()
            return TimeFormatUtil.string2Time(
                "$currentTimeYear:$currentTimeMonth:$currentTimeDay",
                "yyyy:MM:dd"
            )
        }

        fun getFormatTime(time: Long): String {
            var time = time
            val todayCurrentTimeMillis =
                getTodayCurrentTimeMillis()//今天时间戳
            val tomorrow =
                getTodayCurrentTimeMillis() + oneDay
            if (time.toString().length == 10) {
                time *= 1000
            }
            //今日
            if (time in (todayCurrentTimeMillis + 1) until tomorrow) {
                return TimeFormatUtil.getCurrentTimeHour(
                    time
                ) + ":" + TimeFormatUtil.getCurrentTimeMinute(time)
            }
            //昨日
            if (todayCurrentTimeMillis > time && time > todayCurrentTimeMillis - oneDay) {
                return "昨日 " + TimeFormatUtil.getCurrentTimeHour(
                    time
                ) + ":" + TimeFormatUtil.getCurrentTimeMinute(time) + ""
            }
            //昨日之下 今年之上
            val currentTimeYear = TimeFormatUtil.getCurrentTimeYear()
            val year = "$currentTimeYear:01:01"
            val string2Time = TimeFormatUtil.string2Time(year, "yyyy:MM:dd")
            return if (time < todayCurrentTimeMillis - oneDay && time > string2Time) {
                TimeFormatUtil.getCurrentTimeMonth(Date(time)) + "月" + TimeFormatUtil.getCurrentTimeDay(
                    Date(time)
                ) + "日 " + TimeFormatUtil.getCurrentTimeHour(
                    time
                ) + ":" + TimeFormatUtil.getCurrentTimeMinute(time) + ""
            } else {
                TimeFormatUtil.getCurrentTimeYear(time) + "年" + TimeFormatUtil.getCurrentTimeMonth(
                    Date(time)
                ) + "月" + TimeFormatUtil.getCurrentTimeDay(
                    Date(time)
                ) + "日 " + TimeFormatUtil.getCurrentTimeHour(
                    time
                ) + ":" + TimeFormatUtil.getCurrentTimeMinute(time) + ""
            }
        }
    }


}