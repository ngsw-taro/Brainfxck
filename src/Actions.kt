package com.taroid.brainfxck

public val INCREMENT_POINTER : Char = '>'

public val DECREMENT_POINTER : Char = '<'

public val INCREMENT_VALUE   : Char = '+'

public val DECREMENT_VALUE   : Char = '-'

public val INPUT             : Char = ','

public val OUTPUT            : Char = '.'

public val LOOP_START        : Char = '['

public val LOOP_END          : Char = ']'

private fun Machine.getNextState() : MachineState {
    if(state.isEndOfProgram()) {
        return state
    } else {
        return state.incrementProgramCounter()
    }
}

private val incrementPointer : (Machine) -> Machine = {
    it.copy(state = it.getNextState(),
            memory = it.memory.incrementPointer())
}

private val decrementPointer : (Machine) -> Machine = {
    it.copy(state = it.getNextState(),
            memory = it.memory.decrementPointer())
}

private val incrementValue : (Machine) -> Machine = {
    it.copy(state = it.getNextState(),
            memory = it.memory.setValue(it.memory.currentValue + 1))
}

private val decrementValue : (Machine) -> Machine = {
    it.copy(state = it.getNextState(),
            memory = it.memory.setValue(it.memory.currentValue - 1))
}

private val input : (Machine) -> Machine = {
    val inputValue = it.inputStream.read()
    it.copy(state = it.getNextState(),
            memory = it.memory.setValue(inputValue))
}

private val output : (Machine) -> Machine = {
    it.outputStream.write(it.memory.currentValue)
    it.copy(state = it.getNextState())
}

private val startLoop : (Machine) -> Machine = {
    fun startLoop(machine : Machine, nestCount : Int) : Machine {
        if(nestCount == 0) {
            return machine.copy(state = machine.getNextState())
        } else {
            val state = machine.state.incrementProgramCounter()
            val d = when (state.currentProgramCode) {
                LOOP_START ->  1
                LOOP_END   -> -1
                else       ->  0
            }

            return startLoop(machine.copy(state = state), nestCount + d)
        }
    }

    startLoop(it, if(it.memory.currentValue == 0) 1 else 0)
}

private val endLoop : (Machine) -> Machine = {
    fun endLoop(machine : Machine, nestCount : Int) : Machine {
        if(nestCount == 0) {
            return machine.copy(state = machine.getNextState())
        } else {
            val state = machine.state.decrementProgramCounter()
            val d = when(state.currentProgramCode) {
                LOOP_START -> -1
                LOOP_END   ->  1
                else       ->  0
            }

            return endLoop(machine.copy(state = state), nestCount + d)
        }
    }

    endLoop(it, if(it.memory.currentValue == 0) 0 else 1)
}

private val doNothing : (Machine) -> Machine = { it }

// TODO: type aliasがサポートされたら (Machine) -> Machine に別名を与える
public fun getAction(code : Char) : (Machine) -> Machine = when (code) {
    INCREMENT_POINTER -> incrementPointer
    DECREMENT_POINTER -> decrementPointer
    INCREMENT_VALUE   -> incrementValue
    DECREMENT_VALUE   -> decrementValue
    INPUT             -> input
    OUTPUT            -> output
    LOOP_START        -> startLoop
    LOOP_END          -> endLoop
    else              -> doNothing
}