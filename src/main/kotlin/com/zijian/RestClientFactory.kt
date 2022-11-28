package com.zijian

import org.eclipse.microprofile.rest.client.RestClientBuilder
import java.net.URI
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RestClientFactory {
    //final ???
    final inline fun <reified T> getRestClient(): T {
        return RestClientBuilder.newBuilder().baseUri(URI.create("")).build(T::class.java)
    }
}