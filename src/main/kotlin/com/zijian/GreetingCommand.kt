package com.zijian

import mu.KLogger
import mu.KotlinLogging
import picocli.CommandLine.Command

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand(
    private val logger: KLogger = KotlinLogging.logger {},
    private val putObjectService: PutObjectService
) : Runnable {

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        putObjectService.put("aa")
        logger.info { "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" }
    }
}
