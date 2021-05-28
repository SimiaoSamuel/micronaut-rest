package com.zup.controller

import com.zup.CasaDoCodigoGrpcServiceGrpc
import com.zup.dto.NovaCategoriaDto
import com.zup.shared.Open
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/categorias")
@Open
class CategoriaController(@Inject private val grpcClient: CasaDoCodigoGrpcServiceGrpc.CasaDoCodigoGrpcServiceBlockingStub) {

    @Post
    fun criaCategoria(@Valid categoria: NovaCategoriaDto): HttpResponse<Any>{
        val grpcRequest = categoria.toGrpcModel()

        val grpcResponse = grpcClient.criaCategoria(grpcRequest)

        val uri = HttpResponse.uri("/api/categorias/${grpcResponse.id}")

        return HttpResponse.created(uri)
    }
}