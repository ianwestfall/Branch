package com.west.branch.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView
import com.west.branch.payload.request.View
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["ownerId", "name"]),
    ],
)
class Adventure {
    @Id
    @GeneratedValue
    var id: Long? = null

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    var name: String? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ownerId")
    @JsonManagedReference
    var owner: User? = null

    @NotNull
    var published: Boolean = false

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "initialStepId")
    var initialStep: Step? = null

    @CreationTimestamp
    var createdAt: LocalDateTime? = null
}