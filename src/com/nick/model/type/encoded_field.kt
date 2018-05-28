package com.nick.model.type

import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/21.
 */
class encoded_field(
        var field_idx_diff: uleb128, //    此字段标识（包括名称和描述符）的 field_ids 列表中的索引；它会表示为与列表中前一个元素的索引之间的差值。列表中第一个元素的索引则直接表示出来。
        var access_flags: uleb128    //字段的访问标记（public 、final 等）。有关详情，请参阅“access_flags 定义”。
) : base_item() {
    var access = ""
    var field = ""
    var field_off = uleb128(0)
}