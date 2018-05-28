package com.nick.model.unit

import java.math.BigInteger

/**
 * ibase
 * Created by KangShuai on 2017/11/29.
 */
interface ibasic_unit<out T, out R> {
    fun getValue(): T //value
    fun getHexValue(): String // 返回16进制格式化字符串
    fun getUValue(): R
}