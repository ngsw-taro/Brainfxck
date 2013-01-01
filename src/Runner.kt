package com.taroid.brainfxck

// TODO: TCOが実装されたら下記のバージョンを有効にする
//public fun runMachine(machine : Machine) {
//    if(machine.hasNext()) {
//        run(machine.next())
//    } else {
//        machine.outputStream.flush()
//        machine.close()
//    }
//}

// TODO: TCOが実装されたら下記のバージョンを削除する
public fun runMachine(machine : Machine) {
    var current = machine
    while(current.hasNext()) {
        current = current.next()
    }

    current.outputStream.flush()
    current.close()
}

public fun Machine.run() {
    runMachine(this)
}