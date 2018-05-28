package com.nick.model.type

import com.nick.model.unit.uint32_t

/**
 * Created by KangShuai on 2018/5/18.
 */
class call_site_id_item(
        var call_site_off: uint32_t//从文件开头到调用点定义的偏移量。该偏移量应位于数据区段中，且其中的数据应采用下文中“call_site_item”指定的格式。
) : base_item()