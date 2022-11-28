package com.zijian

import org.eclipse.microprofile.rest.client.RestClientBuilder
import java.net.URI
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RestClientFactory {
    fun getTokenRestClient(): TokenService {
        return RestClientBuilder.newBuilder().baseUri(URI.create("")).build(TokenService::class.java)
    }
}