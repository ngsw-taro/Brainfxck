package com.taroid.sample.brainfxck

import com.taroid.brainfxck.createMachine
import com.taroid.brainfxck.run

// Hello World by Brainfxck
fun main(args : Array<String>) {
    val program = """
        +++++++++[>++++++++>+++++++++++>+++++<<<-]>.>++.+++++++..+++.>-.
        ------------.<++++++++.--------.+++.------.--------.>+.
    """

    createMachine(program).run()
}