package com.taroid.brainfxck

import java.io.OutputStream
import kotlin.test.assertEquals
import org.junit.Test as test

public class RunnerTest {

    test fun 記述したプログラムが期待通りに動くはず() {
        val outputStream = object : OutputStream() {
            val buffer = java.util.ArrayList<Int>()

            override fun write(b  : Int) {
                buffer.add(b)
            }

            override fun flush() {
                assertEquals(listOf(0, 1, 2), buffer)
            }
        }

        val program = ".+.>++[>+<-]>."
        val machine = createMachine(program = program, outputStream = outputStream)
        machine.run()
    }
}