package com.zup.dto

import com.zup.NovoLivroRequest
import com.zup.shared.toTimestamp
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.validation.constraints.*

@Introspected
data class NovoLivroDto(
    @field:NotBlank
    val titulo: String?,
    @field:NotBlank
    @field:Size(max = 500)
    val resumo: String?,
    @field:NotBlank
    val sumario: String?,
    @field:NotNull
    @field:Min(100)
    val paginas: Int?,
    @field:NotBlank
    val isbn: String?,
    @field:NotNull
    @field:FutureOrPresent
    val dataLancamento: LocalDateTime?,
    @field:NotNull
    val categoriaId: Long?,
    @field:NotNull
    val autorId: Long?
){
    fun toGrpcModel(): NovoLivroRequest {
        return NovoLivroRequest.newBuilder()
            .setPaginas(paginas!!)
            .setIdAutor(autorId!!)
            .setIdCategoria(categoriaId!!)
            .setResumo(resumo)
            .setIsbn(isbn)
            .setSumario(sumario)
            .setTitulo(titulo)
            .setDataLancamento(dataLancamento!!.toTimestamp())
            .build()
    }
}
