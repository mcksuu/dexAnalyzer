package com.nick

import com.nick.model.type.*
import com.nick.model.unit.uint32_t
import com.nick.model.unit.uleb128
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by KangShuai on 2018/5/18.
 */

var string_ids: Array<string_id_item>? = null
var proto_ids: Array<proto_id_item>? = null
var type_ids: Array<type_id_item>? = null
var field_ids: Array<field_id_item>? = null
var method_ids: Array<method_id_item>? = null
val list = ArrayList<string_data_item>()

fun main(args: Array<String>) {
    // TODO 路径需要修改！！
    val stream = File(File("").absolutePath + "//..//dexAnalyzer//src//com//nick//classes.dex").inputStream()
    val os = ByteArrayOutputStream()
    var bytes = ByteArray(1024)
    var len = stream.read(bytes)
    while (len != -1) {
        os.write(bytes, 0, len)
        len = stream.read(bytes)
    }
    // 将文件转成二进制字节数组操作
    bytes = os.toByteArray()
    // 创建header_item
    val header_item = header_item(
            Array(8, { i -> read_uint8_t(bytes, i) }), // 1
            read_uint32_t(bytes, 8), // 4
            kotlin.ByteArray(20, { // 20
                b ->
                bytes[b + 12]
            }),
            read_uint32_t(bytes, 32), // 4
            read_uint32_t(bytes, 32 + 4), // 4
            read_uint32_t(bytes, 32 + 4 * 2), // 4
            read_uint32_t(bytes, 32 + 4 * 3), // 4
            read_uint32_t(bytes, 32 + 4 * 4), // 4
            read_uint32_t(bytes, 32 + 4 * 5), // 4
            read_uint32_t(bytes, 32 + 4 * 6), // 4
            read_uint32_t(bytes, 32 + 4 * 7), // 4
            read_uint32_t(bytes, 32 + 4 * 8), // 4
            read_uint32_t(bytes, 32 + 4 * 9), // 4
            read_uint32_t(bytes, 32 + 4 * 10), // 4
            read_uint32_t(bytes, 32 + 4 * 11), // 4
            read_uint32_t(bytes, 32 + 4 * 12), // 4
            read_uint32_t(bytes, 32 + 4 * 13), // 4
            read_uint32_t(bytes, 32 + 4 * 14), // 4
            read_uint32_t(bytes, 32 + 4 * 15), // 4
            read_uint32_t(bytes, 32 + 4 * 16), // 4
            read_uint32_t(bytes, 32 + 4 * 17), // 4
            read_uint32_t(bytes, 32 + 4 * 18), // 4
            read_uint32_t(bytes, 32 + 4 * 19) // 4
    )
    println(header_item)

    // 偏移量
    var offset = header_item.header_size.getValue()
    // 字符串偏移量的数组
    string_ids = Array(header_item.string_ids_size.getValue(), {
        i ->
        string_id_item(read_uint32_t(bytes, offset + i * 4))
    })

//    string_ids?.forEach { println(it) }
    // 字符串数组数组
    string_ids?.forEach {
        val value = it.string_data_off.getValue()
        val unsignedLeb128 = readUnsignedLeb128(bytes, value)

        val string_data_item = string_data_item(it.string_data_off, uleb128(unsignedLeb128.second), coypByte(bytes, unsignedLeb128.first, unsignedLeb128.second))
        list.add(string_data_item)
        println(string_data_item)
    }

    offset = header_item.type_ids_off.getValue()
    type_ids = Array(header_item.type_ids_size.getValue(), {
        i ->
        type_id_item(read_uint32_t(bytes, offset + i * 4))
    })

    type_ids!!.forEachIndexed { i, item ->
        //        println(list.get(item.descriptor_idx.getValue()))
        item.type = list.get(item.descriptor_idx.getValue()).toString()
    }

    offset = header_item.proto_ids_off.getValue()
    // 方法原型标识符列表偏移量
    proto_ids = Array<proto_id_item>(header_item.proto_ids_size.getValue(), {
        i ->
        val proto_id_item = proto_id_item(read_uint32_t(bytes, offset + (i * 12)), read_uint32_t(bytes, offset + (i * 12) + 4), read_uint32_t(bytes, offset + (i * 12) + 8))

        proto_id_item.strings = list[proto_id_item.shorty_idx.getValue()].toString()
        proto_id_item.return_type = list[proto_id_item.return_type_idx.getValue()].toString()

        val value = proto_id_item.parameters_off.getValue()
        if (value > 0) {
            val size = read_uint32_t(bytes, value)
            proto_id_item.type_list = type_list(size, Array<type_item>(size.getValue(), {
                i ->
                type_item(read_uint16_t(bytes, value + i * 2 + 4))
            }))
        }
        proto_id_item
    })

    // 方法原型标识符列表数据
    proto_ids!!.forEachIndexed { i, item ->
        val type_id_item = type_ids!![item.return_type_idx.getValue()]
        item.type_list?.list?.forEach { println(list.get(type_ids!![it.type_idx.getValue().toInt()].descriptor_idx.getValue()).toString()) }
        println(list[item.shorty_idx.getValue()].toString() + " " + list[type_id_item.descriptor_idx.getValue()].toString())
    }

    // 属性偏移量和数据
    offset = header_item.field_ids_off.getValue()
    field_ids = Array(header_item.field_ids_size.getValue(), {
        i ->
        field_id_item(read_uint16_t(bytes, offset + i * 8), read_uint16_t(bytes, offset + i * 8 + 2), read_uint32_t(bytes, offset + i * 8 + 4))
    })
    field_ids!!.forEachIndexed { i, item ->
        item.type = type_ids!![item.type_idx.getValue().toInt()].type
        item.clazz = type_ids!![item.class_idx.getValue().toInt()].type
        item.name = list[item.name_idx.getValue()].toString()
    }

    // 方法偏移量和数据
    offset = header_item.method_ids_off.getValue()
    method_ids = Array(header_item.method_ids_size.getValue(), {
        i ->
        method_id_item(read_uint16_t(bytes, offset + i * 8), read_uint16_t(bytes, offset + 2 + i * 8), read_uint32_t(bytes, offset + 4 + i * 8))
    })

    method_ids!!.forEach {
        it.clazz = list[type_ids!![it.class_idx.getValue().toInt()].descriptor_idx.getValue()].toString()
        it.proto_list = proto_ids!![it.proto_idx.getValue().toInt()].type_list/*.list?.forEach { println(list.get(type_ids[it.type_idx.getValue().toInt()].descriptor_idx.getValue()).toString()) }*/
        it.name = list[it.name_idx.getValue()].toString()
    }

    // 类定义偏移量和数据，这里最麻烦了！！
    offset = header_item.class_defs_off.getValue()
    val class_defs = Array(header_item.class_defs_size.getValue(), {
        i ->
        val class_def_item = class_def_item(
                read_uint32_t(bytes, offset + i * 32),
                read_uint32_t(bytes, offset + i * 32 + 4 * 1),
                read_uint32_t(bytes, offset + i * 32 + 4 * 2),
                read_uint32_t(bytes, offset + i * 32 + 4 * 3),
                read_uint32_t(bytes, offset + i * 32 + 4 * 4),
                read_uint32_t(bytes, offset + i * 32 + 4 * 5),
                read_uint32_t(bytes, offset + i * 32 + 4 * 6),
                read_uint32_t(bytes, offset + i * 32 + 4 * 7)
        )

        class_def_item.clazz_name = type_ids!![class_def_item.class_idx.getValue()].type
        class_def_item.access_des = access_flags(class_def_item.access_flags.getValue()).toString()
        class_def_item.superclass = type_ids!![class_def_item.superclass_idx.getValue()].type

        if (class_def_item.source_file_idx.getValue() == -1) {
            class_def_item.source_file = "NO_INDEX"
        } else {
            class_def_item.source_file = list[class_def_item.source_file_idx.getValue()].toString()
        }


        if (class_def_item.interfaces_off.getValue() != 0) {
            val size = read_uint32_t(bytes, class_def_item.interfaces_off.getValue())
            class_def_item.interfaces = type_list(
                    size,
                    Array(size.getValue(), {
                        i ->
                        val type_item = type_item(read_uint16_t(bytes, class_def_item.interfaces_off.getValue() + i * 2 + 4))
                        type_item.type = type_ids!![type_item.type_idx.getValue().toInt()].type
                        type_item
                    }))
        }

        if (class_def_item.annotations_off.getValue() != 0) {
            createAnnotations(class_def_item, field_ids!!, bytes)
        }

        if (class_def_item.class_data_off.getValue() > 0) {
            val static_fields_size = readUnsignedLeb128(bytes, class_def_item.class_data_off.getValue())
            val instance_fields_size = readUnsignedLeb128(bytes, static_fields_size.first)
            val direct_methods_size = readUnsignedLeb128(bytes, instance_fields_size.first)
            val virtual_methods_size = readUnsignedLeb128(bytes, direct_methods_size.first)

            var offset1 = virtual_methods_size.first

            class_def_item.class_data = class_data_item(
                    uleb128(static_fields_size.second),
                    uleb128(instance_fields_size.second),
                    uleb128(direct_methods_size.second),
                    uleb128(virtual_methods_size.second),
                    Array(static_fields_size.second, {
                        i ->
                        val field_idx_diff = readUnsignedLeb128(bytes, offset1)
                        offset1 = field_idx_diff.first
                        val access_flags = readUnsignedLeb128(bytes, offset1)
                        offset1 = access_flags.first
                        val encoded_field = encoded_field(
                                uleb128(field_idx_diff.second),
                                uleb128(access_flags.second))

                        encoded_field.access = access_flags(encoded_field.access_flags.getValue()).toString()
                        encoded_field
                    }),
                    Array(instance_fields_size.second, {
                        i ->
                        val field_idx_diff2 = readUnsignedLeb128(bytes, offset1)
                        offset1 = field_idx_diff2.first
                        val access_flags2 = readUnsignedLeb128(bytes, offset1)
                        offset1 = access_flags2.first
                        encoded_field(
                                uleb128(field_idx_diff2.second),
                                uleb128(access_flags2.second))
                    }),
                    Array<encoded_method>(direct_methods_size.second, {
                        i ->

                        val method_idx_diff = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_idx_diff.first
                        val method_access_flags = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_access_flags.first
                        val method_code_off = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_code_off.first

                        encoded_method(
                                uleb128(method_idx_diff.second),
                                uleb128(method_access_flags.second),
                                uleb128(method_code_off.second)
                        )
                    }),
                    Array<encoded_method>(virtual_methods_size.second, {
                        i ->
                        val method_idx_diff = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_idx_diff.first
                        val method_access_flags = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_access_flags.first
                        val method_code_off = readUnsignedLeb128(bytes, offset1)
                        offset1 = method_code_off.first
                        encoded_method(
                                uleb128(method_idx_diff.second),
                                uleb128(method_access_flags.second),
                                uleb128(method_code_off.second)
                        )

                    }))

            val static_fields = class_def_item.class_data?.static_fields
            static_fields?.forEachIndexed {
                i, item ->
                if (i == 0) {
                    item.field_off = uleb128(item.field_idx_diff.getValue())
                } else {
                    item.field_off = uleb128(static_fields[i - 1].field_off.getValue() + item.field_idx_diff.getValue())
                }

                item.field = field_ids!![item.field_off.getValue()].name
                item.access = access_flags(item.access_flags.getValue()).toString()
            }


            val instance_fields = class_def_item.class_data?.instance_fields
            instance_fields?.forEachIndexed {
                i, item ->
                if (i == 0) {
                    item.field_off = uleb128(item.field_idx_diff.getValue())
                } else {
                    item.field_off = uleb128(instance_fields[i - 1].field_off.getValue() + item.field_idx_diff.getValue())
                }

                item.field = field_ids!![item.field_off.getValue()].name
                item.access = access_flags(item.access_flags.getValue()).toString()
            }

            val direct_methods = class_def_item.class_data?.direct_methods
            direct_methods?.forEachIndexed {
                i, item ->
                if (i == 0) {
                    item.method_off = uleb128(item.method_idx_diff.getValue())
                } else {
                    item.method_off = uleb128(direct_methods[i - 1].method_off.getValue() + item.method_idx_diff.getValue())
                }
                item.method = method_ids!![item.method_off.getValue()].name
                item.access = access_flags(item.access_flags.getValue()).toString()
            }

            val virtual_methods = class_def_item.class_data?.virtual_methods
            virtual_methods?.forEachIndexed {
                i, item ->
                if (i == 0) {
                    item.method_off = uleb128(item.method_idx_diff.getValue())
                } else {
                    item.method_off = uleb128(virtual_methods[i - 1].method_off.getValue() + item.method_idx_diff.getValue())
                }
                item.method = method_ids!![item.method_off.getValue()].name
                item.access = access_flags(item.access_flags.getValue()).toString()
            }
        }
        class_def_item

    })

    class_defs.forEach {
        println("类名 ${it.clazz_name}")
        println("父类 ${it.superclass}")
        println("接口 ${it.interfaces.toString()}")
        println("访问修饰符 ${it.access_des}")
        println("文件 ${it.source_file}")
        println("Class data ${it.class_data.toString()}")
        println("annotations \n${it.annotations.toString()}")
    }

}


