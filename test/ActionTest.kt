package com.taroid.brainfxck

import java.io.InputStream
import java.io.OutputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test as test

public class ActionTest {

    val machine = {
        val memory = MemoryImpl(1, listOf(1, 23, 456))
        createMachine("").copy(memory = memory)
    }()

    test fun 指定した文字に対応するアクションがない場合は何もしないアクションが取得できるはず() {
        val action = getAction('#')
        assertEquals(machine, action(machine))
    }

    test fun ポインタをインクリメントするはず() {
        val result = getAction(INCREMENT_POINTER)(machine)
        assertEquals(456, result.memory.currentValue)
    }

    test fun ポインタをデクリメントするはず() {
        val result = getAction(DECREMENT_POINTER)(machine)
        assertEquals(1, result.memory.currentValue)
    }

    test fun ポインタが指す値をインクリメントするはず() {
        val result = getAction(INCREMENT_VALUE)(machine)
        assertEquals(24, result.memory.currentValue)
    }

    test fun ポインタが指す値をデクリメントするはず() {
        val result = getAction(DECREMENT_VALUE)(machine)
        assertEquals(22, result.memory.currentValue)
    }

    test fun ポインタが指す先へ入力するはず() {
        val inputStream = object : InputStream() {
            override fun read() : Int = 7890
        }

        // テストのために入力元を切り替え
        val testMachine = machine.copy(inputStream = inputStream)
        val result = getAction(INPUT)(testMachine)
        assertEquals(7890, result.memory.currentValue)
    }

    test fun ポインタが指す値を出力するはず() {
        val outputStream = object : OutputStream() {
            override fun write(b : Int) {
                assertEquals(23, b)
            }
        }

        // テストのために出力先を切り替え
        val testMachine = machine.copy(outputStream = outputStream)
        val result = getAction(OUTPUT)(testMachine)
        assertEquals(testMachine, result)
    }

    val machineForLoopStart = {
        val state = MachineStateImpl("+[-].", 1)
        val memory = MemoryImpl(0)
        machine.copy(state = state, memory = memory)
    }()

    test fun ポインタが指す先が0ならループの最後までジャンプするはず() {
        val result = getAction(LOOP_START)(machineForLoopStart)
        assertEquals('.', result.state.currentProgramCode)
    }

    test fun ポインタが指す先が0以外ならループの中に入るはず() {
        val result = getAction(LOOP_START)(machineForLoopStart.copy(memory = MemoryImpl(0, listOf(1))))
        assertEquals('-', result.state.currentProgramCode)
    }

    val machineForLoopEnd = {
        val state = MachineStateImpl("+[-].", 3)
        val memory = MemoryImpl(0)
        machine.copy(state = state, memory = memory)
    }()

    test fun ポインタが指す先が0ならループから出るはず() {
        val result = getAction(LOOP_END)(machineForLoopEnd)
        assertEquals('.', result.state.currentProgramCode)
    }

    test fun ポインタが指す先が0以外ならループの最初に戻るはず() {
        val result = getAction(LOOP_END)(machineForLoopEnd.copy(memory = MemoryImpl(0, listOf(1))))
        assertEquals('-', result.state.currentProgramCode)
    }
}