package com.zup.dto

import com.zup.NovaCategoriaRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class NovaCategoriaDto(
    @field:NotBlank
    val nome: String?
){
    fun toGrpcModel(): NovaCategoriaRequest? {
        return NovaCategoriaRequest.newBuilder()
            .setNome(nome)
            .build()
    }
}