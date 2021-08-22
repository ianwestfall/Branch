package com.west.branch.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(
    name = "auth_user",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"]),
        UniqueConstraint(columnNames = ["email"]),
    ],
)
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @NotBlank
    @Size(max = 20)
    var username: String? = null

    @NotNull
    @Size(max = 50)
    @Email
    var email: String? = null

    @NotNull
    @NotBlank
    @Size(min = 8, max = 120)
    @JsonIgnore
    var password: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "role_id")],
    )
    var roles: Set<Role> = HashSet()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonBackReference
    var adventures: List<Adventure>? = null

    constructor() {}
    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }
}