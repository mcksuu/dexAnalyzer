package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/21.
 */
class code_item(
        var registers_size: uint16_t, //    此代码使用的寄存器数量
        var ins_size: uint16_t, //此代码所用方法的传入参数的字数
        var outs_size: uint16_t, //此代码进行方法调用所需的传出参数空间的字数
        var tries_size: uint16_t, //此实例的 try_item 数量。如果此值为非零值，则这些项会显示为 tries 数组（正好位于此实例中 insns 的后面）。

        var debug_info_off: uint32_t, //    从文件开头到此代码的调试信息（行号+局部变量信息）序列的偏移量；如果没有任何信息，则该值为 0。该偏移量（如果为非零值）应该是到 data 区段中某个位置的偏移量。数据格式由下文的“debug_info_item”指定。
        var insns_size: uint32_t, // 指令列表的大小（以 16 位代码单元为单位）
        var insns: Array<uint16_t>, // 字节码的实际数组。insns 数组中代码的格式由随附文档 Dalvik 字节码指定。请注意，尽管此项被定义为 ushort 的数组，但仍有一些内部结构倾向于采用四字节对齐方式。此外，如果此项恰好位于某个字节序交换文件中，则交换操作将只在单个 ushort 上进行，而不是在较大的内部结构上进行。
        var padding: uint16_t, // （可选） = 0    使 tries 实现四字节对齐的两字节填充。只有 tries_size 为非零值且 insns_size 是奇数时，此元素才会存在。
        var tries: Array<try_item>, // （可选）    用于表示在代码中捕获异常的位置以及如何对异常进行处理的数组。该数组的元素在范围内不得重叠，且数值地址按照从低到高的顺序排列。只有 tries_size 为非零值时，此元素才会存在。
        var handlers: encoded_catch_handler_list // （可选）    用于表示“捕获类型列表和关联处理程序地址”的列表的字节。每个 try_item 都具有到此结构的分组偏移量。只有 tries_size 为非零值时，此元素才会存在。
) : base_item() {
}