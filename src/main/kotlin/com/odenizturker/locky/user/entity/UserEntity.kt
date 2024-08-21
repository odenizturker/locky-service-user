package com.odenizturker.locky.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import java.util.UUID

@Table("users")
data class UserEntity(
    @Id
    val id: UUID? = null,
    val username: String,
    val password: String,
    val authorities: Set<Authorities>,
    val accountExpired: Boolean = false,
    val accountLocked: Boolean = false,
    val credentialsExpired: Boolean = false,
    val enabled: Boolean = true,
)

enum class Authorities : GrantedAuthority {
    USER,
    ADMIN,
    ;

    override fun getAuthority(): String = this.name
}
