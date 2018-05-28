package com.nick.model.type

import com.nick.model.unit.uint32_t
import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/22.
 */
class string_data_item(
        var string_off: uint32_t,
        var utf16_size: uleb128, //此字符串的大小；以 UTF-16 代码单元（在许多系统中为“字符串长度”）为单位。也就是说，这是该字符串的解码长度（编码长度隐含在 0 字节的位置）。
        var data: ByteArray   //一系列 MUTF-8 代码单元（又称八位字节），后跟一个值为 0 的字节。请参阅上文中的“MUTF-8（修改后的 UTF-8）编码”，了解有关该数据格式的详情和讨论。
) {
    override fun toString(): String {
        return String(data)
    }
}