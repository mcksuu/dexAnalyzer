package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/21.
 */
class try_item(
        var start_addr: uint32_t, //    此条目涵盖的代码块的起始地址。该地址是到第一个所涵盖指令开头部分的 16 位代码单元的计数。
        var insn_count: uint16_t, //此条目所覆盖的 16 位代码单元的数量。所涵盖（包含）的最后一个代码单元是 start_addr + insn_count - 1。
        var handler_off: uint16_t    //从关联的 encoded_catch_hander_list 开头部分到此条目的 encoded_catch_handler 的偏移量（以字节为单位）。此偏移量必须是到 encoded_catch_handler 开头部分的偏移量。
) : base_item() {
}