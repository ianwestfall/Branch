package com.west.branch.controllers

import com.west.branch.models.ERole
import com.west.branch.models.Role
import com.west.branch.models.User
import com.west.branch.payload.request.LoginRequest
import com.west.branch.payload.request.RegisterRequest
import com.west.branch.payload.response.JwtResponse
import com.west.branch.payload.response.MessageResponse
import com.west.branch.repositories.RoleRepository
import com.west.branch.repositories.UserRepository
import com.west.branch.security.JwtUtils
import com.west.branch.security.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest?): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest!!.username, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities!!.stream()
                .map { item: GrantedAuthority -> item.authority }
                .collect(Collectors.toList())
        return ResponseEntity.ok(JwtResponse(jwt,
                userDetails.id!!,
                userDetails.username!!,
                userDetails.email!!,
                roles))
    }

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<*> {
        if (userRepository!!.existsByUsername(registerRequest.username)!!) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository!!.existsByEmail(registerRequest.email)!!) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Email is already in use!"))
        }

        // Create new user's account
        val user = User(registerRequest.username,
                registerRequest.email,
                encoder!!.encode(registerRequest.password))
        val strRoles: Set<String> = registerRequest.roles
        val roles: MutableSet<Role> = HashSet()
        if (strRoles.isEmpty()) {
            val userRole: Role = roleRepository!!.findByName(ERole.ROLE_USER)
                    ?: throw RuntimeException("Error: Role is not found.")
            roles.add(userRole)
        }

        strRoles.forEach(Consumer { role: String? ->
            val newRole = when (role) {
                "admin" -> {
                    roleRepository!!.findByName(ERole.ROLE_ADMIN)
                            ?: throw RuntimeException("Error: Role is not found.")
                }
                else -> {
                    roleRepository!!.findByName(ERole.ROLE_USER)
                            ?: throw RuntimeException("Error: Role is not found.")
                }
            }
            roles.add(newRole)
        })

        user.roles = roles
        userRepository!!.save(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}