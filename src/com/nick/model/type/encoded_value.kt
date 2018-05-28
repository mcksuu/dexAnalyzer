package com.nick.model.type

import com.nick.model.type.encoded_value.Companion.VALUE_ANNOTATION
import com.nick.model.type.encoded_value.Companion.VALUE_ARRAY
import com.nick.model.type.encoded_value.Companion.VALUE_BOOLEAN
import com.nick.model.type.encoded_value.Companion.VALUE_BYTE
import com.nick.model.type.encoded_value.Companion.VALUE_CHAR
import com.nick.model.type.encoded_value.Companion.VALUE_DOUBLE
import com.nick.model.type.encoded_value.Companion.VALUE_ENUM
import com.nick.model.type.encoded_value.Companion.VALUE_FIELD
import com.nick.model.type.encoded_value.Companion.VALUE_FLOAT
import com.nick.model.type.encoded_value.Companion.VALUE_INT
import com.nick.model.type.encoded_value.Companion.VALUE_LONG
import com.nick.model.type.encoded_value.Companion.VALUE_METHOD
import com.nick.model.type.encoded_value.Companion.VALUE_METHOD_HANDLE
import com.nick.model.type.encoded_value.Companion.VALUE_METHOD_TYPE
import com.nick.model.type.encoded_value.Companion.VALUE_NULL
import com.nick.model.type.encoded_value.Companion.VALUE_SHORT
import com.nick.model.type.encoded_value.Companion.VALUE_STRING
import com.nick.model.type.encoded_value.Companion.VALUE_TYPE
import com.nick.model.unit.uint8_t

/**
 * Created by KangShuai on 2018/5/24.
 */
