package com.odenizturker.locky.user.service

import com.odenizturker.locky.user.entity.UserEntity
import com.odenizturker.locky.user.model.UserModel
import com.odenizturker.locky.user.model.UserRegistrationModel
import com.odenizturker.locky.user.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun create(user: UserRegistrationModel) {
        val userEntity = userRepository.findByUsername(user.username).awaitSingleOrNull()
        if (userEntity != null) {
            throw Exception()
        }
        userRepository
            .save(
                UserEntity(
                    username = user.username,
                    password = user.password,
                    authorities = emptySet(),
                ),
            ).awaitSingle()
    }

    suspend fun getByUsername(username: String): UserModel {
        val userEntity = userRepository.findByUsername(username).awaitSingleOrNull() ?: throw Exception()

        return UserModel(userEntity)
    }
}
