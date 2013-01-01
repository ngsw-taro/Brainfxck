package com.taroid.brainfxck

public data trait MachineState {

    public val program : String

    public val currentProgramCode : Char

    public fun incrementProgramCounter() : MachineState

    public fun decrementProgramCounter() : MachineState

    public fun isEndOfProgram() : Boolean
}