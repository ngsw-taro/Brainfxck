package com.taroid.brainfxck

public trait Memory {

    public val currentValue : Int

    public fun setValue(newValue : Int) : Memory

    public fun incrementPointer() : Memory

    public fun decrementPointer() : Memory
}