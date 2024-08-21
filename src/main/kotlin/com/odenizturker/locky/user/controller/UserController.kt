package com.odenizturker.locky.user.controller

import com.odenizturker.locky.user.model.UserModel
import com.odenizturker.locky.user.model.UserRegistrationModel
import com.odenizturker.locky.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    suspend fun create(
        @RequestBody userRegistrationModel: UserRegistrationModel,
    ): Unit = userService.create(userRegistrationModel)

    @GetMapping
    suspend fun getByUsername(
        @RequestParam username: String,
    ): UserModel = userService.getByUsername(username)
}
