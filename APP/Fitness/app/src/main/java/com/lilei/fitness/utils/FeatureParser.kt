package com.lilei.fitness.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import tech.gujin.toast.ToastUtil
import java.lang.reflect.Method
import java.util.*

class FeatureParser {
    companion object {
        var isSupport: Boolean = getBoolean("support_steps_provider", false)
        fun getBoolean(name: String?, defaultValue: Boolean): Boolean {
            try {
                val featureParserClass = Class.forName("miui.util.FeatureParser")
                val method: Method = featureParserClass.getMethod(
                    "getBoolean",
                    String::class.java,
                    Boolean::class.javaPrimitiveType
                )
                return method.invoke(null, name, defaultValue) as Boolean
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return defaultValue
        }

        fun XiaoMiGetSteps(context: Context): LinkedList<XiaoMiStep>? {
            if (!isSupport) {
                ToastUtil.show("该手机不支持获取步数")
                return null
            }
            val steps: LinkedList<XiaoMiStep> = LinkedList<XiaoMiStep>()
            val query: Cursor? = context.contentResolver.query(
                Steps.CONTENT_URI,
                Steps.projection,
                null,
                null,
                Steps.DEFAULT_SORT_ORDER
            )
            if (query?.moveToFirst() == true) {
                do {
                    steps.add(
                        XiaoMiStep(
                            query.getInt(0),
                            query.getLong(1),
                            query.getLong(2),
                            query.getInt(3),
                            query.getInt(4)
                        )
                    )
                } while (query.moveToNext())
            }
            return steps
        }

        fun getHuaWeiStatus() {
            try {
                val featureParserClass = Class.forName(/* className = */ "com.huawei")
                for (method: Method in featureParserClass.methods) {
                    Log.e("test", "value:" + method)
                }
            } catch (e: Exception) {

            }
        }
    }
}

data class XiaoMiStep(
    val id: Int = 0,
    val mBeginTime: Long = 0,
    val mEndTime: Long = 0,
    val mMode: Int = 0,
    val mSteps: Int = 0,
)

object Steps {
    /* Data Field */
    const val ID = "_id"
    const val BEGIN_TIME = "_begin_time"
    const val END_TIME = "_end_time"

    // 0: NOT SUPPORT 1:REST 2:WALKING 3:RUNNING
    const val MODE = "_mode"
    const val STEPS = "_steps"

    /* Default sort order */
    const val DEFAULT_SORT_ORDER = "_id asc"

    /* Authority */
    const val AUTHORITY = "com.miui.providers.steps"

    /* Content URI */
    val CONTENT_URI: Uri = Uri.parse("content://" + AUTHORITY + "/item")
    var projection = arrayOf(
        Steps.ID,
        Steps.BEGIN_TIME,
        Steps.END_TIME,
        Steps.MODE,
        Steps.STEPS
    )
}