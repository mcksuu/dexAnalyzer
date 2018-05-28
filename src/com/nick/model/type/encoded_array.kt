package com.nick.model.type

import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/24.
 */
class encoded_array(
        var size: uleb128, //数组中的元素数量
        var values: Array<encoded_value>//[size]采用本节所指定格式的一系列 size encoded_value 字节序列；依序连接。
) {}

class encoded_annotation(
        var type_idx: uleb128, //注释的类型。这种类型必须是“类”（而非“数组”或“基元”）。
        var size: uleb128, //此注释中 name-value 映射的数量
        var elements: Array<annotation_element> //[size]    注释的元素，直接以内嵌形式（不作为偏移量）表示。元素必须按 string_id 索引以升序进行排序。
) {
    var type = ""
    override fun toString(): String {
        var result = "注解的类型 $type \n 数量 ${size.getValue()} \n "
        elements.forEachIndexed {
            i, it ->
            result += "第 ${i + 1} 个注解：$it \n"
        }
        return result
    }
}

class annotation_element(
        var name_idx: uleb128, //元素名称，表示为要编入 string_ids 节的索引。该字符串必须符合上文定义的 MemberName 的语法。
        var value: encoded_value    //元素值
) {
    override fun toString(): String {
        return "name_idx为 ${name_idx.getValue()} value为 $value"
    }
}
