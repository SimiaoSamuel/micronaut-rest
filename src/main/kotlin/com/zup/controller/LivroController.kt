package com.zup.controller

import com.zup.CasaDoCodigoGrpcServiceGrpc
import com.zup.dto.NovoLivroDto
import com.zup.shared.Open
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/livros")
@Open
class LivroController(@Inject private val grpcClient: CasaDoCodigoGrpcServiceGrpc.CasaDoCodigoGrpcServiceBlockingStub) {

    @Post
    fun criaLivro(@Valid livro: NovoLivroDto): HttpResponse<Any>{
        val grpcRequest = livro.toGrpcModel()

        val grpcResponse = grpcClient.criaLivro(grpcRequest)

        val uri = HttpResponse.uri("/api/livros/${grpcResponse.id}")

        return HttpResponse.created(uri)
    }
}