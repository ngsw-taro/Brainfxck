package com.taroid.brainfxck

import java.io.InputStream
import java.io.OutputStream

public trait Compiler {
        fun compile(source : String,
                    inputStream : InputStream = System.`in`,
                    outputStream : OutputStream = System.`out`) : Machine
}