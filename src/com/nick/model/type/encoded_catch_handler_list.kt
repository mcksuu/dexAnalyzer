package com.nick.model.type

import com.nick.model.unit.sleb128
import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/21.
 */
class encoded_catch_handler_list(
        var size: uleb128, //  列表的大小（以条目数表示）
        var list: Array<encoded_catch_handler>//处理程序列表的实际列表，直接表示（不作为偏移量）并依序连接
) : base_item() {
}

class encoded_catch_handler(
        var size: sleb128, //  此列表中捕获类型的数量。如果为非正数，则该值是捕获类型数量的负数，捕获数量后跟一个“全部捕获”处理程序。例如，size 为 0 表示捕获类型为“全部捕获”，而没有明确类型的捕获。size 为 2 表示有两个明确类型的捕获，但没有“全部捕获”类型的捕获。size 为 -1 表示有一个明确类型的捕获和一个“全部捕获”类型的捕获。
        var handlers: Array<encoded_type_addr_pair>, //abs(size) 编码项的信息流（一种捕获类型对应一项）；按照应对类型进行测试的顺序排列。
        var catch_all_addr: uleb128//（可选）“全部捕获”处理程序的字节码地址。只有当 size 为非正数时，此元素才会存在。
) : base_item() {

}

class encoded_type_addr_pair(
        var type_idx: uleb128, //    要捕获的异常类型的 type_ids 列表中的索引
        var addr: uleb128    //关联的异常处理程序的字节码地址
) : base_item() {

}