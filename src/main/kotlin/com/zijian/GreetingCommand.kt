package com.zijian

import mu.KotlinLogging
import picocli.CommandLine.Command

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand : Runnable {
    private val logger = KotlinLogging.logger {}

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        logger.info("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~")
    }

}