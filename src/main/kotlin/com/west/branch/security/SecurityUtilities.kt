package com.west.branch.security

import com.west.branch.models.User
import com.west.branch.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component()
@Scope("singleton")
class SecurityUtilities {
    @Autowired
    val userRepository: UserRepository? = null

    fun getCurrentUser(): User {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        return userRepository!!.findByUsername(userDetails.username)!!
    }
}