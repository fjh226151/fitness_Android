package com.lilei.fitness.bean

import cn.bmob.v3.BmobObject

data class eatFoods(
    var eatFood: String,
    var userId: String,
    var eatGoodTime: String
) : BmobObject("eatFoods")