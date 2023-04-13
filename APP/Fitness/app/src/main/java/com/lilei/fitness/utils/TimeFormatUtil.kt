package com.lilei.fitness.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeFormatUtil {

    companion object {

        @JvmStatic
        fun getTimeString(timeStamp: Long): String? {
            if (timeStamp == 0L) return ""
            val inputTime = Calendar.getInstance()
            inputTime.timeInMillis = timeStamp * 1000
            val currenTimeZone = inputTime.time
            val calendar = Calendar.getInstance()
            //        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            var sdf = SimpleDateFormat("yyyy/MM/dd")
            if (calendar.before(inputTime)) {
                // 今天
                sdf = SimpleDateFormat("HH:mm")
                return sdf.format(currenTimeZone)
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            if (calendar.before(inputTime)) {
                // 昨天
                sdf = SimpleDateFormat("昨天 HH:mm")
                return sdf.format(currenTimeZone)
            }
            calendar.add(Calendar.YEAR, -1)
            if (calendar.before(inputTime)) {
                //今年
                sdf = SimpleDateFormat("MM月dd日")
                return sdf.format(currenTimeZone)
            }
            return sdf.format(currenTimeZone)
        }

        @JvmStatic
        fun getTimeMsgString(timeStamp: Long): String? {
            if (timeStamp == 0L) return ""
            val inputTime = Calendar.getInstance()
            inputTime.timeInMillis = timeStamp * 1000
            val currenTimeZone = inputTime.time
            val calendar = Calendar.getInstance()
            //        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            var sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            if (calendar.before(inputTime)) {
                // 今天
                sdf = SimpleDateFormat("HH:mm")
                return sdf.format(currenTimeZone)
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            if (calendar.before(inputTime)) {
                // 昨天
                sdf = SimpleDateFormat("昨天 HH:mm")
                return sdf.format(currenTimeZone)
            }
            calendar.add(Calendar.YEAR, -1)
            if (calendar.before(inputTime)) {
                //今年
                sdf = SimpleDateFormat("MM月dd日 HH:mm")
                return sdf.format(currenTimeZone)
            }
            return sdf.format(currenTimeZone)
        }

        @JvmStatic
        fun getHomeDate(): String {
            val calendar = Calendar.getInstance()
            Calendar.DAY_OF_WEEK_IN_MONTH
            return "${getWeekDayStr(calendar.get(Calendar.DAY_OF_WEEK))} ${
                SimpleDateFormat("MM月dd日").format(
                    calendar.time
                )
            }"
        }

        fun getCurrentTimeHMS(): String {
            return SimpleDateFormat("HH:mm:ss").format(Date())
        }

        fun getCurrentTimeYear(time: Long): String {
            return SimpleDateFormat("yyyy").format(Date(time))
        }

        fun getCurrentTimeYear(): String {
            return SimpleDateFormat("yyyy").format(Date())
        }

        fun getCurrentTimeMonth(): String {
            return SimpleDateFormat("MM").format(Date())
        }

        fun getCurrentTimeMonthDay(data: Long): String {
            return SimpleDateFormat("MM月dd日").format(data)
        }

        fun getCurrentTimeMonth(date: Date): String {
            return SimpleDateFormat("MM").format(date)
        }

        fun getCurrentTimeDay(): String {
            return SimpleDateFormat("dd").format(Date())
        }

        fun getCurrentTimeDay(date: Date): String {
            return SimpleDateFormat("dd").format(date)
        }

        fun getCurrentTimeHour(long: Long): String {
            return SimpleDateFormat("HH").format(Date(long))
        }

        fun getCurrentTimeMinute(long: Long): String {
            return SimpleDateFormat("mm").format(Date(long))
        }

        fun getCurrentTimeHour(): String {
            return SimpleDateFormat("HH").format(Date())
        }

        fun getCurrentTimeMinute(): String {
            return SimpleDateFormat("mm").format(Date())
        }

        fun getCurrentTimeHM(): String {
            return SimpleDateFormat("HH:mm").format(Date())
        }

        fun getCurrentTimeYearHM(): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
        }

        fun getCurrentTimeYearHMChina(): String {
            return SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(Date())
        }

        fun getCurrentTimeYearHMChina(long: Long): String {
            return SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(Date(long))
        }

        fun getCurrentTimeHM(long: Long): String {
            return SimpleDateFormat("HH:mm").format(Date(long))
        }

        fun getCurrentTimeYM(long: Long): String {
            return SimpleDateFormat("yyyy.MM").format(Date(long))
        }

        fun getCurrentTimeYMChina(long: Long): String {
            return SimpleDateFormat("yyyy年MM月").format(Date(long))
        }

        fun getCurrentTimeYMD(long: Long): String {
            return SimpleDateFormat("yyyy.MM.dd").format(Date(long))
        }

        //距离本周日还剩几天
        fun getCurrentTimeToSun(long: Long): Int {
            val calendar = Calendar.getInstance()
            calendar.time = Date(long)
            Calendar.DAY_OF_WEEK_IN_MONTH
            val week = calendar.get(Calendar.DAY_OF_WEEK) - 1
            return if (week == 0) {
                //周日
                0
            } else {
                7 - week
            }
        }

        fun getCurrentTimeMDW(long: Long): String {
            val calendar = Calendar.getInstance()
            calendar.time = Date(long)
            Calendar.DAY_OF_WEEK_IN_MONTH
            val week = getWeekDayStr(calendar.get(Calendar.DAY_OF_WEEK))
            return "${SimpleDateFormat("MM月dd").format(calendar.time)} $week"
        }

        fun getCurrentTimeYMDWChina(long: Long): String {
            val calendar = Calendar.getInstance()
            calendar.time = Date(long)
            Calendar.DAY_OF_WEEK_IN_MONTH
            val week = getWeekDayStr(calendar.get(Calendar.DAY_OF_WEEK))
            return "${SimpleDateFormat("yyyy年MM月dd日").format(calendar.time)} $week"
        }

        fun getCurrentTimeYMDChina(long: Long): String {
            return SimpleDateFormat("yyyy年MM月dd日").format(Date(long))
        }

        fun getTodayStartTime(): Long {
            var calendar = Calendar.getInstance()
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            return calendar.timeInMillis
        }

        fun getCurrentTimeUpWeekMD(long: Long): String {
            return SimpleDateFormat("MM.dd").format(Date(long))
        }

        fun string2Time(time: String, format: String): Long {
            return SimpleDateFormat(format).parse(time).time
        }

        fun Time2String(time: Long, format: String): String {
            return SimpleDateFormat(format).format(time)
        }

        /**
         * 上个月
         */
        fun getUpMonthLong(long: Long): Long {
            var date = Date(long)
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
            return calendar.time.time
        }

        /**
         * 下个月
         */
        fun getNextMonthLong(long: Long): Long {
            var date = Date(long)
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1) // 设置为下一个月
            return calendar.time.time
        }


        fun getMondayLong(long: Long): Long {
            var date = Date(long)
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.firstDayOfWeek = Calendar.MONDAY
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            calendar.add(Calendar.DATE, calendar.firstDayOfWeek - day)
            return calendar.time.time
        }

        fun getSundayLong(long: Long): Long {
            var date = Date(long)
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.firstDayOfWeek = Calendar.MONDAY
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            calendar.add(Calendar.DATE, calendar.firstDayOfWeek - day + 6)
            return calendar.time.time
        }

        /**
         * 上周
         */
        fun getUpWeekLong(long: Long): Long {
            var date = Date(getMondayLong(long))
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar.add(Calendar.DATE, -7) // 设置为上周
            return calendar.time.time
        }

        /**
         * 下周
         */
        fun getNextWeekLong(long: Long): Long {
            var date = Date(long)
            var calendar = Calendar.getInstance()
            calendar.time = date // 设置为当前时间
            calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) + 1) // 设置为下周
            return calendar.time.time
        }


        fun getFileName(): String {
            return SimpleDateFormat("yyyyMMdd").format(Date())
        }


        fun getTimeWeekChina(long: Long): String {
            val calendar = Calendar.getInstance()
            calendar.time = Date(long)
            Calendar.DAY_OF_WEEK_IN_MONTH
            val week = getWeekDayStr(calendar.get(Calendar.DAY_OF_WEEK))
            return "$week"
        }

        /**
         * 时间转化为星期
         *
         * @param indexOfWeek 星期的第几天
         */
        fun getWeekDayStr(indexOfWeek: Int): String? {
            var weekDayStr = ""
            when (indexOfWeek) {
                1 -> weekDayStr = "周日"
                2 -> weekDayStr = "周一"
                3 -> weekDayStr = "周二"
                4 -> weekDayStr = "周三"
                5 -> weekDayStr = "周四"
                6 -> weekDayStr = "周五"
                7 -> weekDayStr = "周六"
            }
            return weekDayStr
        }

        fun timeStringToLong(time: String, format: String = "yyyy-MM-dd HH:mm"): Long {
            return SimpleDateFormat(format).parse(time).time
        }

        fun timeToLineYMD(long: Long): String {
            return SimpleDateFormat("yyyy-MM-dd").format(Date(long))
        }

        fun timeChinaToLine(time: String): String {
            return time.replace("年", "-").replace("月", "-").replace("日", "")
        }

        fun timeToChinaYMD(time: String, oldValue: String): String {
            var t = time.split(oldValue)
            return "${t[0]}年${t[1]}月${t[2]}日"
        }

    }
}