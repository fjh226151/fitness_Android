package com.lilei.fitness.bean

import cn.bmob.v3.BmobObject

data class goods(
    var userId: String,
    var goodName: String,
    var depleteGoodsValue: String
) : BmobObject("goods")