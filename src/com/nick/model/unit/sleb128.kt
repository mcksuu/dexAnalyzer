package com.nick.model.unit

/**
 * Created by KangShuai on 2018/5/21.
 */
class sleb128(var value: Int) : basic_unit<Int, Long>(value) {
    override fun getUValue(): Long {
        return if (getValue() > 0) getValue().toLong() else getValue().toLong() + 0xffffffff + 1
    }

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(getValue())
        if (toHexString.length < 8) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }
}