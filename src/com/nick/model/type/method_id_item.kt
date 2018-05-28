package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class method_id_item(
        var class_idx: uint16_t, // 此方法的定义符的 type_ids 列表中的索引。此项必须是“类”或“数组”类型，而不能是“基元”类型。
        var proto_idx: uint16_t, // 此方法的原型的 proto_ids 列表中的索引。
        var name_idx: uint32_t    //此方法名称的 string_ids 列表中的索引。该字符串必须符合上文定义的 MemberName 的语法。
) : base_item() {
    var clazz = ""
    var proto_list: type_list? = null
    var name = ""
}