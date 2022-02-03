package com.aliosmanarslan.glycemicindex.models

import java.util.*

data class Category(
    var cid:String = UUID.randomUUID().toString(),
    var title:String? = null,
)