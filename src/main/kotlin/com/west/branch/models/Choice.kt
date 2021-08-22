package com.west.branch.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "adventureId")
    var adventure: Adventure? = null

    @NotNull
    var ordinal: Int? = null

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    var text: String? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sourceStepId")
    var sourceStep: Step? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "targetStepId")
    var targetStep: Step? = null
}