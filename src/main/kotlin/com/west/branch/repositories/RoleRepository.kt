package com.west.branch.repositories

import com.west.branch.models.ERole
import com.west.branch.models.Role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: ERole?): Role?
}