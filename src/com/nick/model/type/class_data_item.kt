package com.nick.model.type

import com.nick.model.unit.uleb128

/**
 * Created by KangShuai on 2018/5/21.
 */
class class_data_item(
        var static_fields_size: uleb128, // 此项中定义的静态字段的数量
        var instance_fields_size: uleb128, //   此项中定义的实例字段的数量
        var direct_methods_size: uleb128, //  此项中定义的直接方法的数量
        var virtual_methods_size: uleb128, //   此项中定义的虚拟方法的数量
        var static_fields: Array<encoded_field>, //    定义的静态字段；以一系列编码元素的形式表示。这些字段必须按 field_idx 以升序进行排序。
        var instance_fields: Array<encoded_field>, //  定义的实例字段；以一系列编码元素的形式表示。这些字段必须按 field_idx 以升序进行排序。
        var direct_methods: Array<encoded_method>, // 定义的直接（static、private 或构造函数的任何一个）方法；以一系列编码元素的形式表示。这些方法必须按 method_idx 以升序进行排序。
        var virtual_methods: Array<encoded_method> //   定义的虚拟（非 static、private 或构造函数）方法；以一系列编码元素的形式表示。此列表不得包括继承方法，除非被此项所表示的类覆盖。这些方法必须按 method_idx 以升序进行排序。虚拟方法的 method_idx 不得与任何直接方法相同。
) : base_item() {

    override fun toString(): String {
        var result = "静态字段的数量 ${static_fields_size.getValue()}\n" +
                "实例字段的数量 ${instance_fields_size.getValue()}\n" +
                "直接方法的数量 ${direct_methods_size.getValue()}\n" +
                "虚拟方法的数量 ${virtual_methods_size.getValue()}\n"
        result += "静态字段: \n"
        static_fields.forEachIndexed {
            i, item ->
            result += " 字段名称 ${item.field}  访问权限 ${item.access} \n"
        }

        result += "实例字段: \n"
        instance_fields.forEachIndexed {
            i, item ->
            result += " 字段名称 ${item.field}  访问权限 ${item.access} \n"
        }

        result += "直接方法: \n"
        direct_methods.forEachIndexed {
            i, item ->
            result += " 字段名称 ${item.method}  访问权限 ${item.access} \n"
        }

        result += "虚拟方法: \n"
        virtual_methods.forEachIndexed {
            i, item ->
            result += " 字段名称 ${item.method}  访问权限 ${item.access} \n"
        }

        return result
    }
}