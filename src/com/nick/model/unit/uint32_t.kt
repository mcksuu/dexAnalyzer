package com.nick.model.unit

/**
 * uint32_t
 * Created by KangShuai on 2017/11/29.
 */
class uint32_t(var value: Int) : basic_unit<Int, Long>(value) {

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(getValue())
        if (toHexString.length < 8) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

    override fun getUValue(): Long {
        return if (getValue() > 0) getValue().toLong() else getValue().toLong() + 0xffffffff + 1
    }

}

class uint32_t_f(var value: Float) : basic_unit<Float, Double>(value) {

    override fun getHexValue(): String {
        var toHexString = java.lang.Float.toHexString(value)
        if (toHexString.length < 8) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

    override fun getUValue(): Double {
        return 0.0
    }
}