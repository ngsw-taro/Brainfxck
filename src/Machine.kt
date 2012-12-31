package com.taroid.brainfxck

import java.io.InputStream
import java.io.OutputStream

public class Machine(state : MachineState,
    memory : Memory) : MachineState by state, Memory by memory