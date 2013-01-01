package com.taroid.brainfxck

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import org.junit.Test as test
import kotlin.test.fail

public class MemoryImplTest {

    test fun 設定したポインタが指す値を取得できるはず() {
        assertEquals(1, MemoryImpl(0, listOf(1, 2, 3)).currentValue)
        assertEquals(2, MemoryImpl(1, listOf(1, 2, 3)).currentValue)
        assertEquals(3, MemoryImpl(2, listOf(1, 2, 3)).currentValue)
    }

    test fun 設定した値を取得できるはず() {
        assertEquals(1, MemoryImpl(0).setValue(1).currentValue)
        assertEquals(1, MemoryImpl(2, listOf(0, 0, 0)).setValue(1).currentValue)
    }

    test fun インクリメントによりポインタは次の値を指すはず() {
        val memory = MemoryImpl(0, listOf(1, 2, 3))
        assertEquals(2, memory.incrementPointer().currentValue)
        assertEquals(3, memory.incrementPointer().incrementPointer().currentValue)
    }

    test fun インクリメントによりメモリは自動的に拡張されるはず() {
        val memory = MemoryImpl(0)
        assertEquals(0, memory.incrementPointer().currentValue)
        assertEquals(0, memory.incrementPointer().incrementPointer().currentValue)
    }

    test fun デクリメントによりポインタは前の値を指すはず() {
        val memory = MemoryImpl(2, listOf(1, 2, 3))
        assertEquals(2, memory.decrementPointer().currentValue)
        assertEquals(1, memory.decrementPointer().decrementPointer().currentValue)
    }

    test(expected = javaClass<IllegalArgumentException>())
    fun ポインタが負数の場合は例外を投げるはず() {
        MemoryImpl(-1)
    }

    test(expected = javaClass<IllegalArgumentException>())
    fun ポインタがメモリの範囲外を指すと例外を投げるはず() {
        MemoryImpl(3, listOf(1, 2, 3))
    }

    test fun インクリメントによりポインタがIntの最大値を超えようとすると例外を投げるはず() {
        try {
            val values = java.util.ArrayList<Int>(Integer.MAX_VALUE)
            MemoryImpl(values.size - 1, values).incrementPointer()
            fail()
        } catch(e : OutOfMemoryError) {
            // OOMは仕方ない
        } catch(e : IllegalArgumentException) {
        }
    }

    test(expected = javaClass<IllegalStateException>())
    fun デクリメントによりポインタが負数になる場合は例外を投げるはず() {
        MemoryImpl(0).decrementPointer()
    }
}