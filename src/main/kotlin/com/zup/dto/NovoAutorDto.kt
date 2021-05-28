package com.zup.dto

import com.zup.NovoAutorRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Introspected
data class NovoAutorDto(
    @field:NotBlank
    val nome: String?,
    @field:NotBlank
    @field:Email
    val email: String?,
    @field:NotBlank
    val descricao: String?
){
    fun toGrpcModel(): NovoAutorRequest {
        return NovoAutorRequest.newBuilder()
            .setDescricao(descricao)
            .setEmail(email)
            .setNome(nome)
            .build()
    }
}

