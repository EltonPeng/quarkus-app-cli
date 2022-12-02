package com.zijian

import mu.KLogger
import mu.KotlinLogging
import picocli.CommandLine.Command
import java.io.File
import java.time.LocalDate
import javax.inject.Inject

@Command(name = "greeting", mixinStandardHelpOptions = true)
class GreetingCommand(
    private val logger: KLogger = KotlinLogging.logger {}
) : Runnable {

    @Inject
    lateinit var restClientFactory: RestClientFactory

    @Inject
    lateinit var convertService: ConvertService

    @Inject
    lateinit var putObjectService: PutObjectService

    override fun run() {
        System.out.printf("Yo bro, go go commando!\n")
        //val token = restClientFactory.getRestClient<TokenService>().get()
        logger.info { "token is retrieved from ${restClientFactory.javaClass}" }
        val token = Token("null", expiredIn = LocalDate.now(), tokenType = TokenType.Temp)

        val fileContent = convertService.goWithMoshi(token)
        logger.info { fileContent }
        File("aa").createNewFile()
        putObjectService.put("aa")
        logger.info { "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" }
    }
}
