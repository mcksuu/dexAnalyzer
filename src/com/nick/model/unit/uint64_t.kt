package com.nick.model.unit

import java.math.BigInteger

/**
 * uint32_t uint64_t_d
 * Created by KangShuai on 2017/11/29.
 */
class uint64_t(var value: Long) : basic_unit<Long, BigInteger>(value) {

    override fun getHexValue(): String {
        var toHexString = java.lang.Long.toHexString(getValue())
        if (toHexString.length < 16) {
            for (i in 1..(8 - toHexString.length)) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

    override fun getUValue(): BigInteger {
        return if (getValue() > 0) BigInteger.valueOf(getValue()) else {
            var max = BigInteger.valueOf(Long.MAX_VALUE)
            max = max.add(BigInteger.valueOf(1))
            max.multiply(BigInteger.valueOf(2))
        }
    }
}

class uint64_t_d(var value: Double) : basic_unit<Double, Double>(value) {

    override fun getHexValue(): String {
        var toHexString = java.lang.Double.toHexString(getValue())
        if (toHexString.length < 16) {
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