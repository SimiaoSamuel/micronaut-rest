package com.zup.controller

import com.zup.CasaDoCodigoGrpcServiceGrpc
import com.zup.dto.NovoAutorDto
import com.zup.shared.Open
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import java.lang.RuntimeException
import javax.inject.Inject
import javax.validation.Valid

@Open
@Controller("/api")
class AutorController(@Inject private val grpcClient: CasaDoCodigoGrpcServiceGrpc.CasaDoCodigoGrpcServiceBlockingStub) {

    @Post("/autores")
    fun novoAutor(@Valid autor: NovoAutorDto): MutableHttpResponse<Any> {
        val grpcRequest = autor.toGrpcModel()

        val criaAutor = grpcClient.criaAutor(grpcRequest)

        val uri = HttpResponse.uri("/api/livros/${criaAutor.id}")

        return HttpResponse.created(uri)
    }
}