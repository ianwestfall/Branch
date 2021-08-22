package com.west.branch.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @NotBlank
    @Size(min = 2, max = 500)
    var text: String? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "adventureId")
    var adventure: Adventure? = null

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    @JsonManagedReference
    var choices: List<Choice>? = null
}
