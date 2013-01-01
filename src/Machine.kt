package com.taroid.brainfxck

import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream

public data class Machine(internal val state : MachineState,
                     internal val memory : Memory,
                     internal val inputStream : InputStream,
                     internal val outputStream : OutputStream) : AutoCloseable, Closeable {
    public fun next() : Machine = getAction(state.currentProgramCode)(this)

    public fun hasNext() : Boolean = !state.isEndOfProgram()

    override public fun close() {
        inputStream.close()
        outputStream.close()
    }
}

public fun createMachine(program : String,
                     inputStream : InputStream = System.`in`,
                     outputStream : OutputStream = System.`out`) : Machine {
    val state = MachineStateImpl(program)
    val memory = MemoryImpl()
    return Machine(state, memory, inputStream, outputStream)
}
