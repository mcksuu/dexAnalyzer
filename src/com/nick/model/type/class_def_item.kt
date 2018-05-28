package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class class_def_item(
        var class_idx: uint32_t, //   此类的 type_ids 列表中的索引。此项必须是“类”类型，而不能是“数组”或“基元”类型。
        var access_flags: uint32_t, //   类的访问标记（public 、final 等）。有关详情，请参阅“access_flags 定义”。
        var superclass_idx: uint32_t, //超类的 type_ids 列表中的索引。如果此类没有超类（即它是根类，例如 Object），则该值为常量值 NO_INDEX。如果此类存在超类，则此项必须是“类”类型，而不能是“数组”或“基元”类型。
        var interfaces_off: uint32_t, //从文件开头到接口列表的偏移量；如果没有接口，则该值为 0。该偏移量应该位于 data 区段，且其中的数据应采用下文中“type_list”指定的格式。该列表的每个元素都必须是“类”类型（而不能是“数组”或“基元”类型），并且不得包含任何重复项。
        var source_file_idx: uint32_t, //文件（包含这个类（至少大部分）的原始来源）名称的 string_ids 列表中的索引；或者该值为特殊值 NO_INDEX，以表示缺少这种信息。任何指定方法的 debug_info_item 都可以覆盖此源文件，但预期情况是大多数类只能来自一个源文件。
        var annotations_off: uint32_t, // TODO 从文件开头到此类的注释结构的偏移量；如果此类没有注释，则该值为 0。该偏移量（如果为非零值）应该位于 data 区段，且其中的数据应采用下文中“annotations_directory_item”指定的格式，同时所有项将此类作为定义符进行引用。
        var class_data_off: uint32_t, //从文件开头到此项的关联类数据的偏移量；如果此类没有类数据，则该值为 0（这种情况有可能出现，例如，如果此类是标记接口）。该偏移量（如果为非零值）应该位于 data 区段，且其中的数据应采用下文中“class_data_item”指定的格式，同时所有项将此类作为定义符进行引用。
        var static_values_off: uint32_t    //TODO 从文件开头到 static 字段的初始值列表的偏移量；如果没有该列表（并且所有 static 字段都将使用 0 或 null 进行初始化），则该值为 0。该偏移量应该位于 data 区段，且其中的数据应采用下文中“encoded_array_item”指定的格式。该数组的大小不得超出此类所声明的 static 字段的数量，且 static 字段所对应的元素应采用相对应的 field_list 中所声明的顺序每个数组元素的类型均必须与其相应字段的声明类型相匹配。如果该数组中的元素比 static 字段中的少，则剩余字段将使用适当类型 0 或 null 进行初始化。
) : base_item() {
    var clazz_name = ""
    var access_des = ""
    var superclass = ""
    var interfaces: type_list? = null
    var source_file = ""
    var annotations: annotations_directory_item? = null
    var class_data: class_data_item? = null
//    var static_values:enco
}