private fun createAnnotations(class_def_item: class_def_item, field_ids: Array<field_id_item>, bytes: ByteArray) {
    var annotations_offset = class_def_item.annotations_off.getValue()

    if (annotations_offset > 0) {
        val class_annotations_off = read_uint32_t(bytes, annotations_offset)
        if (class_annotations_off.getValue() > 0) {
            annotations_offset += 4
            val fields_size = read_uint32_t(bytes, annotations_offset)
            annotations_offset += 4
            val annotated_methods_size = read_uint32_t(bytes, annotations_offset)
            annotations_offset += 4
            val annotated_parameters_size = read_uint32_t(bytes, annotations_offset)
            annotations_offset += 4

            class_def_item.annotations = annotations_directory_item(
                    class_annotations_off,
                    fields_size,
                    annotated_methods_size,
                    annotated_parameters_size,
                    Array(fields_size.getValue(), {
                        i ->
                        val field_annotation = field_annotation(
                                read_uint32_t(bytes, annotations_offset),
                                read_uint32_t(bytes, annotations_offset + 4))
                        annotations_offset += 8

                        field_annotation.field = field_ids[field_annotation.field_idx.getValue()].name

                        var field_temp_off = field_annotation.annotations_off.getValue()
                        val size = read_uint32_t(bytes, field_annotation.annotations_off.getValue())
                        field_temp_off += 4
                        field_annotation.annotations = annotation_set_item(
                                size,
                                Array(size.getValue(), {
                                    i ->
                                    val annotation_off_item = annotation_off_item(read_uint32_t(bytes, field_temp_off))
                                    field_temp_off += 4
                                    var temp_offset = annotation_off_item.annotation_off.getValue()
                                    val visibility = read_uint8_t(bytes, temp_offset)
                                    temp_offset++
                                    val type_idx = readUnsignedLeb128(bytes, temp_offset)
                                    temp_offset = type_idx.first
                                    val encoded_annotation_size = readUnsignedLeb128(bytes, temp_offset)
                                    temp_offset = encoded_annotation_size.first
                                    annotation_off_item.annotation_item = annotation_item(
                                            visibility,
                                            encoded_annotation(
                                                    uleb128(type_idx.second),
                                                    uleb128(encoded_annotation_size.second),
                                                    Array(encoded_annotation_size.second, {
                                                        i ->

                                                        val name_idx = readUnsignedLeb128(bytes, temp_offset)
                                                        temp_offset = name_idx.first
                                                        val value_arg = read_uint8_t(bytes, temp_offset)
                                                        temp_offset += 1
                                                        val valueSize = getValueSize(value_arg.getValue())
                                                        val encoded_value = encoded_value(
                                                                value_arg,
                                                                bytes.copyOfRange(temp_offset, temp_offset + valueSize)
                                                        )
                                                        encoded_value.string_ids = string_ids!!
                                                        encoded_value.proto_ids = proto_ids!!
                                                        encoded_value.type_ids = type_ids!!
                                                        encoded_value.field_ids = com.nick.field_ids!!
                                                        encoded_value.method_ids = method_ids!!
                                                        encoded_value.list.addAll(list)
                                                        temp_offset += valueSize
                                                        annotation_element(
                                                                uleb128(name_idx.second),
                                                                encoded_value
                                                        )

                                                    })

                                            )
                                    )

                                    annotation_off_item.annotation_item?.annotation?.type = type_ids!![annotation_off_item.annotation_item?.annotation?.type_idx!!.getValue()].type
                                    annotation_off_item
                                })
                        )

                        field_annotation
                    }),
                    Array(annotated_methods_size.getValue(), {
                        i ->
                        val method_annotation = method_annotation(
                                read_uint32_t(bytes, annotations_offset),
                                read_uint32_t(bytes, annotations_offset + 4)
                        )
                        annotations_offset += 8

                        var field_temp_off = method_annotation.annotations_off.getValue()
                        val size = read_uint32_t(bytes, method_annotation.annotations_off.getValue())
                        field_temp_off += 4
                        method_annotation.annotations = annotation_set_item(
                                size,
                                Array(size.getValue(), {
                                    i ->
                                    val annotation_off_item = annotation_off_item(read_uint32_t(bytes, field_temp_off))
                                    field_temp_off += 4
                                    var temp_offset = annotation_off_item.annotation_off.getValue()
                                    val visibility = read_uint8_t(bytes, temp_offset)
                                    temp_offset++
                                    val type_idx = readUnsignedLeb128(bytes, temp_offset)
                                    temp_offset = type_idx.first
                                    val encoded_annotation_size = readUnsignedLeb128(bytes, temp_offset)
                                    temp_offset = encoded_annotation_size.first
                                    annotation_off_item.annotation_item = annotation_item(
                                            visibility,
                                            encoded_annotation(
                                                    uleb128(type_idx.second),
                                                    uleb128(encoded_annotation_size.second),
                                                    Array(encoded_annotation_size.second, {
                                                        i ->

                                                        val name_idx = readUnsignedLeb128(bytes, temp_offset)
                                                        temp_offset = name_idx.first
                                                        val value_arg = read_uint8_t(bytes, temp_offset)
                                                        temp_offset += 1
                                                        val valueSize = getValueSize(value_arg.getValue())
                                                        val encoded_value = encoded_value(
                                                                value_arg,
                                                                bytes.copyOfRange(temp_offset, temp_offset + valueSize)
                                                        )
                                                        encoded_value.string_ids = string_ids!!
                                                        encoded_value.proto_ids = proto_ids!!
                                                        encoded_value.type_ids = type_ids!!
                                                        encoded_value.field_ids = com.nick.field_ids!!
                                                        encoded_value.method_ids = method_ids!!
                                                        encoded_value.list.addAll(list)
                                                        temp_offset += valueSize
                                                        annotation_element(
                                                                uleb128(name_idx.second),
                                                                encoded_value
                                                        )

                                                    })

                                            )
                                    )

                                    annotation_off_item.annotation_item?.annotation?.type = type_ids!![annotation_off_item.annotation_item?.annotation?.type_idx!!.getValue()].type
                                    annotation_off_item
                                })
                        )
                        method_annotation.fiel = method_ids!![method_annotation.method_idx.getValue()].name
                        method_annotation
                    }),
                    Array(annotated_parameters_size.getValue(), {
                        i ->
                        val parameter_annotation = parameter_annotation(
                                read_uint32_t(bytes, annotations_offset),
                                read_uint32_t(bytes, annotations_offset + 4)
                        )
                        annotations_offset += 8
                        parameter_annotation
                    })
            )
        }
    }
}
