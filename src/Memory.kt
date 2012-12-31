package com.taroid.brainfxck

public trait Memory {
    
    val currentValue : Int

    fun incrementPointer() : Memory

    fun decrementPointer() : Memory

    fun incrementValue() : Memory

    fun decrementValue() : Memory

    fun input() : Memory

    fun output() : Memory
}