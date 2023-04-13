package com.lilei.fitness.utils

import android.hardware.Sensor
import android.hardware.SensorManager
import android.util.Log
import java.text.DecimalFormat

class SensorUtil {
    companion object {
        fun test(mSensorManager: SensorManager) {
            val sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL)
            val sb = StringBuilder()
            sb.append("当前设备支持${sensorList?.size ?: 0}个传感器:\n")
            sensorList?.forEach { sensor ->
                sb.append("设备名称:").append(sensor.name).append("\n")
                    .append("设备版本:").append(sensor.version).append("\n")
                    .append("供应商:").append(sensor.vendor).append("\n\n")
            }
            Log.e("test", sb.toString())
        }

        //金额转换为 , 相隔
        fun setMoney(money: Double?): String {
            if (money == null || money == 0.0) {
                return "0"
            }
            val format = DecimalFormat("##,###,###,###,###,###,###,##0.00")
            var money = format.format(money)
            val l = money.split(".")
            val s = l.last()
            val f = l.first()
            money = if (s == "00") {
                return if (f.isNullOrEmpty()) {
                    "0"
                } else {
                    l[0]
                }
            } else if (s.last().toString() == "0") {
                s.substring(0, 1)
            } else {
                s
            }
            //极端情况0.01
//            if (f == "0" || f == "00" || TextUtils.isEmpty(f)) {
//                money = "0$money"
//            }
            return "$f.$money"
        }
    }
}