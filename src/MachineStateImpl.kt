package com.taroid.brainfxck

private val PC_OUT_OF_RANGE = "The program counter is out of range."

internal class MachineStateImpl(override public val program : String,
                                programCounter : Int) : MachineState {

    private val programCounter : Int

    {
        require(programCounter >= 0, PC_OUT_OF_RANGE)
        require(programCounter <= program.length, PC_OUT_OF_RANGE)

        this.programCounter = programCounter
    }

    override public val currentProgramCode : Char
    get() {
        assert(programCounter >= 0 && programCounter < program.length())
        return program[programCounter]
    }

    override public fun incrementProgramCounter() : MachineState {
        check(!isEndOfProgram(), PC_OUT_OF_RANGE)
        return MachineStateImpl(program, programCounter + 1)
    }

    override public fun decrementProgramCounter() : MachineState {
        check(programCounter > 0, PC_OUT_OF_RANGE)
        return MachineStateImpl(program, programCounter - 1)
    }

    override public fun isEndOfProgram() = programCounter == program.length()
}