package com.zijian

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GreetingCommandTest {

    @Test
    fun run() {
        val command = GreetingCommand()
        command.run()
    }
}