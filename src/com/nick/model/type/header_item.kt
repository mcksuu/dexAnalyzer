package com.nick.model.type

import com.nick.model.unit.basic_unit
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint8_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class header_item(
        var magic: Array<uint8_t>,
        var checksum: uint32_t, //文件剩余内容（除 magic 和此字段之外的所有内容）的 adler32 校验和；用于检测文件损坏情况
        var signature: ByteArray, //文件剩余内容（除 magic、checksum 和此字段之外的所有内容）的 SHA-1 签名（哈希）；用于对文件进行唯一标识
        var file_size: uint32_t, //    整个文件（包括标头）的大小，以字节为单位
        var header_size: uint32_t, //    标头（整个区段）的大小，以字节为单位。这一项允许至少一定程度的向后/向前兼容性，而不必让格式失效。
        var endian_tag: uint32_t, // ENDIAN_CONSTANT    字节序标记。更多详情，请参阅上文中“ENDIAN_CONSTANT 和 REVERSE_ENDIAN_CONSTANT”下的讨论。
        var link_size: uint32_t, //  链接区段的大小；如果此文件未进行静态链接，则该值为 0
        var link_off: uint32_t, //  从文件开头到链接区段的偏移量；如果 link_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 link_data 区段的偏移量。本文档尚未指定此处所指的数据格式；此标头字段（和之前的字段）会被保留为钩子，以供运行时实现使用。
        var map_off: uint32_t, //  从文件开头到映射项的偏移量。该偏移量（必须为非零）应该是到 data 区段的偏移量，而数据应采用下文中“map_list”指定的格式。
        var string_ids_size: uint32_t, //   字符串标识符列表中的字符串数量
        var string_ids_off: uint32_t, //   从文件开头到字符串标识符列表的偏移量；如果 string_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 string_ids 区段开头的偏移量。
        var type_ids_size: uint32_t, //  类型标识符列表中的元素数量，最多为 65535
        var type_ids_off: uint32_t, //  从文件开头到类型标识符列表的偏移量；如果 type_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 type_ids 区段开头的偏移量。
        var proto_ids_size: uint32_t, //    原型标识符列表中的元素数量，最多为 65535
        var proto_ids_off: uint32_t, //  从文件开头到原型标识符列表的偏移量；如果 proto_ids_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 proto_ids 区段开头的偏移量。
        var field_ids_size: uint32_t, //   字段标识符列表中的元素数量
        var field_ids_off: uint32_t, //  从文件开头到字段标识符列表的偏移量；如果 field_ids_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 field_ids 区段开头的偏移量。
        var method_ids_size: uint32_t, //   方法标识符列表中的元素数量
        var method_ids_off: uint32_t, //   从文件开头到方法标识符列表的偏移量；如果 method_ids_size == 0，则该值为 0。该偏移量（如果为非零值）应该是到 method_ids 区段开头的偏移量。
        var class_defs_size: uint32_t, //   类定义列表中的元素数量
        var class_defs_off: uint32_t, //   从文件开头到类定义列表的偏移量；如果 class_defs_size == 0（不可否认是一种奇怪的极端情况），则该值为 0。该偏移量（如果为非零值）应该是到 class_defs 区段开头的偏移量。
        var data_size: uint32_t, // data 区段的大小（以字节为单位）。该数值必须是 sizeof(uint) 的偶数倍。
        var data_off: uint32_t //从文件开头到 data 区段开头的偏移量。
) : base_item() {
    override fun toString(): String {
        var s = ""
        signature.forEach {
            s += java.lang.Integer.toHexString(it.toInt() and 0xff) + ","
        }

        val b = ByteArray(magic.size, { i -> magic[i].getValue() })
        val string = java.lang.String(b)
        return "magic = $string\n" +
                "checksum = ${getHexValue(checksum)},value = ${getUUnitValue(checksum)}\n" +
                "signature = $s\n" +
                "file_size = ${getHexValue(file_size)},value = ${getUUnitValue(file_size)}\n" +
                "header_size = ${getHexValue(header_size)},value = ${getUUnitValue(header_size)}\n" +
                "endian_tag = ${getHexValue(endian_tag)},value = ${getUUnitValue(endian_tag)}\n" +
                "link_size = ${getHexValue(link_size)},value = ${getUUnitValue(link_size)}\n" +
                "link_off = ${getHexValue(link_off)},value = ${getUUnitValue(link_off)}\n" +
                "map_off = ${getHexValue(map_off)},value = ${getUUnitValue(map_off)}\n" +
                "string_ids_size = ${getHexValue(string_ids_size)},value = ${getUUnitValue(string_ids_size)}\n" +
                "string_ids_off = ${getHexValue(string_ids_off)},value = ${getUUnitValue(string_ids_off)}\n" +
                "type_ids_size = ${getHexValue(type_ids_size)},value = ${getUUnitValue(type_ids_size)}\n" +
                "type_ids_off = ${getHexValue(type_ids_off)},value = ${getUUnitValue(type_ids_off)}\n" +
                "proto_ids_size = ${getHexValue(proto_ids_size)},value = ${getUUnitValue(proto_ids_size)}\n" +
                "proto_ids_off = ${getHexValue(proto_ids_off)},value = ${getUUnitValue(proto_ids_off)}\n" +
                "field_ids_size = ${getHexValue(field_ids_size)},value = ${getUUnitValue(field_ids_size)}\n" +
                "field_ids_off = ${getHexValue(field_ids_off)},value = ${getUUnitValue(field_ids_off)}\n" +
                "method_ids_size = ${getHexValue(method_ids_size)},value = ${getUUnitValue(method_ids_size)}\n" +
                "method_ids_off = ${getHexValue(method_ids_off)},value = ${getUUnitValue(method_ids_off)}\n" +
                "class_defs_size = ${getHexValue(class_defs_size)},value = ${getUUnitValue(class_defs_size)}\n" +
                "class_defs_off = ${getHexValue(class_defs_off)},value = ${getUUnitValue(class_defs_off)}\n" +
                "data_size = ${getHexValue(data_size)},value = ${getUUnitValue(data_size)}\n" +
                "data_off = ${getHexValue(data_off)},value = ${getUUnitValue(data_off)}\n"
    }
}