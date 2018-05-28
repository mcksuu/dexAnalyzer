package com.nick.model.type

import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/21.
 */
class encoded_method(
        var method_idx_diff: uleb128, //   此方法标识（包括名称和描述符）的 method_ids 列表中的索引；它会表示为与列表中前一个元素的索引之间的差值。列表中第一个元素的索引则直接表示出来。
        var access_flags: uleb128, // 方法的访问标记（public 、final 等）。有关详情，请参阅“access_flags 定义”。
        var code_off: uleb128 //  从文件开头到此方法代码结构的偏移量；如果此方法是 abstract 或 native，则该值为 0。该偏移量应该是到 data 区段中某个位置的偏移量。数据格式由下文的“code_item”指定。
) : base_item() {
    var method = ""
    var access = ""
    var method_off: uleb128 = uleb128(0)
}