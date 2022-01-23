package com.example.petscoffee.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.petscoffee.model.goods.Goods
import com.example.petscoffee.model.goods.GoodsConverter
import com.example.petscoffee.model.pets.Pets
import com.example.petscoffee.model.pets.PetsConverter

@TypeConverters(PetsConverter::class, GoodsConverter::class)
@Entity(tableName = "coffeeShop")
class CoffeeShop(
    val account: String,
    var name: String,
    var password: String,

) {
    var time = 0
    var day = 0
    var money = 10000f
    var pets: MutableList<Pets> = mutableListOf()// 宠物对象变长数组
    var bag: MutableList<Goods> = mutableListOf()
    @PrimaryKey
    var id: Long = 0

    fun timeChange() {
        if (time < 1) {
            time++ // 时间推移
        } else {
            day++ // 总天数增加
            time = 0
        }
    }

    fun showTime(): String {//为databinding展示时间
        return StringBuilder().run {
            append("第")
            append(day)
            append("天 的 ")
            append(if (time == 0) "上午" else "下午")
            toString()
        }
    }
}

