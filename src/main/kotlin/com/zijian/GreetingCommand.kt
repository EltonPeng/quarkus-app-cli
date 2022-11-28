package com.zijian

import mu.KLogger
import mu.KotlinLogging
import picocli.CommandLine.Command

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand(
    private val logger: KLogger = KotlinLogging.logger {},
    private val restClientFactory: RestClientFactory, // can build and test but cannot run
    private val convertService: ConvertService,
    private val putObjectService: PutObjectService = PutObjectServiceImpl()
) : Runnable {

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        val token = restClientFactory.getRestClient<TokenService>().get()
        logger.info { "token is ${token.accessToken}" }

        val fileContent = convertService.goWithMoshi(token)
        putObjectService.put("aa")
        logger.info { "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" }
    }
}
