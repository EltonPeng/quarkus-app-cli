package com.zijian

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.POST
import javax.ws.rs.Path

@Path("/token")
@RegisterRestClient(configKey="token-api")
@RegisterClientHeaders
interface TokenService {

    @POST
    fun get(): Token
}