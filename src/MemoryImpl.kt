package com.taroid.brainfxck

import kotlin.dom.addClass

internal class MemoryImpl(pointer : Int = 0, private val values : List<Int> = listOf(0)) : Memory {

    private val pointer : Int

    {
        require(pointer >= 0,
            "The pointer should not be less than 0, but actual it is $pointer.")
        require(pointer < values.size,
            "The pointer should not be greater than size of the values")
        this.pointer = pointer
    }

    override public val currentValue : Int
    get() {
        assert(pointer >= 0 && pointer < values.size)
        return values[pointer]
    }

    override fun incrementPointer(): Memory {
        check(pointer < Integer.MAX_VALUE,
            "The pointer should be less than ${Integer.MAX_VALUE}.")

        val incrementedPointer = pointer + 1
        val newValues = if(values.size == incrementedPointer) {
            values + 0
        } else {
            values
        }

        return MemoryImpl(incrementedPointer, newValues)
    }

    override public fun decrementPointer(): Memory {
        check(pointer > 0, "The pointer should not be less than 0.")
        return MemoryImpl(pointer - 1, values)
    }

    override public fun setValue(newValue : Int) : Memory {
        val newValues = java.util.ArrayList(values)
        newValues[pointer] = newValue
        return MemoryImpl(pointer, newValues.toList())
    }
}