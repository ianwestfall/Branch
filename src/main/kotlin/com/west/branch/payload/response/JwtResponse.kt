package com.west.branch.payload.response

data class JwtResponse(
    val accessToken: String,
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>,
) {
    val tokenType = "Bearer"
}
