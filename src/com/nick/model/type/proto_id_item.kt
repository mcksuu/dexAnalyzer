package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class proto_id_item(
        var shorty_idx: uint32_t, //此原型的简短式描述符字符串的 string_ids 列表中的索引。该字符串必须符合上文定义的 ShortyDescriptor 的语法，而且必须与该项的返回类型和参数相对应。
        var return_type_idx: uint32_t, //此原型的返回类型的 type_ids 列表中的索引。
        var parameters_off: uint32_t //从文件开头到此原型的参数类型列表的偏移量；如果此原型没有参数，则该值为 0。该偏移量（如果为非零值）应该位于 data 区段中，且其中的数据应采用下文中“"type_list"”指定的格式。此外，不得对列表中的类型 void 进行任何引用。
) : base_item() {
    var strings = ""
    var return_type = ""
    var type_list: type_list? = null

    override fun toString(): String {
        val result = "名称 $strings 返回类型 $return_type \n 参数列表 ${type_list.toString()}"
        return result
    }
}