class encoded_value(
        var value_arg: uint8_t, //(value_arg << 5) | 一种字节，用于表示紧跟后面的 value 及高 3 位中可选澄清参数的类型。请参阅下文，了解各种 value 定义。在大多数情况下，value_arg 会以字节为单位将紧跟后面的 value 的长度编码为 (size - 1)；例如，0 表示该值需要 1 个字节，7 表示该值需要 8 个字节；不过，也存在下述例外情况。
        var value: ByteArray    //用于表示值的字节，不同 value_arg 字节的长度不同且采用不同的解译方式；不过一律采用小端字节序。有关详情，请参阅下文中的各种值定义。
) {
    companion object {
        val VALUE_BYTE = 0x00    //（无；必须为 0） ubyte[1] 有符号的单字节整数值
        val VALUE_SHORT = 0x02    //size - 1 (0…1) ubyte[size] 有符号的双字节整数值，符号扩展
        val VALUE_CHAR = 0x03    //size - 1 (0…1) ubyte[size] 无符号的双字节整数值，零扩展
        val VALUE_INT = 0x04    //size - 1 (0…3) ubyte[size] 有符号的四字节整数值，符号扩展
        val VALUE_LONG = 0x06    //size - 1 (0…7) ubyte[size] 有符号的八字节整数值，符号扩展
        val VALUE_FLOAT = 0x10    //size - 1 (0…3) ubyte[size] 四字节位模式，向右零扩展，系统会将其解译为 IEEE754 32 位浮点值
        val VALUE_DOUBLE = 0x11    //size - 1 (0…7) ubyte[size] 八字节位模式，向右零扩展，系统会将其解译为 IEEE754 64 位浮点值
        val VALUE_METHOD_TYPE = 0x15 //size -1(0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 proto_ids 节的索引；表示方法类型值
        val VALUE_METHOD_HANDLE = 0x16 // -1(0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 method_handles 节的索引；表示方法句柄值
        val VALUE_STRING = 0x17    //size-1 (0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 string_ids 节的索引；表示字符串值
        val VALUE_TYPE = 0x18   //size-1 (0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 type_ids 节的索引；表示反射类型/类值
        val VALUE_FIELD = 0x19  // size-1 (0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 field_ids 节的索引；表示反射字段值
        val VALUE_METHOD = 0x1a //  size-1 (0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 method_ids 节的索引；表示反射方法值
        val VALUE_ENUM = 0x1b   //size-1 (0…3)    ubyte[size]    无符号（零扩展）四字节整数值，会被解译为要编入 field_ids 节的索引；表示枚举类型常量的值
        val VALUE_ARRAY = 0x1c  // （无；必须为 0）    encoded_array    值的数组，采用下文“encoded_array 格式”所指定的格式。value 的大小隐含在编码中。
        val VALUE_ANNOTATION = 0x1d    //（无；必须为 0）    encoded_annotation    子注释，采用下文“encoded_annotation 格式”所指定的格式。value 的大小隐含在编码中。
        val VALUE_NULL = 0x1e    //（无；必须为 0）    （无）    null 引用值
        val VALUE_BOOLEAN = 0x1f //   布尔值 (0…1)    （无）    一位值；0 表示 false，1 表示 true。该位在 value_arg 中有所表示。
    }

    var value_str = null
    var string_ids: Array<string_id_item>? = null
    var proto_ids: Array<proto_id_item>? = null
    var type_ids: Array<type_id_item>? = null
    var field_ids: Array<field_id_item>? = null
    var method_ids: Array<method_id_item>? = null
    val list = ArrayList<string_data_item>()

    override fun toString(): String {
        val value_str = "类型 " + getValueTypeStr(value_arg.getValue()) + " 值 " + when (getValueType(value_arg.getValue())) {
            VALUE_BYTE -> read_uint8_t(value, 0).getValue()
            VALUE_SHORT -> read_uint16_t(value, 0).getValue()
            VALUE_CHAR -> read_uint16_t(value, 0).getValue().toChar()
            VALUE_INT -> read_uint32_t(value, 0).getValue()
            VALUE_LONG -> read_uint64_t(value, 0).getValue()
            VALUE_FLOAT -> read_uint32_t_f(value, 0)
            VALUE_DOUBLE -> read_uint64_t_d(value, 0).getValue()
            VALUE_METHOD_TYPE -> proto_ids!![read_uint32_t(value, 0).getValue()].toString()
            VALUE_METHOD_HANDLE -> value_str
            VALUE_STRING -> {
                var s = ""
                list.forEach(action = {
                    data ->
                    if (data.string_off.getValue() == string_ids!![read_uint32_t(value, 0).getValue()].string_data_off.getValue())
                        s = data.toString()
                    return@forEach
                })
                s
            }
            VALUE_TYPE -> type_ids!![read_uint32_t(value, 0).getValue()].toString()
            VALUE_FIELD -> field_ids!![read_uint32_t(value, 0).getValue()].toString()
            VALUE_METHOD -> method_ids!![read_uint32_t(value, 0).getValue()].toString()
            VALUE_ENUM -> field_ids!![read_uint32_t(value, 0).getValue()].toString()
            VALUE_ARRAY -> ""
            VALUE_ANNOTATION -> ""
            VALUE_NULL -> "NULL"
            VALUE_BOOLEAN -> value_arg.getValue().toInt() ushr 5

            else -> ""
        }
        return value_str
    }
}

fun getValueSize(value_arg: Byte): Int {
    return when (getValueType(value_arg)) {
        VALUE_BYTE -> 1
        VALUE_SHORT -> value_arg.toInt() ushr 5 + 1
        VALUE_CHAR -> value_arg.toInt() ushr 5 + 1
        VALUE_INT -> value_arg.toInt() ushr 5 + 1
        VALUE_LONG -> value_arg.toInt() ushr 5 + 1
        VALUE_FLOAT -> value_arg.toInt() ushr 5 + 1
        VALUE_DOUBLE -> value_arg.toInt() ushr 5 + 1
        VALUE_METHOD_TYPE -> value_arg.toInt() ushr 5 + 1
        VALUE_METHOD_HANDLE -> value_arg.toInt() ushr 5 + 1
        VALUE_STRING -> value_arg.toInt() ushr 5 + 1
        VALUE_TYPE -> value_arg.toInt() ushr 5 + 1
        VALUE_FIELD -> value_arg.toInt() ushr 5 + 1
        VALUE_METHOD -> value_arg.toInt() ushr 5 + 1
        VALUE_ENUM -> value_arg.toInt() ushr 5 + 1
        VALUE_ARRAY -> 0
        VALUE_ANNOTATION -> 0
        VALUE_NULL -> 0
        VALUE_BOOLEAN -> 0
        else -> 0
    }
//    var result = 0
//    if (value_arg.toInt() and VALUE_BYTE == VALUE_BYTE)
//        result = 1
//    else if (value_arg.toInt() and VALUE_SHORT == VALUE_SHORT)

}

