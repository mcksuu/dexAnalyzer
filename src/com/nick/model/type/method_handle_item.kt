package com.nick.model.type

import com.nick.model.unit.uint16_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class method_handle_item(
        var method_handle_type: uint16_t, //   方法句柄的类型；见下表
        var unused1: uint16_t, // （未使用）
        var field_or_method_id: uint16_t, //  字段或方法 ID 取决于方法句柄类型是访问器还是方法调用器
        var unused2: uint16_t    //（未使用）
) : base_item() {
    companion object {
        val METHOD_HANDLE_TYPE_STATIC_PUT = 0x00    //方法句柄是静态字段设置器（访问器）
        val METHOD_HANDLE_TYPE_STATIC_GET = 0x01    //方法句柄是静态字段获取器（访问器）
        val METHOD_HANDLE_TYPE_INSTANCE_PUT = 0x02  //  方法句柄是实例字段设置器（访问器）
        val METHOD_HANDLE_TYPE_INSTANCE_GET = 0x03  //  方法句柄是实例字段获取器（访问器）
        val METHOD_HANDLE_TYPE_INVOKE_STATIC = 0x04 //   方法句柄是静态方法调用器
        val METHOD_HANDLE_TYPE_INVOKE_INSTANCE = 0x05 //   方法句柄是实例方法调用器
    }
}