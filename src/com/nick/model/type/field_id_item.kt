package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class field_id_item(
        var class_idx: uint16_t, //   此字段的定义符的 type_ids 列表中的索引。此项必须是“类”类型，而不能是“数组”或“基元”类型。
        var type_idx: uint16_t, //此字段的类型的 type_ids 列表中的索引。
        var name_idx: uint32_t   // 此字段的名称的 string_ids 列表中的索引。该字符串必须符合上文定义的 MemberName 的语法。
) : base_item() {
    var type = ""
    var clazz = ""
    var name = ""
}