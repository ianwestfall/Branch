package com.west.branch.models

import javax.persistence.*


@Entity
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: ERole? = null

    constructor() {}
    constructor(name: ERole?) {
        this.name = name
    }
}
