package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/21.
 */
class type_list(
        var size: uint32_t, //列表的大小（以条目数表示）
        var list: Array<type_item> // 列表的元素

) : base_item() {

    override fun toString(): String {
        var result = "接口数量 ${size.getValue()} \n"

        list.forEach {
            result += " 接口 ${it.type}\n"
        }

        return result
    }
}

class type_item(
        var type_idx: uint16_t//    type_ids 列表中的索引
) : base_item() {
    var type = ""
}
