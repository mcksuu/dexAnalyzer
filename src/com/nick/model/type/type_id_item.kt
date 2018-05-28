package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class type_id_item(
        var descriptor_idx: uint32_t //类型标识符列表。这些是此文件引用的所有类型（类、数组或原始类型）的标识符（无论文件中是否已定义）。此列表必须按 string_id 索引进行排序，且不得包含任何重复条目。
) : base_item() {
    var type: String = ""
}