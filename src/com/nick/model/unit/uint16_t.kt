package com.nick.model.unit

/**
 * uint16_t
 * Created by KangShuai on 2017/11/29.
 */
class uint16_t(value: Short) : basic_unit<Short, Int>(value) {

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(getValue().toInt())
        if (toHexString.length < 4) {
            for (i in 1..4 - toHexString.length) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

    override fun getUValue(): Int {
        return if (getValue() > 0) getValue().toInt() else 0xffff + getValue() + 1
    }
}