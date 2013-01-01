package com.taroid.brainfxck

import java.io.InputStream
import java.io.OutputStream

public data class Machine(public val state : MachineState,
                     public val memory : Memory,
                     public val inputStream : InputStream,
                     public val outputStream : OutputStream) {

    public fun next() : Machine = getAction(state.currentProgramCode)(this)
}

public fun createMachine(program : String,
                     inputStream : InputStream = System.`in`,
                     outputStream : OutputStream = System.`out`) : Machine {
    val state = MachineStateImpl(program)
    val memory = MemoryImpl()
    return Machine(state, memory, inputStream, outputStream)
}
