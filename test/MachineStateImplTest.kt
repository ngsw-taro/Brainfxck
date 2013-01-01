package com.taroid.brainfxck

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import org.junit.Test as test

public class MachineStateImplTest {

    test fun 設定したプログラムを取得できるはず() {
        assertEquals("", MachineStateImpl("").program)
        assertEquals("hoge", MachineStateImpl("hoge").program)
        assertEquals("ほげ", MachineStateImpl("ほげ", 2).program)
    }

    test fun 設定したPCが指すコードを取得できるはず() {
        assertEquals('a', MachineStateImpl("abc").currentProgramCode)
        assertEquals('b', MachineStateImpl("abc", 1).currentProgramCode)
        assertEquals('c', MachineStateImpl("abc", 2).currentProgramCode)
    }

    test fun PCをインクリメントすると次のコードを指すはず() {
        assertEquals('b', MachineStateImpl("abc").incrementProgramCounter().currentProgramCode)
        assertEquals('c', MachineStateImpl("abc", 1).incrementProgramCounter().currentProgramCode)
        assertEquals('c', MachineStateImpl("abc").incrementProgramCounter().incrementProgramCounter().currentProgramCode)
    }

    test fun PCをデクリメントすると前のコードを指すはず() {
        assertEquals('a', MachineStateImpl("abc", 1).decrementProgramCounter().currentProgramCode)
        assertEquals('b', MachineStateImpl("abc", 2).decrementProgramCounter().currentProgramCode)
        assertEquals('a', MachineStateImpl("abc", 2).decrementProgramCounter().decrementProgramCounter().currentProgramCode)
    }

    test fun PCの指す先がプログラムの終端であるかを適切に示すはず() {
        assertFalse(MachineStateImpl("abc").isEndOfProgram())
        assertFalse(MachineStateImpl("abc", 2).isEndOfProgram())
        assertTrue(MachineStateImpl("abc", 3).isEndOfProgram())

        val state = MachineStateImpl("hogefuga", 7)
        assertFalse(state.isEndOfProgram())
        assertTrue(state.incrementProgramCounter().isEndOfProgram())
    }

    test(expected = javaClass<IllegalArgumentException>())
    fun PCが負数の場合は例外を投げるはず() {
        MachineStateImpl("abc", -1)
    }

    test(expected = javaClass<IllegalArgumentException>())
    fun PCがプログラム長を超える場合は例外を投げるはず() {
        MachineStateImpl("abc", 4)
    }

    test(expected = javaClass<IllegalStateException>())
    fun PCが負数になってしまうようなデクリメントを行おうとすると例外を投げるはず() {
        MachineStateImpl("abc").decrementProgramCounter()
    }

    test(expected = javaClass<IllegalStateException>())
    fun PCがプログラム長を超えてしまうようなインクリメントを行うおうとすると例外をなげるはず() {
        MachineStateImpl("abc", 3).incrementProgramCounter()
    }
}