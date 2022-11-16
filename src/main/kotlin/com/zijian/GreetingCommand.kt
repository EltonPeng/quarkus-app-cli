package com.zijian

import mu.KLogger
import mu.KotlinLogging
import picocli.CommandLine.Command
import javax.inject.Inject

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand(
    private val logger: KLogger = KotlinLogging.logger {},
    private val tokenService: TokenService, // can build and test but cannot run
    private val putObjectService: PutObjectService = PutObjectServiceImpl()
) : Runnable {

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        val token = tokenService.get()
        logger.info { "token is ${token.accessToken}" }

        putObjectService.put("aa")
        logger.info { "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" }
    }
}
