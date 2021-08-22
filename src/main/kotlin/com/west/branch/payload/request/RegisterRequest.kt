package com.west.branch.payload.request

import javax.validation.constraints.*


data class RegisterRequest (
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    val username: String,

    @field:NotBlank
    @field:Size(max = 50)
    @field:Email
    val email: String,

    val roles: Set<String>,

    @field:NotBlank
    @field:Size(min = 6, max = 40)
    val password: String
)
