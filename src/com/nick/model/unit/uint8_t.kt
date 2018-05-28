package com.nick.model.unit

/**uint8_t
 * Created by KangShuai on 2017/11/29.
 */
class uint8_t(value: Byte) : basic_unit<Byte, Int>(value) {

    override fun getHexValue(): String {
        var toHexString = Integer.toHexString(getValue().toInt())
        if (toHexString.length < 2) {
            for (i in 1..2 - toHexString.length) {
                toHexString = "0" + toHexString
            }
        }
        return "0x" + toHexString
    }

    override fun getUValue(): Int {
        return if (relValue > 0) relValue.toInt() else relValue + 0xff+1
    }
}