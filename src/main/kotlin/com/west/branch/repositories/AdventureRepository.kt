package com.west.branch.repositories

import com.west.branch.models.Adventure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdventureRepository : JpaRepository<Adventure, Long> {
    // fun findAllByOrderByCreatedAtDesc():
}
