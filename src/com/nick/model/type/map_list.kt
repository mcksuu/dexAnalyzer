package com.nick.model.type

import com.nick.model.unit.uint16_t
import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/21.
 */
class map_list(
        var size: uint32_t,
        var list: Array<map_item>
) : base_item() {
}

class map_item(
        var type: uint16_t,
        var unused: uint16_t,
        var size: uint32_t,
        var offset: uint32_t
) : base_item() {
    companion object {
        val UNDEFINE_SIZE = 0x00
        val CALCULATE_SIZE = 0x10
        val header_item = Pair(0x0000, 0x70)// TYPE_HEADER_ITEM
        val string_id_item = Pair(0x0001, 0x04)// TYPE_STRING_ID_ITEM 0x0001 0x04
        val type_id_item = Pair(0x0002, 0x04)//TYPE_TYPE_ID_ITEM 0x0002 0x04
        val proto_id_item = Pair(0x0003, 0x0c)// TYPE_PROTO_ID_ITEM 0x0003 0x0c
        val field_id_item = Pair(0x0004, 0x08)// TYPE_FIELD_ID_ITEM 0x0004 0x08
        val method_id_item = Pair(0x0005, 0x08)// TYPE_METHOD_ID_ITEM 0x0005 0x08
        val class_def_item = Pair(0x0006, 0x20)// TYPE_CLASS_DEF_ITEM 0x0006 0x20
        val call_site_id_item = Pair(0x0007, 0x04)// TYPE_CALL_SITE_ID_ITEM 0x0007 0x04
        val method_handle_item = Pair(0x0008, 0x08)// TYPE_METHOD_HANDLE_ITEM 0x0008 0x08
        val map_list = Pair(0x1000, 0x70)//TYPE_MAP_LIST 0x1000 4 + (item.size * 12)
        val type_list = Pair(0x1001, 0x70)// TYPE_TYPE_LIST 0x1001 4 + (item.size * 2)
        val annotation_set_ref_list = Pair(0x1002, CALCULATE_SIZE)// TYPE_ANNOTATION_SET_REF_LIST 0x1002 4 + (item.size * 4)
        val annotation_set_item = Pair(0x1003, CALCULATE_SIZE)//TYPE_ANNOTATION_SET_ITEM 0x1003 4 + (item.size * 4)
        val class_data_item = Pair(0x2000, UNDEFINE_SIZE)//TYPE_CLASS_DATA_ITEM 0x2000 隐式；必须解析
        val code_item = Pair(0x2001, UNDEFINE_SIZE)//TYPE_CODE_ITEM 0x2001 隐式；必须解析
        val string_data_item = Pair(0x2002, UNDEFINE_SIZE)// TYPE_STRING_DATA_ITEM 0x2002 隐式；必须解析
        val debug_info_item = Pair(0x2003, UNDEFINE_SIZE)//TYPE_DEBUG_INFO_ITEM 0x2003 隐式；必须解析
        val annotation_item = Pair(0x2004, UNDEFINE_SIZE)// TYPE_ANNOTATION_ITEM 0x2004 隐式；必须解析
        val encoded_array_item = Pair(0x2005, UNDEFINE_SIZE)//TYPE_ENCODED_ARRAY_ITEM 0x2005 隐式；必须解析
        val annotations_directory_item = Pair(0x2006, UNDEFINE_SIZE)// TYPE_ANNOTATIONS_DIRECTORY_ITEM 0x2006 隐式；必须解析


    }
}
