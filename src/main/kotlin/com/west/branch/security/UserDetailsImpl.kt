package com.west.branch.security

import com.fasterxml.jackson.annotation.JsonIgnore
import com.west.branch.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors


class UserDetailsImpl(
        val id: Long?,
        private val username: String?,
        val email: String?,
        @field:JsonIgnore private val password: String?,
        private val authorities: Collection<GrantedAuthority>?,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return Objects.equals(id, user.id)
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (authorities?.hashCode() ?: 0)
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = user.roles.stream()
                    .map { role -> SimpleGrantedAuthority(role.name?.name) }
                    .collect(Collectors.toList())
            return UserDetailsImpl(
                    user.id,
                    user.username,
                    user.email,
                    user.password,
                    authorities)
        }
    }
}