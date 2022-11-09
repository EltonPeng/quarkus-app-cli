package com.zijian

import mu.KLogger
import mu.KotlinLogging
import picocli.CommandLine.Command
import javax.inject.Inject

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand(
    private val logger: KLogger = KotlinLogging.logger {}
) : Runnable {

    @Inject
    lateinit var putObjectService: PutObjectService

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        putObjectService.put("aa")
        logger.info { "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" }
    }
}
