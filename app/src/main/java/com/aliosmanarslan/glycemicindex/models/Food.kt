package com.aliosmanarslan.glycemicindex.models

data class Food(
    val fid:Int? = 0,
    var cid:String? = "",
    var name:String? = null,
    var glycemicIndex:String? = null,
    var carbohydrateAmount:String? = null,
    var caloriesAmount:String? = null
)