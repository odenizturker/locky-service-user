package com.odenizturker.locky.user.repository

import com.odenizturker.locky.user.entity.UserEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
interface UserRepository : ReactiveCrudRepository<UserEntity, UUID> {
    fun findByUsername(username: String): Mono<UserEntity>
}
