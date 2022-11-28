package com.zijian

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/product")
@RegisterRestClient
@RegisterClientHeaders
interface ProductService {

    @GET
    fun get(): Product
}