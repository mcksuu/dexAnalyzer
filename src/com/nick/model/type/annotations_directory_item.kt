package com.nick.model.type

import com.nick.model.unit.uint32_t
import com.nick.model.unit.uint8_t

/**
 * Created by KangShuai on 2018/5/24.
 */
class annotations_directory_item(
        var class_annotations_off: uint32_t, //从文件开头到直接在该类上所做的注释的偏移量；如果该类没有任何直接注释，则该值为 0。该偏移量（如果为非零值）应该是到 data 区段中某个位置的偏移量。数据格式由下文的“annotation_set_item”指定。
        var fields_size: uint32_t, //此项所注释的字段数量
        var annotated_methods_size: uint32_t, // 此项所注释的方法数量
        var annotated_parameters_size: uint32_t, //此项所注释的方法参数列表的数量
        var field_annotations: Array<field_annotation>, //[fields_size]（可选）    所关联字段的注释列表。该列表中的元素必须按 field_idx 以升序进行排序。
        var method_annotations: Array<method_annotation>, //[methods_size]（可选）    所关联方法的注释列表。该列表中的元素必须按 method_idx 以升序进行排序。
        var parameter_annotations: Array<parameter_annotation>//[parameters_size]（可选）    所关联方法参数的注释列表。该列表中的元素必须按 method_idx 以升序进行排序。
) {

    override fun toString(): String {
        var field_annotation_str = ""
        field_annotations.forEachIndexed {
            i, item ->
            field_annotation_str += "属性${item.field} 注解 ${item.toString()} \n"
        }
        var method_annotations_str = ""
        method_annotations.forEachIndexed {
            i, item ->
            method_annotations_str += "方法${item.fiel} 注解 $item \n"
        }
        val result = "属性注解数量 ${fields_size.getValue()} \n 属性注解\n $field_annotation_str \n 方法注解数量 ${annotated_methods_size.getValue()} \n 方法注解\n $method_annotations_str"

        return result
    }
}

class field_annotation(
        var field_idx: uint32_t, //    字段（带注释）标识的 field_ids 列表中的索引
        var annotations_off: uint32_t//  从文件开头到该字段的注释列表的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量。数据格式由下文的“annotation_set_item”指定。
) {
    var field = ""
    var annotations: annotation_set_item? = null
    override fun toString(): String {
        return annotations?.toString() as String
    }
}

class method_annotation(
        var method_idx: uint32_t, //方法（带注释）标识的 method_ids 列表中的索引
        var annotations_off: uint32_t//从文件开头到该方法注释列表的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量。数据格式由下文的“annotation_set_item”指定。
) {
    var fiel = ""
    var annotations: annotation_set_item? = null
    override fun toString(): String {
        return annotations?.toString() as String
    }
}

class parameter_annotation(
        var method_idx: uint32_t, //方法（其参数带注释）标识的 method_ids 列表中的索引
        var annotations_off: uint32_t//从文件开头到该方法参数的注释列表的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量。数据格式由下文的“annotation_set_ref_list”指定。
) {
    var fiel = ""
    var annotations: annotation_set_ref_list? = null
}

class annotation_set_ref_list(
        var size: uint32_t, //列表的大小（以条目数表示）
        var list: Array<annotation_set_ref_item> //[size]    列表的元素
) {
    override fun toString(): String {
        return super.toString()
    }
}

class annotation_set_ref_item(
        var annotations_off: uint32_t//从文件开头到所引用注释集的偏移量；如果此元素没有任何注释，则该值为 0。该偏移量（如果为非零值）应该是到 data 区段中某个位置的偏移量。数据格式由下文的“annotation_set_item”指定。
) {
    override fun toString(): String {
        return super.toString()
    }
}

class annotation_set_item(
        var size: uint32_t, //    该集合的大小（以条目数表示）
        var entries: Array<annotation_off_item> //[size]    该集合的元素。这些元素必须按 type_idx 以升序进行排序。
) {
    override fun toString(): String {
        var result = "数量 ${size.getValue()} \n "
        entries.forEach {
            if (!it.toString().isNullOrEmpty()) {
                result += it.toString() + " \n"
            }
        }
        return result
    }
}

class annotation_off_item(
        var annotation_off: uint32_t//    从文件开头到注释的偏移量。该偏移量应该是到 data 区段中某个位置的偏移量，且该位置的数据格式由下文的“annotation_item”指定。
) {
    var annotation_item: annotation_item? = null
    override fun toString(): String {
        return annotation_item?.toString() as String
    }
}

class annotation_item(
        var visibility: uint8_t, //此注释的预期可见性（见下文）
        var annotation: encoded_annotation    //已编码的注释内容；采用上文的“encoded_value 编码”下的“encoded_annotation 格式”所述的格式。
) {
    override fun toString(): String {

        val vis =
                when (visibility.getValue().toInt()) {
                    VISIBILITY_BUILD -> "VISIBILITY_BUILD"
                    VISIBILITY_RUNTIME -> "VISIBILITY_RUNTIME"
                    VISIBILITY_SYSTEM -> "VISIBILITY_SYSTEM"
                    else -> ""
                }

        val result = "注解可见性 $vis \n 注解 $annotation \n"
        return result
    }

    companion object {
        var VISIBILITY_BUILD = 0x00    //预计仅在构建（例如，在编译其他代码期间）时可见
        var VISIBILITY_RUNTIME = 0x01    //预计在运行时可见
        var VISIBILITY_SYSTEM = 0x02    //预计在运行时可见，但仅对基本系统（而不是常规用户代码）可见
    }
}
