package com.taroid.brainfxck

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import org.junit.Test as test
import java.io.OutputStream
import com.sun.tools.internal.xjc.generator.annotation.spec.XmlValueWriter
import java.io.OutputStreamWriter
import com.sun.org.apache.regexp.internal.REProgram

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