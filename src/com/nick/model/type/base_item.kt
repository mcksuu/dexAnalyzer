package com.nick.model.type

import com.nick.model.unit.basic_unit

/**
 * Created by KangShuai on 2018/5/22.
 */
open class base_item {
    override fun toString(): String {
        this.javaClass.declaredFields.forEach {
            it.isAccessible = true
            if (it.get(this) is basic_unit<*, *>) {
                val basic_unit = it.get(this) as basic_unit<*, *>
                println(it.name + " \thexValue = " + basic_unit.getHexValue() + " \tvalue = " + basic_unit.getValue() + " \tUValue = " + basic_unit.getUValue())
            }
        }
        return super.toString()
    }
}