fun getValueType(value_arg: Byte): Int {
    val value_type = value_arg.toInt()
    return when {
        value_type and VALUE_BYTE == VALUE_BYTE -> VALUE_BYTE
        value_type and VALUE_SHORT == VALUE_SHORT -> VALUE_SHORT
        value_type and VALUE_CHAR == VALUE_CHAR -> VALUE_CHAR
        value_type and VALUE_INT == VALUE_INT -> VALUE_INT
        value_type and VALUE_LONG == VALUE_LONG -> VALUE_LONG
        value_type and VALUE_FLOAT == VALUE_FLOAT -> VALUE_FLOAT
        value_type and VALUE_DOUBLE == VALUE_DOUBLE -> VALUE_DOUBLE
        value_type and VALUE_METHOD_TYPE == VALUE_METHOD_TYPE -> VALUE_METHOD_TYPE
        value_type and VALUE_METHOD_HANDLE == VALUE_METHOD_HANDLE -> VALUE_METHOD_HANDLE
        value_type and VALUE_STRING == VALUE_STRING -> VALUE_STRING
        value_type and VALUE_TYPE == VALUE_TYPE -> VALUE_TYPE
        value_type and VALUE_FIELD == VALUE_FIELD -> VALUE_FIELD
        value_type and VALUE_METHOD == VALUE_METHOD -> VALUE_METHOD
        value_type and VALUE_ENUM == VALUE_ENUM -> VALUE_ENUM
        value_type and VALUE_ARRAY == VALUE_ARRAY -> VALUE_ARRAY
        value_type and VALUE_ANNOTATION == VALUE_ANNOTATION -> VALUE_ANNOTATION
        value_type and VALUE_NULL == VALUE_NULL -> VALUE_NULL
        value_type and VALUE_BOOLEAN == VALUE_BOOLEAN -> VALUE_BOOLEAN
        else -> 0
    }
}

fun getValueTypeStr(value_arg: Byte): String {
    val value_type = value_arg.toInt()
    return when {
        value_type and VALUE_BYTE == VALUE_BYTE -> "VALUE_BYTE"
        value_type and VALUE_SHORT == VALUE_SHORT -> "VALUE_SHORT"
        value_type and VALUE_CHAR == VALUE_CHAR -> "VALUE_CHAR"
        value_type and VALUE_INT == VALUE_INT -> "VALUE_INT"
        value_type and VALUE_LONG == VALUE_LONG -> "VALUE_LONG"
        value_type and VALUE_FLOAT == VALUE_FLOAT -> "VALUE_FLOAT"
        value_type and VALUE_DOUBLE == VALUE_DOUBLE -> "VALUE_DOUBLE"
        value_type and VALUE_METHOD_TYPE == VALUE_METHOD_TYPE -> "VALUE_METHOD_TYPE"
        value_type and VALUE_METHOD_HANDLE == VALUE_METHOD_HANDLE -> "VALUE_METHOD_HANDLE"
        value_type and VALUE_STRING == VALUE_STRING -> "VALUE_STRING"
        value_type and VALUE_TYPE == VALUE_TYPE -> "VALUE_TYPE"
        value_type and VALUE_FIELD == VALUE_FIELD -> "VALUE_FIELD"
        value_type and VALUE_METHOD == VALUE_METHOD -> "VALUE_METHOD"
        value_type and VALUE_ENUM == VALUE_ENUM -> "VALUE_ENUM"
        value_type and VALUE_ARRAY == VALUE_ARRAY -> "VALUE_ARRAY"
        value_type and VALUE_ANNOTATION == VALUE_ANNOTATION -> "VALUE_ANNOTATION"
        value_type and VALUE_NULL == VALUE_NULL -> "VALUE_NULL"
        value_type and VALUE_BOOLEAN == VALUE_BOOLEAN -> "VALUE_BOOLEAN"
        else -> ""
    }
}

