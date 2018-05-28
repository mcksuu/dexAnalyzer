package com.nick.model.type

import com.nick.model.unit.*
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by KangShuai on 2017/11/29.
 */

fun read_uint8_t(byteArray: ByteArray, offset: Int): uint8_t {
    return uint8_t(byteArray[offset])
}

fun read_uint16_t(stream: ByteArray, index: Int): uint16_t {
    val byte = ByteArray(2)
    byte[0] = stream[index]
    byte[1] = stream[index + 1]

    val byteBuffer = ByteBuffer.allocate(byte.size)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(byte)
    byteBuffer.flip()
    return uint16_t(byteBuffer.short)
}

fun read_uint32_t(stream: ByteArray, index: Int): uint32_t {
    val byte = ByteArray(4)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }
    val byteBuffer = ByteBuffer.allocate(byte.size)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(byte)
    byteBuffer.flip()
    return uint32_t(byteBuffer.int)
}

fun read_uint32_t_f(stream: ByteArray, index: Int): uint32_t_f {
    val byte = ByteArray(4)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }
    val byteBuffer = ByteBuffer.allocate(byte.size)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(byte)
    byteBuffer.flip()
    return uint32_t_f(byteBuffer.float)
}

fun read_uint64_t(stream: ByteArray, index: Int): uint64_t {
    val byte = ByteArray(8)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }

    val byteBuffer = ByteBuffer.allocate(byte.size)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(byte)
    byteBuffer.flip()
    return uint64_t(byteBuffer.long)
}

fun read_uint64_t_d(stream: ByteArray, index: Int): uint64_t_d {
    val byte = ByteArray(8)
    for (i in 0..byte.size - 1) {
        byte[i] = stream[index + i]
    }

    val byteBuffer = ByteBuffer.allocate(byte.size)
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    byteBuffer.put(byte)
    byteBuffer.flip()
    return uint64_t_d(byteBuffer.double)
}

fun <T, R> getUUnitValue(unit: basic_unit<T, R>): R {
    return unit.getUValue()
}

fun <T, R> getHexValue(unit: basic_unit<T, R>): String {
    return unit.getHexValue()
}

/**
 * Reads an signed integer from `in`.
 */
fun readSignedLeb128(bytes: ByteArray, offset: Int): Pair<Int, Int> {
    var result = 0
    var cur: Int
    var count = 0
    var signBits = -1

    do {
        cur = bytes[offset + count].toInt() and 0x000000ff
        result = result or (cur and 0x7f shl count * 7)
        signBits = signBits shl 7
        count++
    } while ((cur and 0x80) == 0x80 && count < 5)

    if (cur and 0x80 == 0x80) {
        throw Exception("invalid LEB128 sequence")
    }

    // Sign extend if appropriate
    if (signBits shr 1 and result != 0) {
        result = result or signBits
    }

    return Pair(offset + count, result)
}

/**
 * Reads an unsigned integer from `in`.
 */
fun readUnsignedLeb128(bytes: ByteArray, offset: Int): Pair<Int, Int> {
    var result = 0
    var cur: Int
    var count = 0

    do {
        cur = (bytes[offset + count].toInt() and 0xff)
        result = result or (cur and 0x7f shl count * 7)
        count++
    } while (cur and 0x80 == 0x80 && count < 5)

    if (cur and 0x80 == 0x80) {
        throw Exception("invalid LEB128 sequence")
    }

    return Pair(offset + count, result)
}

fun writeSignedLeb128(value: Int): ByteArray {
    var value = value
    var remaining = value shr 7
    var hasMore = true
    val end = if (value and Integer.MIN_VALUE == 0) 0 else -1
    val byteArray = ByteArray(5)
    var pos = 0
    while (hasMore) {
        hasMore = remaining != end || remaining and 1 != value shr 6 and 1

        byteArray[pos] = ((value and 0x7f or if (hasMore) 0x80 else 0).toByte())
        value = remaining
        remaining = remaining shr 7
        pos++
    }

    return byteArray
}

fun coypByte(bytes: ByteArray, offset: Int, size: Int): ByteArray {
    var tempSize = size
    var pos = 0
    while (pos < tempSize) {
        if (bytes[pos + offset].toInt() and 0xE0 == 0xE0) { //三字节
            tempSize += 2
            pos += 3
        } else if (bytes[pos + offset].toInt() and 0xC0 == 0xC0) {
            tempSize += 1
            pos += 2
        } else {
            pos++
        }

    }
    return bytes.copyOfRange(offset, offset + tempSize)
}