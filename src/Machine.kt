package com.taroid.brainfxck

import java.io.InputStream
import java.io.OutputStream

public class Machine(private val previousMachine : Machine,
                     private val state : MachineState,
                     private val memory : Memory) {

    public fun next() : Machine {
        //TODO
        return this
    }

    public fun back() : Machine = previousMachine
}