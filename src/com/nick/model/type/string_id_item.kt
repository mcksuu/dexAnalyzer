package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class string_id_item(
        var string_data_off: uint32_t//    从文件开头到此项的字符串数据的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量，而数据应采用下文中“string_data_item”指定的格式。没有偏移量对齐要求。
) : base_item() {
}