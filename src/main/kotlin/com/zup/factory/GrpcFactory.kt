package com.zup.factory

import com.zup.CasaDoCodigoGrpcServiceGrpc
import com.zup.PaisEstadoServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcFactory (@GrpcChannel(value = "casadocodigo") val channel: ManagedChannel){

    @Singleton
    fun livroClientStub(): CasaDoCodigoGrpcServiceGrpc.CasaDoCodigoGrpcServiceBlockingStub {
        return CasaDoCodigoGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun paisEstadoClientStub(): PaisEstadoServiceGrpc.PaisEstadoServiceBlockingStub {
        return PaisEstadoServiceGrpc.newBlockingStub(channel)
    }
}